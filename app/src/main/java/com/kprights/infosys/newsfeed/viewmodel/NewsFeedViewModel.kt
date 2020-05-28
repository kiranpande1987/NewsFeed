package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kprights.infosys.newsfeed.model.NewsFeed


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:57 PM
 */

class NewsFeedViewModel(
    private val newsFeedRepository: NewsFeedRepository
) : ViewModel()
{
    val newsFeed: LiveData<NewsFeed> = Transformations.map(newsFeedRepository.newsFeed){ it }
    val newsTitle: LiveData<String> = Transformations.map(newsFeed){ "News Feed" }
    val status: LiveData<NewsFeedRepository.ApiStatus> = Transformations.map(newsFeedRepository.status){ it }

    override fun onCleared() {
        super.onCleared()
        newsFeedRepository.cancel()
    }
}