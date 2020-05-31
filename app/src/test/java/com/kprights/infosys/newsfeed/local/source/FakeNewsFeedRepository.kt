package com.kprights.infosys.newsfeed.local.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import com.kprights.infosys.newsfeed.viewmodel.INewsFeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 31/05/20
 * Time : 5:16 PM
 */

class FakeNewsFeedRepository : INewsFeedRepository {

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override val job = Job()
    override val scope = CoroutineScope(job)

    override var newsFeed: LiveData<NewsFeed> = MutableLiveData()
    override val status: MutableLiveData<INewsFeedRepository.ApiStatus> =
        MutableLiveData<INewsFeedRepository.ApiStatus>()

    override fun updateDataFromRemoteDataSource() {

        if (shouldReturnError) {
            status.postValue(INewsFeedRepository.ApiStatus.ERROR)
        } else {
            val remoteDataSource = NewsFeed().apply {
                id = 100
                strTitle = "Sakal"
                listOfNews.add(
                    News(103, "Pune", "Pandemic Situation", "")
                )
            }

            (newsFeed as MutableLiveData).postValue(remoteDataSource)
            status.postValue(INewsFeedRepository.ApiStatus.DONE)
        }
    }

    override suspend fun fetchDataFromRemote(): NewsFeed? {
        TODO("Not yet implemented")
    }

    override suspend fun insertDataIntoDatabase(newsFeed: NewsFeed) {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }
}