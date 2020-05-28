package com.kprights.infosys.newsfeed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.kprights.infosys.newsfeed.common.DatabaseService
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.`is` as matchersIs


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 07/05/20
 * Time : 11:38 PM
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsFeedDaoTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: DatabaseService

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            DatabaseService::class.java
        ).allowMainThreadQueries().build()
    }


    @Test
    fun insertTaskAndGetById() = runBlockingTest {
        // GIVEN - Insert a task.

        val newsFeed = NewsFeed().apply {
            strTitle = "News Feed Title"
            listOfNews.add(News().apply {
                strTitle = "TestNews"
                strImageUrl = "ImURL"
                strDescription = "Description"
            })
        }

        database.newsFeedDao.insert(newsFeed)

        val loaded = database.newsFeedDao.getNewsFeed()

        // THEN - The loaded data contains the expected values.
        assertThat<NewsFeed>(loaded, notNullValue())
        assertThat(loaded.strTitle, matchersIs(newsFeed.strTitle))
        assertThat(loaded.listOfNews.isNotEmpty(), matchersIs(true))

        assertThat(
            loaded.listOfNews.get(0).strTitle,
            matchersIs(newsFeed.listOfNews.get(0).strTitle)
        )
        assertThat(
            loaded.listOfNews.get(0).strImageUrl,
            matchersIs(newsFeed.listOfNews.get(0).strImageUrl)
        )
        assertThat(
            loaded.listOfNews.get(0).strDescription,
            matchersIs(newsFeed.listOfNews.get(0).strDescription)
        )

    }


    @After
    fun closeDb() = database.close()

}