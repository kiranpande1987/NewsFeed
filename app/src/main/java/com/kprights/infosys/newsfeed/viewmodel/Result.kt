package com.kprights.infosys.newsfeed.viewmodel


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 16/04/20
 * Time : 1:42 PM
 */

sealed class Result<T>(
    val data: T? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Loading<T>(data: T? = null) : Result<T>(data)
    class Error<T>(exception: Exception, data: T? = null) : Result<T>(data, exception)
}
