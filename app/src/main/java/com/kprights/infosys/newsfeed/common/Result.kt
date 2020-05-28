package com.kprights.infosys.newsfeed.common

import kotlin.coroutines.suspendCoroutine


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 11/05/20
 * Time : 10:56 PM
 */

sealed class Result<out T : Any>
data class Success<out T : Any>(val data: T) : Result<T>()
data class Failure(val error: Throwable?) : Result<Nothing>()

interface Mappable<out T : Any> {
    fun mapToResult(): Result<T>
}

suspend fun <T : Any> getResult(): Result<T> = suspendCoroutine {

}