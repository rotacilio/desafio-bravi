package br.com.rotacilio.android.boredapp.utils

import retrofit2.HttpException

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Throwable) : DataState<Nothing>() {

        val statusCode: Int get() = if (this.exception is HttpException) this.exception.code() else -1
    }
    object Loading : DataState<Nothing>()
}