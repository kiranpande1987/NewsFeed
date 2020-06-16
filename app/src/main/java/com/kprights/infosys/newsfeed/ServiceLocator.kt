package com.kprights.infosys.newsfeed

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.kprights.infosys.newsfeed.common.DatabaseService
import com.kprights.infosys.newsfeed.viewmodel.IDataSource
import com.kprights.infosys.newsfeed.viewmodel.LocalDataSource
import com.kprights.infosys.newsfeed.viewmodel.NewsFeedRepository
import com.kprights.infosys.newsfeed.viewmodel.RemoteDataSource
import kotlinx.coroutines.runBlocking


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 04/06/20
 * Time : 12:27 AM
 */

object ServiceLocator {

    private val lock = Any()
    private var database: DatabaseService? = null

    @Volatile
    var repository: NewsFeedRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): NewsFeedRepository {
        synchronized(this) {
            return repository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): NewsFeedRepository {
        val newRepo = NewsFeedRepository(createTaskLocalDataSource(context), RemoteDataSource())
        repository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): IDataSource {
        return LocalDataSource(DatabaseService.getInstance(context).newsFeedDao)
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                //
            }
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }
    }
}