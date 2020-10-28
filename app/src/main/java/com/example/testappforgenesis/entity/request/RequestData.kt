package com.example.testappforgenesis.entity.request

import com.example.testappforgenesis.utils.PER_PAGE
import com.example.testappforgenesis.utils.SORT_BY_STARS

data class RequestData(
    val query: String,
    val page: Int,
    val perPage: Int = PER_PAGE,
    val sort: String = SORT_BY_STARS,
)