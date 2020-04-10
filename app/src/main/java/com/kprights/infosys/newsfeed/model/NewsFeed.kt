package com.kprights.infosys.newsfeed.model

import com.google.gson.annotations.SerializedName


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:56 PM
 */

data class NewsFeed(
    @SerializedName("title") val strTitle : String = "",
    @SerializedName("rows") val listOfNews : List<News> = listOf<News>()
)