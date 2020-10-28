package com.example.testappforgenesis.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.testappforgenesis.R
import com.example.testappforgenesis.ui.adapter.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.frg_main.*
import kotlinx.android.synthetic.main.image_and_label_view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    private val viewModel: RepoViewModel by viewModels()
    private val repoAdapter: RepoAdapter = RepoAdapter()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.frg_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rcv_repo.adapter = repoAdapter

        showWelcomeView()
        initLiveSearch()

        subscribeToLoadState()
        subscribeToResultState()
    }

    private fun showWelcomeView() {
        ll_holder.isVisible = true
        iv_image.setImageResource(R.drawable.logo_welcome)
        tv_label.text = getString(R.string.msg_welcome_label)
    }

    private fun initLiveSearch() {
        edt_repo_name.doAfterTextChanged {
            val query = it?.toString() ?: ""
            if (query.isNotBlank()) {
                viewModel.queryChannel.offer(query)
            }
        }
    }

    private fun subscribeToLoadState() {
        viewModel.loadStateLD.observe(viewLifecycleOwner) { visibilityState ->
            progress_bar.isVisible = visibilityState
            if (visibilityState) {
                rcv_repo.isGone = true
                ll_holder.isGone = true
            }
        }
    }

    private fun subscribeToResultState() {
        viewModel.searchResultLD.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GitHubRepoState.Data -> { showRepositories(state) }
                is GitHubRepoState.Empty -> { showEmptyResultView() }
                is GitHubRepoState.Error -> { showErrorView(state) }
            }
        }
    }

    private fun showRepositories(gitHubRepoState: GitHubRepoState.Data) {
        ll_holder.isGone = true
        rcv_repo.isVisible = true
        repoAdapter.setItems(gitHubRepoState.list)
    }

    private fun showEmptyResultView() {
        rcv_repo.isGone = true
        ll_holder.isVisible = true
        iv_image.setImageResource(R.drawable.logo_empty_result)
        tv_label.text = getString(R.string.msg_search_result_is_empty)
    }

    private fun showErrorView(gitHubRepoState: GitHubRepoState.Error) {
        rcv_repo.isGone = true
        ll_holder.isVisible = true
        iv_image.setImageResource(R.drawable.logo_error)
        tv_label.text = gitHubRepoState.message
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}