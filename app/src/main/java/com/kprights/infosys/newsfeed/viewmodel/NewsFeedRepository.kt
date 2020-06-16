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
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Main
) : INewsFeedRepository {

    override val job = Job()
    override val scope = CoroutineScope(job + ioDispatcher)

    override var newsFeed: LiveData<NewsFeed> = localDataSource.getAllNews()
    override val status: MutableLiveData<INewsFeedRepository.ApiStatus> =
        MutableLiveData<INewsFeedRepository.ApiStatus>()

    init {
        updateDataFromRemoteDataSource()
    }

    // Fetch Latest Data from Web.
    // if Success, Delete Data from Local Database.
    // Save Latest Data to Local Database.
    // If Error, Throw Exception.
    override fun updateDataFromRemoteDataSource() {
        scope.launch(ioDispatcher) {

            val newsFeed = fetchDataFromRemote()

            newsFeed?.let {
                deleteDataFromDatabase()
                insertDataIntoDatabase(it)
                status.postValue(INewsFeedRepository.ApiStatus.DONE)
            }
        }
    }

    override suspend fun fetchDataFromRemote(): NewsFeed? {
        try {
            status.postValue(INewsFeedRepository.ApiStatus.LOADING)
            return remoteDataSource.getNewsFromRemote()
        } catch (e: Exception) {
            status.postValue(INewsFeedRepository.ApiStatus.ERROR)
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

    override suspend fun insertDataIntoDatabase(newsFeed: NewsFeed) {
        withContext(ioDispatcher)
        {
            localDataSource.saveAllNews(newsFeed)
        }
    }

    override fun cancel() {
        job.cancel()
    }
}