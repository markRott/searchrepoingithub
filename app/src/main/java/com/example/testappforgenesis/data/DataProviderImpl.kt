package com.example.testappforgenesis.data

import android.util.Log
import com.example.testappforgenesis.utils.THREAD_TAG
import com.example.testappforgenesis.api.ApplicationApi
import com.example.testappforgenesis.utils.Status
import com.example.testappforgenesis.utils.getResult
import com.example.testappforgenesis.entity.mapper.toUi
import com.example.testappforgenesis.entity.request.RequestData
import com.example.testappforgenesis.ui.GitHubRepoState

class DataProviderImpl(private val api: ApplicationApi) : DataProvider {

    override suspend fun searchRepo(requestData: RequestData): GitHubRepoState {
        val response = getResult {
            api.searchGitHubRepo(
                requestData.query,
                requestData.page,
                requestData.sort,
                requestData.perPage
            )
        }

        Log.d(THREAD_TAG, "Thread name: ${Thread.currentThread().name}")

        return when (response.status) {
            Status.SUCCESS -> { return GitHubRepoState.Data(response.data?.items?.toUi() ?: emptyList()) }
            else -> { GitHubRepoState.Error(response.msg ?: "") }
        }
    }
}