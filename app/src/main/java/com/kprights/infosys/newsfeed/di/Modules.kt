package com.kprights.infosys.newsfeed.di

import com.kprights.infosys.newsfeed.common.DatabaseService
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import com.kprights.infosys.newsfeed.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 14/06/20
 * Time : 7:51 PM
 */

val newsModule = module {
    single { NewsFeed() }
    factory { News() }
}

val newsFeedViewModel = module {

    single { DatabaseService.getInstance(androidContext()).newsFeedDao }

    single { LocalDataSource(get()) }
    single { RemoteDataSource() }

    single<INewsFeedRepository> {
        NewsFeedRepository(
            get<LocalDataSource>(),
            get<RemoteDataSource>()
        )
    }
    viewModel { NewsFeedViewModel(get()) }
}

val koinModules = listOf(newsModule, newsFeedViewModel)