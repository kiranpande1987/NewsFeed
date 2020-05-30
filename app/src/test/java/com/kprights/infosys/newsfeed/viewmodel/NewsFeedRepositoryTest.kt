package com.kprights.infosys.newsfeed.viewmodel

import com.kprights.infosys.newsfeed.MainCoroutineRule
import com.kprights.infosys.newsfeed.local.source.FakeDataSource
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Copyright (c) 2020 for KPrights
 *
 *
 * User : Kiran Pande
 * Date : 05/05/20
 * Time : 1:08 AM
 */

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class NewsFeedRepositoryTest
{
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val local = NewsFeed().apply {
        id = 100
        strTitle = "Sakal"
        listOfNews.add(
            News(101, "India", "Pandemic Situation", "")
        )
        listOfNews.add(
            News(102, "Maharashtra", "More Pandemic Situation", "")
        )
    }

    private val remote = NewsFeed().apply {
        id = 100
        strTitle = "Sakal"
        listOfNews.add(
            News(103, "Pune", "Pandemic Situation", "")
        )
    }

    private lateinit var remoteDataSource: FakeDataSource
    private lateinit var localDataSource: FakeDataSource

    // Class under test
    private lateinit var newsFeedRepository: NewsFeedRepository

    @Before
    fun createRepository() {
        remoteDataSource = FakeDataSource(remote)
        localDataSource = FakeDataSource(local)
        // Get a reference to the class under test
        newsFeedRepository = NewsFeedRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            ioDispatcher = Dispatchers.Main
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestsAllNewsFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        // When tasks are requested from the tasks repository
        val newsFeedFromRemote = newsFeedRepository.fetchDataFromRemote()

        // Then tasks are loaded from the remote data source
        assertThat(newsFeedFromRemote, IsEqual(remote))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_deleteAllNewsFromLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // When tasks are requested from the tasks repository
        Assert.assertEquals(local.id, 100)
        Assert.assertEquals(local.strTitle, "Sakal")
        Assert.assertEquals(local.listOfNews.size, 2)

        newsFeedRepository.deleteDataFromDatabase()

        // Then tasks are loaded from the remote data source
        Assert.assertEquals(local.id, -1)
        Assert.assertEquals(local.strTitle, "")
        Assert.assertEquals(local.listOfNews.size, 0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_saveAllNewsToLocalDataSource() = mainCoroutineRule.runBlockingTest {
        val newsFeedFromRemote = newsFeedRepository.fetchDataFromRemote()

        newsFeedRepository.insertDataIntoDatabase(newsFeedFromRemote!!)
    }
}