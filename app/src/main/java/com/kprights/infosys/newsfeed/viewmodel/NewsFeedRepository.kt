package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.*

/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 11/04/20
 * Time : 11:20 AM
 */

class NewsFeedRepository(
    private val localDataSource: IDataSource,
    private val remoteDataSource: IDataSource,
    private val ioDispatcher: CoroutineDispatcher
)
{
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val job = Job()
    private val scope = CoroutineScope(job + ioDispatcher)

    var newsFeed: LiveData<NewsFeed> = localDataSource.getAllNews()
    val status: MutableLiveData<ApiStatus> = MutableLiveData<ApiStatus>()

    init { updateDataFromRemoteDataSource() }

    // Fetch Latest Data from Web.
    // if Success, Delete Data from Local Database.
    // Save Latest Data to Local Database.
    // If Error, Throw Exception.
    fun updateDataFromRemoteDataSource()
    {
        scope.launch(ioDispatcher) {

            //getlocal()
            val newsFeed = fetchDataFromRemote()

            newsFeed?.let {
                deleteDataFromDatabase()
                insertDataIntoDatabase(it)
                status.postValue(ApiStatus.DONE)
            }
        }
    }

    suspend fun fetchDataFromRemote() : NewsFeed?
    {
        try
        {
            status.postValue(ApiStatus.LOADING)
            return remoteDataSource.getNewsFromRemote()
        }
        catch (e: Exception)
        {
            status.postValue(ApiStatus.ERROR)
        }

        return null
    }

    suspend fun deleteDataFromDatabase()
    {
        withContext(ioDispatcher)
        {
            localDataSource.deleteAllNews()
        }
    }

    suspend fun insertDataIntoDatabase(newsFeed: NewsFeed)
    {
        withContext(ioDispatcher)
        {
            localDataSource.saveAllNews(newsFeed)
        }
    }

    fun cancel()
    {
        job.cancel()
    }
}