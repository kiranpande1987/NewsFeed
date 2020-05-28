package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import com.kprights.infosys.newsfeed.model.NewsFeed


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 16/04/20
 * Time : 12:41 PM
 */

interface IDataSource {
    fun getAllNews(): LiveData<NewsFeed>
    suspend fun deleteAllNews()
    suspend fun saveAllNews(newsFeed: NewsFeed)

    suspend fun getNewsFromRemote(): NewsFeed
}