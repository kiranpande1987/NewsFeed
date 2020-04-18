package com.kprights.infosys.newsfeed.viewmodel

import com.kprights.infosys.newsfeed.common.WebService
import com.kprights.infosys.newsfeed.model.News
import timber.log.Timber


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 16/04/20
 * Time : 12:42 PM
 */

class RemoteDataSource : IDataSource {

    suspend fun getAllNews(): List<News> {

        val deferred = WebService.getNewsFeed()
        val newsFeed = deferred.await()
        Timber.e("Remote Call")
        return newsFeed.listOfNews
    }
}