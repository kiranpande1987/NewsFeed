package com.kprights.infosys.newsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


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
            return NewsFeedViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}