package com.kprights.infosys.newsfeed.viewmodel

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kprights.infosys.newsfeed.common.DatabaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Copyright (c) 2020 for KPrights
 *
 *
 * User : Kiran Pande
 * Date : 05/05/20
 * Time : 1:08 AM
 */

@RunWith(AndroidJUnit4::class)
class NewsFeedRepositoryTest
{
    @ExperimentalCoroutinesApi
    val testCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup()
    {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testNewsFeedRepositoryTest() = runBlocking {

        val database = DatabaseService.getInstance(ApplicationProvider.getApplicationContext()).newsFeedDao
        val repository = NewsFeedRepository(database, testCoroutineDispatcher)

        val newsFeed = repository.fetchDataFromRemote()

        Assert.assertEquals(true, (newsFeed != null))
        Assert.assertEquals("About Canada", newsFeed?.strTitle)

        repository.deleteDataFromDatabase()

        Assert.assertEquals(true, (repository.newsFeed.value == null))

        repository.insertDataIntoDatabase(newsFeed!!)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tear()
    {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

}