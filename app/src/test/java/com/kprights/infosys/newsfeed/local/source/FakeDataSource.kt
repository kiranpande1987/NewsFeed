package com.kprights.infosys.newsfeed.local.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kprights.infosys.newsfeed.model.NewsFeed
import com.kprights.infosys.newsfeed.viewmodel.IDataSource


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 28/05/20
 * Time : 10:39 PM
 */

class FakeDataSource(var exitingNewFeed: NewsFeed) : IDataSource {

    private var newsFeed: MutableLiveData<NewsFeed> = MutableLiveData<NewsFeed>()

    override fun getAllNews(): LiveData<NewsFeed> {
        return newsFeed.apply {
            //postValue(exitingNewFeed)
        }
    }

    override suspend fun deleteAllNews() {
        exitingNewFeed.apply {
            id = -1
            strTitle = ""
            listOfNews.clear()
        }
    }

    override suspend fun saveAllNews(news: NewsFeed) {
        this.exitingNewFeed = news
    }

    override suspend fun getNewsFromRemote(): NewsFeed {
        return exitingNewFeed
    }
}