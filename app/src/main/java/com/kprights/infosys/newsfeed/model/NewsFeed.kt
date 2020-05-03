package com.kprights.infosys.newsfeed.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
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
    val id: Long = 0L,
    @SerializedName("title") var strTitle : String = "News Feed",
    @SerializedName("rows") var listOfNews : List<News> = listOf<News>()
){
    class Converters
    {

        @TypeConverter
        fun listToJson(value: List<News>) = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<News>::class.java).toList()
    }
}