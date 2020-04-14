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

class NewsFeedViewModel(
    val lifecycleOwner: LifecycleOwner,
    database: NewsFeedDao
) : ViewModel()
{
    private var newsFeedRepository: NewsFeedRepository = NewsFeedRepository(database)

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

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    init {
        getNewsFeedFromRepository()
    }

    private fun getNewsFeedFromRepository()
    {
        newsFeedRepository.data.observe(lifecycleOwner, Observer {
                newsFeed -> update(newsFeed)
        })

        try {
            _status.value = ApiStatus.LOADING

            // Single Source Of Truth : Function to get NewsFeed for ViewModel.
            newsFeedRepository.getNewsFeedFeed()
        }
        catch (e: Exception)
        {
            _newsFeed.value = NewsFeed()
            _status.value = ApiStatus.ERROR
        }
    }

    private fun update(newsFeed: NewsFeed)
    {
        if(newsFeed.strTitle.equals("Error"))
        {
            _newsFeed.value = NewsFeed()
            _status.value = ApiStatus.ERROR
        }
        else
        {
            _newsFeed.value = newsFeed
            _newsTitle.value = _newsFeed.value!!.strTitle
            _status.value = ApiStatus.DONE
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}