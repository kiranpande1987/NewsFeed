package com.kprights.infosys.newsfeed

import android.app.Application
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
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}