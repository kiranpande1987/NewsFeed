package com.kprights.infosys.newsfeed.model

import com.google.gson.annotations.SerializedName


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 5:37 PM
 */

data class News(
    @SerializedName("title") val strTitle : String = "",
    @SerializedName("description") val strDescription : String = "",
    @SerializedName("imageHref") val strImageUrl : String = ""
)