package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.model.NewsFeed


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:57 PM
 */

class NewsFeedViewModel(
    database: NewsFeedDao
) : ViewModel()
{
    enum class ApiStatus { LOADING, ERROR, DONE }

    private var newsFeedRepository: NewsFeedRepository = NewsFeedRepository(database)
    val newsFeed: LiveData<NewsFeed> = Transformations.map(newsFeedRepository.newsFeed){ it }
    val newsTitle: LiveData<String> = Transformations.map(newsFeed){ newFeed -> newFeed.strTitle }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    override fun onCleared() {
        super.onCleared()
        newsFeedRepository.cancel()
    }
}