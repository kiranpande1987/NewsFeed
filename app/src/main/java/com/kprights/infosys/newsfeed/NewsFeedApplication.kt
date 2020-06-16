package com.kprights.infosys.newsfeed

import android.app.Application
import com.kprights.infosys.newsfeed.di.koinModules
import com.kprights.infosys.newsfeed.viewmodel.INewsFeedRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 18/04/20
 * Time : 11:02 AM
 */

class NewsFeedApplication: Application() {

    val repository: INewsFeedRepository
        get() = ServiceLocator.provideRepository(this)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@NewsFeedApplication)
            modules(koinModules)
        }
    }
}