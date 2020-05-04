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

class NewsFeedRepository(database: NewsFeedDao, private val dispatcher: CoroutineDispatcher)
{
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val localDataSource = LocalDataSource(database)
    private val remoteDataSource = RemoteDataSource()

    private val job = Job()
    private val scope = CoroutineScope(job + dispatcher)

    val newsFeed: LiveData<NewsFeed> = localDataSource.getAllNews()
    val status: MutableLiveData<ApiStatus> = MutableLiveData<ApiStatus>()

    init { updateDataFromRemoteDataSource() }

    // Fetch Latest Data from Web.
    // if Success, Delete Data from Local Database.
    // Save Latest Data to Local Database.
    // If Error, Throw Exception.
    fun updateDataFromRemoteDataSource()
    {
        scope.launch(dispatcher) {
            val newsFeed = fetchDataFromRemote()

            newsFeed?.let {
                deleteDataFromDatabase()
                insertDataIntoDatabase(it)
            }
        }
    }

    suspend fun fetchDataFromRemote() : NewsFeed?
    {
        try
        {
            status.value = ApiStatus.LOADING
            return remoteDataSource.getAllNews()
        }
        catch (e: Exception)
        {
            status.value = ApiStatus.ERROR
        }

        return null
    }

    suspend fun deleteDataFromDatabase()
    {
        withContext(dispatcher)
        {
            localDataSource.deleteAllNews()
        }
    }

    suspend fun insertDataIntoDatabase(newsFeed: NewsFeed)
    {
        withContext(dispatcher)
        {
            localDataSource.saveAllNews(newsFeed)
        }
    }

    fun cancel()
    {
        job.cancel()
    }
}