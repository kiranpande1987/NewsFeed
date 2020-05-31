package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 31/05/20
 * Time : 5:10 PM
 */

interface INewsFeedRepository {
    val job: CompletableJob
    val scope: CoroutineScope
    var newsFeed: LiveData<NewsFeed>
    val status: MutableLiveData<ApiStatus>

    enum class ApiStatus { LOADING, ERROR, DONE }

    // Fetch Latest Data from Web.
    // if Success, Delete Data from Local Database.
    // Save Latest Data to Local Database.
    // If Error, Throw Exception.
    fun updateDataFromRemoteDataSource()

    suspend fun fetchDataFromRemote(): NewsFeed?

    suspend fun insertDataIntoDatabase(newsFeed: NewsFeed)

    fun cancel()
}