package com.kprights.infosys.newsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kprights.infosys.newsfeed.common.DatabaseService
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.Dispatchers


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:57 PM
 */

class NewsFeedViewModel(
    application: Application
) : AndroidViewModel(application)
{
    private var newsFeedRepository: NewsFeedRepository = NewsFeedRepository(DatabaseService.getInstance(application).newsFeedDao, Dispatchers.Main)
    val newsFeed: LiveData<NewsFeed> = Transformations.map(newsFeedRepository.newsFeed){ it }
    val newsTitle: LiveData<String> = Transformations.map(newsFeed){ "News Feed" }
    val status: LiveData<NewsFeedRepository.ApiStatus> = Transformations.map(newsFeedRepository.status){ it }

    override fun onCleared() {
        super.onCleared()
        newsFeedRepository.cancel()
    }
}