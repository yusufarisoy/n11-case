package com.yusufarisoy.n11case.core

import retrofit2.Response

suspend fun <T : Any> networkCall(
    call: suspend () -> Response<T>
): T {
    val response = call()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            return body
        } else {
            throw Throwable(message = response.message())
        }
    } else {
        throw Throwable(message = response.message())
    }
}
