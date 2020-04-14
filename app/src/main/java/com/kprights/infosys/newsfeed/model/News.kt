package com.kprights.infosys.newsfeed.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 5:37 PM
 */

@Parcelize
@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @SerializedName("title") val strTitle : String? = "",
    @SerializedName("description") val strDescription : String? = "",
    @SerializedName("imageHref") val strImageUrl : String? = ""
) : Parcelable