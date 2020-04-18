package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.model.News


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 16/04/20
 * Time : 12:42 PM
 */

class LocalDataSource(private val database: NewsFeedDao) : IDataSource {

    fun getAllNews(): LiveData<List<News>> {
        return database.getAllNews()
    }

    fun deleteAllNews() {
        database.clear()
    }

    fun saveAllNews(news: News) {
        database.insert(news)
    }
}