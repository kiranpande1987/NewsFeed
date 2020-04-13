package com.kprights.infosys.newsfeed.viewmodel

import androidx.lifecycle.*
import com.kprights.infosys.newsfeed.common.NewsFeedDao
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:57 PM
 */

class NewsFeedViewModel() : ViewModel()
{
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _newsFeed = MutableLiveData<NewsFeed>()
    val newsFeed: LiveData<NewsFeed>
        get() = _newsFeed

    private val _newsTitle = MutableLiveData<String>()
    val newsTitle: LiveData<String>
        get() = _newsTitle

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    var database: NewsFeedDao? = null
    var newsFeedRepository: NewsFeedRepository? = null

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    fun set(lifecycleOwner: LifecycleOwner, database: NewsFeedDao)
    {
        newsFeedRepository = NewsFeedRepository(database)
        newsFeedRepository!!.data.observe(lifecycleOwner, Observer {
                newsFeed -> update(newsFeed)
        })

        try {
            _status.value = ApiStatus.LOADING

            // Single Source Of Truth : Function to get NewsFeed for ViewModel.
            newsFeedRepository!!.getNewsFeedFeed()
        }
        catch (e: Exception)
        {
            _newsFeed.value = NewsFeed()
            _status.value = ApiStatus.ERROR
        }
    }

    private fun update(newsFeed: NewsFeed)
    {
        _newsFeed.value = newsFeed
        _newsTitle.value = _newsFeed.value!!.strTitle
        _status.value = ApiStatus.DONE
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}