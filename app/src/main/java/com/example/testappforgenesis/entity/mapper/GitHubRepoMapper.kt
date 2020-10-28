package com.example.testappforgenesis.entity.mapper

import com.example.testappforgenesis.entity.remote.RepoItem
import com.example.testappforgenesis.entity.ui.RepoItemUI

fun RepoItem.toUi(): RepoItemUI = RepoItemUI(
    id = id,
    name = name ?: "",
    fullName = fullName ?: "",
    description = description ?: "",
    url = url ?: "",
    language = language ?: "",
    stars = stars ?: 0
)

fun List<RepoItem>.toUi(): List<RepoItemUI> = this.map { it.toUi() }