package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import com.kprights.infosys.newsfeed.common.WebService
import com.kprights.infosys.newsfeed.model.NewsFeed
import timber.log.Timber


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 16/04/20
 * Time : 12:42 PM
 */

class RemoteDataSource : IDataSource {

    override suspend fun getNewsFromRemote(): NewsFeed {
        val deferred = WebService.getNewsFeed()
        val newsFeed = deferred.await()
        Timber.e("Remote Call $newsFeed")
        return newsFeed
    }

    override fun getAllNews(): LiveData<NewsFeed> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllNews() {
        TODO("Not yet implemented")
    }

    override suspend fun saveAllNews(newsFeed: NewsFeed) {
        TODO("Not yet implemented")
    }

}