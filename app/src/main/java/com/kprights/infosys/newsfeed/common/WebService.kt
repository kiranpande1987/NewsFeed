package com.kprights.infosys.newsfeed.common

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 11:40 PM
 */

private const val BASE_URL = "https://dl.dropboxusercontent.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

val WebService: Api by lazy { retrofit.create(Api::class.java) }

interface Api
{
    //
    // Put various Web API Calls of GET and POST
    //
    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getNewsFeed() : Deferred<NewsFeed>
}