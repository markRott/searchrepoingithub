package com.example.testappforgenesis.entity.remote

import com.google.gson.annotations.SerializedName

class RepoItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("stargazers_count") val stars : Int?
)