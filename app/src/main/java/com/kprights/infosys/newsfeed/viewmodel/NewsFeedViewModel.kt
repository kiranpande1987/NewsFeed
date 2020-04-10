package com.kprights.infosys.newsfeed.viewmodel

import android.app.Application
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.kprights.infosys.newsfeed.common.WebService
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


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

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    init
    {
        getNewsFeedFromWeb()
    }

    private fun getNewsFeedFromWeb()
    {
        scope.launch {
            val deferred = WebService.getNewsFeed()

            try
            {
                _status.value = ApiStatus.LOADING
                val result =  deferred.await()
                _status.value = ApiStatus.DONE
                _newsFeed.value =  result
                _newsTitle.value = result.strTitle
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _newsFeed.value = NewsFeed()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}