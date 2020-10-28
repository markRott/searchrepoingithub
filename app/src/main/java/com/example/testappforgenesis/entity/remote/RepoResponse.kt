package com.example.testappforgenesis.entity.remote

import com.google.gson.annotations.SerializedName

class RepoResponse(
    @SerializedName("items")
    val items: List<RepoItem>
)