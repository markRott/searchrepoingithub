package com.example.testappforgenesis.data

import com.example.testappforgenesis.entity.request.RequestData
import com.example.testappforgenesis.ui.GitHubRepoState

interface DataProvider {
    suspend fun searchRepo(requestData: RequestData): GitHubRepoState
}