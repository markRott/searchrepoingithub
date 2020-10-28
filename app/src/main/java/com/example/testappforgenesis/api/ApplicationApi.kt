package com.example.testappforgenesis.api

import com.example.testappforgenesis.entity.remote.RepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApplicationApi {

    @GET("/search/repositories")
    suspend fun searchGitHubRepo(
        @Query("q") searchParam: String,
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("per_page") perPage: Int
    ): Response<RepoResponse>
}