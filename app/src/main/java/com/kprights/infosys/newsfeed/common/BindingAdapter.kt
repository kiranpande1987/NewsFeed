package com.kprights.infosys.newsfeed.common

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kprights.infosys.newsfeed.R
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.view.fragment.NewsFeedListAdapter
import com.kprights.infosys.newsfeed.viewmodel.INewsFeedRepository


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 11/04/20
 * Time : 12:28 AM
 */

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<News>?) {
    val adapter = recyclerView.adapter as NewsFeedListAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: INewsFeedRepository.ApiStatus?) {
    when (status) {
        INewsFeedRepository.ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        INewsFeedRepository.ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        INewsFeedRepository.ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("showImage")
fun showImage(imageViewForNewsFeed: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("http").build()

        Glide.with(imageViewForNewsFeed.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageViewForNewsFeed)
    }
}