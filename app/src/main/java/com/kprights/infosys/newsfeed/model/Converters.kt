package com.kprights.infosys.newsfeed.model

import androidx.room.TypeConverter
import com.google.gson.Gson


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 05/05/20
 * Time : 12:00 AM
 */

class Converters
{
    @TypeConverter
    fun listToJson(value: List<News>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<News>::class.java).toList()
}