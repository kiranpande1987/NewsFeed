package com.kprights.infosys.newsfeed

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kprights.infosys.newsfeed.di.koinModules
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.AutoCloseKoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 16/06/20
 * Time : 7:29 AM
 */

@RunWith(AndroidJUnit4::class)
@Category(CheckModuleTest::class)
class KoinModuleAndroidTest : AutoCloseKoinTest() {

    val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun check() = checkModules {
        androidContext(context)
        modules(koinModules)
    }

}