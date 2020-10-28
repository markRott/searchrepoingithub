package com.example.testappforgenesis.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.testappforgenesis.utils.DEBOUNCE
import com.example.testappforgenesis.utils.FIRST_PAGE
import com.example.testappforgenesis.utils.SECOND_PAGE
import com.example.testappforgenesis.data.DataProvider
import com.example.testappforgenesis.entity.request.RequestData
import com.example.testappforgenesis.entity.ui.RepoItemUI
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors

@FlowPreview
@ExperimentalCoroutinesApi
class RepoViewModel @ViewModelInject constructor(private val dataProvider: DataProvider) : ViewModel() {

    private val firstThread = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val secondThread = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val _loadMLD = MutableLiveData<Boolean>()
    val loadStateLD: LiveData<Boolean> get() = _loadMLD

    val queryChannel = ConflatedBroadcastChannel<String>()
    private val internalQueryFlow: Flow<GitHubRepoState> = queryChannel
            .asFlow()
            .debounce(DEBOUNCE)
            .mapLatest { searchRepo(it) }

    val searchResultLD = internalQueryFlow.asLiveData()

    private suspend fun searchRepo(query: String): GitHubRepoState {
        return supervisorScope {

            _loadMLD.value = true
            val part1 = fetchDataAsync(RequestData(query, FIRST_PAGE), firstThread)
            val part2 = fetchDataAsync(RequestData(query, SECOND_PAGE), secondThread)
            val stateList = getStateList(part1, part2)

            // Checking that all requests have worked incorrectly. E.g: there was no internet connection
            if (stateList.all { it is GitHubRepoState.Error }) {
                _loadMLD.value = false
                return@supervisorScope stateList.first()
            }

            val result = getRepositories(stateList)
            _loadMLD.value = false
            return@supervisorScope if (result.isEmpty()) GitHubRepoState.Empty else GitHubRepoState.Data(result)
        }
    }

    private fun fetchDataAsync(
        requestData: RequestData,
        dispatcher: ExecutorCoroutineDispatcher
    ): Deferred<GitHubRepoState> =
        viewModelScope.async(dispatcher) { dataProvider.searchRepo(requestData) }

    private suspend fun getStateList(vararg parts: Deferred<GitHubRepoState>): List<GitHubRepoState> =
            listOf(*parts).map {
                try {
                    it.await()
                } catch (e: Exception) {
                    GitHubRepoState.Error(e.message ?: "")
                }
            }

    private fun getRepositories(stateList: List<GitHubRepoState>): List<RepoItemUI> = stateList
            .filterIsInstance<GitHubRepoState.Data>()
            .map { it.list }.flatten()

    override fun onCleared() {
        super.onCleared()
        queryChannel.close()
    }
}