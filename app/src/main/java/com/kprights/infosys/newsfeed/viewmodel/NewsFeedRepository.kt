package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.*
import timber.log.Timber

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
        Timber.e("Repo : Start")
        updateDataFromRemoteDataSource()
        Timber.e("Repo : End")
    }

    private fun updateDataFromRemoteDataSource() {
        // Fetch Latest Data from Web.
        // if Success, Delete Data from Local Database.
        // Save Latest Data to Local Database.
        // If Error, Throw Exception.

        scope.launch {

            try
            {
                Timber.e("Repo : 1")
                status.value = ApiStatus.LOADING
                val newsFeed = remoteDataSource.getAllNews()
                Timber.e("Repo : 2")
                withContext(Dispatchers.IO)
                {
                    Timber.e("Repo : 3")
                    localDataSource.deleteAllNews()
                    Timber.e("Repo : 4")
                    localDataSource.saveAllNews(newsFeed)
                    Timber.e("Repo : 5")
                }

                status.value = ApiStatus.DONE
                Timber.e("Repo : 6")
            }
            catch (e: Exception)
            {
                status.value = ApiStatus.ERROR
                Timber.e("Repo : EXC : ${e.message}")
            }
        }
    }

    fun cancel()
    {
        job.cancel()
    }
}