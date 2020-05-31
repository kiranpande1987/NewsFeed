package com.kprights.infosys.newsfeed.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kprights.infosys.newsfeed.MainCoroutineRule
import com.kprights.infosys.newsfeed.getOrAwaitValue
import com.kprights.infosys.newsfeed.local.source.FakeNewsFeedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Copyright (c) 2020 for KPrights
 *
 *
 * User : Kiran Pande
 * Date : 31/05/20
 * Time : 5:24 PM
 */

@ExperimentalCoroutinesApi
class NewsFeedViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: FakeNewsFeedRepository

    // Subject under test
    private lateinit var viewModel: NewsFeedViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {

        // We initialise the tasks to 3, with one active and two completed
        repository = FakeNewsFeedRepository()
        viewModel = NewsFeedViewModel(repository)
    }

    @Test
    fun checkDataAndStatus() {

        repository.setReturnError(false)
        repository.updateDataFromRemoteDataSource()

        val newsFeed = viewModel.newsFeed.getOrAwaitValue()
        val status = viewModel.status.getOrAwaitValue()
        val title = viewModel.newsTitle.getOrAwaitValue()

        Assert.assertEquals(status, INewsFeedRepository.ApiStatus.DONE)

        Assert.assertEquals(title, "Sakal")

        Assert.assertEquals(newsFeed.id, 100)
        Assert.assertEquals(newsFeed.strTitle, "Sakal")
        Assert.assertEquals(newsFeed.listOfNews.size, 1)
    }

    @Test
    fun checkErrorStatus() {
        repository.setReturnError(true)
        repository.updateDataFromRemoteDataSource()

        val status = viewModel.status.getOrAwaitValue()

        Assert.assertEquals(status, INewsFeedRepository.ApiStatus.ERROR)
    }
}