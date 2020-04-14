package com.kprights.infosys.newsfeed.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.common.WebService
import com.kprights.infosys.newsfeed.model.News
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
    private val allNews = database.getAllNews();

    // Single Source Of Truth : Function to get NewsFeed for ViewModel.
    fun getNewsFeedFeed()
    {
        // Check for Local Database for NewsFeed and send it to ViewModel.
        // Check Web for updated NewsFeed
        // Insert updated NewsFeed into Local Database
        // Update ViewModel with latest NewsFeed from Local Database.

        //getNewsFeedFromDatabase() This is NOT working as expected.

        getNewsFeedFromWeb()
        //getNewsFeedFromDatabase()
    }

    // This function retrieves NewsFeed from Local Database
    private fun getNewsFeedFromDatabase()
    {
        val newsFeed = NewsFeed()
        val list = allNews.value

        newsFeed.listOfNews = list ?: listOf<News>()
        Log.e("ListSize : ", ""+newsFeed.listOfNews.size)
        data.value = newsFeed
    }

    // This function retrieves NewsFeed list and insert into database.
    private fun getNewsFeedFromWeb()
    {
        scope.launch {
            val deferred = WebService.getNewsFeed()

            try {
                val result =  deferred.await()
                data.value = result
                insertToDatabase(result)
                //getNewsFeedFromDatabase()
            }
            catch (e: Exception)
            {
                data.value = NewsFeed().apply { strTitle = "Error" }
            }
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