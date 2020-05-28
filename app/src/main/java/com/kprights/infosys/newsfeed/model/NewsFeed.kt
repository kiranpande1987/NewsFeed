package com.kprights.infosys.newsfeed.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:56 PM
 */

@Entity
data class NewsFeed(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @SerializedName("title") var strTitle : String = "News Feed",
    @SerializedName("rows") var listOfNews: MutableList<News> = emptyList<News>().toMutableList()
)