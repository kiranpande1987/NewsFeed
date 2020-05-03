package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.*

/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 11/04/20
 * Time : 11:20 AM
 */

class NewsFeedRepository(database: NewsFeedDao)
{
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val localDataSource = LocalDataSource(database)
    private val remoteDataSource = RemoteDataSource()

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    val newsFeed: LiveData<NewsFeed> = localDataSource.getAllNews()
    val status: MutableLiveData<ApiStatus> = MutableLiveData<ApiStatus>()

    init {
        updateDataFromRemoteDataSource()
    }

    private fun updateDataFromRemoteDataSource() {
        // Fetch Latest Data from Web.
        // if Success, Delete Data from Local Database.
        // Save Latest Data to Local Database.
        // If Error, Throw Exception.

        scope.launch {

            try
            {

                status.value = ApiStatus.LOADING
                val newsFeed = remoteDataSource.getAllNews()

                withContext(Dispatchers.IO)
                {
                    localDataSource.deleteAllNews()
                    localDataSource.saveAllNews(newsFeed)
                }

                status.value = ApiStatus.DONE
            }
            catch (e: Exception)
            {
                status.value = ApiStatus.ERROR
            }
        }
    }

    fun cancel()
    {
        job.cancel()
    }
}