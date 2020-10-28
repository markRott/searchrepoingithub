package com.example.testappforgenesis.utils

enum class Status {
    SUCCESS,
    ERROR
}

data class AppResult<out T>(val status: Status, val data: T?, val msg: String?) {

    companion object {

        fun <T> success(data: T?): AppResult<T> {
            return AppResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): AppResult<T> {
            return AppResult(Status.ERROR, data, message)
        }
    }
}