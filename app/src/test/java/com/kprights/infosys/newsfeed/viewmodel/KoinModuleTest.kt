package com.kprights.infosys.newsfeed.viewmodel

import com.google.common.base.Verify
import com.kprights.infosys.newsfeed.di.newsModule
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.koin.test.inject


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 14/06/20
 * Time : 8:12 PM
 */

@RunWith(JUnit4::class)
@Category(CheckModuleTest::class)
class KoinModuleTest : AutoCloseKoinTest() {

    @Test
    fun checkNonContextModules() = checkModules {
        modules(newsModule)
    }

    @Test
    fun newsFeedModule() {
        val newsFeed: NewsFeed by inject()
        val news: News by inject()

        startKoin {
            modules(newsModule)
        }

        Verify.verifyNotNull(newsFeed)
        Verify.verifyNotNull(news)
    }
}