package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.model.News
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
    private val localDataSource = LocalDataSource(database)
    private val remoteDataSource = RemoteDataSource()

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    private var forceUpdate: Boolean = true
    val newsFeed: LiveData<NewsFeed> = Transformations.map(localDataSource.getAllNews()){
            list -> updateData(list)
    }

    private fun updateData(list: List<News>) : NewsFeed
    {
        scope.launch {
            updateDataFromRemoteDataSource()
        }

        return NewsFeed().apply {
            strTitle = "News Feed"
            listOfNews = list
            Timber.e("Default")
        }
    }

    private suspend fun updateDataFromRemoteDataSource() {
        // Fetch Latest Data from Web.
        // if Success, Delete Data from Local Database.
        // Save Latest Data to Local Database.
        // If Error, Throw Exception.

        if(forceUpdate)
        {
            withContext(Dispatchers.IO) {
                val remoteNews = remoteDataSource.getAllNews()
                Timber.e("Call Compete")
                localDataSource.deleteAllNews()
                remoteNews.forEach { news -> localDataSource.saveAllNews(news) }
                Timber.e("Update : $forceUpdate")
                forceUpdate = false
            }
        }



        // Webservice Fail TOBEDONE
    }

    fun cancel()
    {
        job.cancel()
    }
}