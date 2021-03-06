package com.kprights.infosys.newsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kprights.infosys.newsfeed.common.DatabaseService
import kotlinx.coroutines.Dispatchers


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 14/04/20
 * Time : 3:23 PM
 */

class NewsFeedViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {

            val localDataSource =
                LocalDataSource(DatabaseService.getInstance(application).newsFeedDao)
            val remoteDataSource = RemoteDataSource()

            var newsFeedRepository: NewsFeedRepository = NewsFeedRepository(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
                ioDispatcher = Dispatchers.Main
            )

            return NewsFeedViewModel(newsFeedRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}