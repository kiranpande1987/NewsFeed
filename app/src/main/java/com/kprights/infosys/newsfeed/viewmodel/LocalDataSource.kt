package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.model.NewsFeed


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 16/04/20
 * Time : 12:42 PM
 */

class LocalDataSource(private val database: NewsFeedDao) : IDataSource {

    override fun getAllNews(): LiveData<NewsFeed> {
        return database.getAllNews()
    }

    override suspend fun deleteAllNews() {
        database.clear()
    }

    override suspend fun saveAllNews(newsFeed: NewsFeed) {
        database.insert(newsFeed)
    }

    override suspend fun getNewsFromRemote(): NewsFeed {
        TODO("Not yet implemented")
    }
}