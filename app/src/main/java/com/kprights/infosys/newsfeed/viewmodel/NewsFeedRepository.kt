package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.common.WebService
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.*


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 11/04/20
 * Time : 11:20 AM
 */

class NewsFeedRepository(val database: NewsFeedDao)
{
    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    val data: MutableLiveData<NewsFeed> = MutableLiveData<NewsFeed>()

    // Single Source Of Truth : Function to get NewsFeed for ViewModel.
    fun getNewsFeedFeed()
    {
        // Check for Local Database for NewsFeed and send it to ViewModel.
        // Check Web for updated NewsFeed
        // Insert updated NewsFeed into Local Database
        // Update ViewModel with latest NewsFeed from Local Database.

        //getNewsFeedFromDatabase() This is NOT working as expected.

        getNewsFeedFromWeb()
    }

    // This function retrieves NewsFeed from Local Database
    private fun getNewsFeedFromDatabase()
    {
        val allNews = database.getAll()
        data.value!!.listOfNews = allNews.value!!
    }

    // This function retrieves NewsFeed list and insert into database.
    private fun getNewsFeedFromWeb()
    {
        scope.launch {
            val deferred = WebService.getNewsFeed()
            val result =  deferred.await()
            data.value = result
            insertToDatabase(result)
        }
    }

    // This function inserts new NewsFeed into Local Database
    private suspend fun insertToDatabase(result: NewsFeed)
    {
        withContext(Dispatchers.IO) {
            for (news in result.listOfNews) {
                database.insert(news)
            }
        }
    }
}