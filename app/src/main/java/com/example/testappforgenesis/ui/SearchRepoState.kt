package com.example.testappforgenesis.ui

import com.example.testappforgenesis.entity.ui.RepoItemUI

sealed class GitHubRepoState {
    object Empty : GitHubRepoState()
    class Data(val list: List<RepoItemUI>) : GitHubRepoState()
    class Error(val message: String) : GitHubRepoState()
}