package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kprights.infosys.newsfeed.common.NewsFeedDao


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 14/04/20
 * Time : 3:23 PM
 */

class NewsFeedViewModelFactory(
    private val dataSource: NewsFeedDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {
            return NewsFeedViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}