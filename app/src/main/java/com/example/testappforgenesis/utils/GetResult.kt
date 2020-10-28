package com.example.testappforgenesis.utils

import android.util.Log
import retrofit2.Response

const val TAG: String = "ApiInfo"

suspend fun <T> getResult(action: suspend () -> Response<T>): AppResult<T> {
    try {
        val response = action()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return AppResult.success(body)
        }

        val finalMsg = error("${response.code()} ${response.message()}")
        return AppResult.error(finalMsg)

    } catch (e: Exception) {
        Log.e(TAG, "Get result exception: ${e.message}")
        return AppResult.error(e.message ?: e.toString())
    }
}

private fun error(msg: String): String {
    val finalMsg = "Network call has failed for a following reason: $msg"
    Log.e(TAG, finalMsg)
    return finalMsg
}