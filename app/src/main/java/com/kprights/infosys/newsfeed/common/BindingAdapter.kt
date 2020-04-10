package com.kprights.infosys.newsfeed.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kprights.infosys.newsfeed.R
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import com.kprights.infosys.newsfeed.view.fragment.NewsFeedListAdapter
import com.kprights.infosys.newsfeed.viewmodel.NewsFeedViewModel


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
fun bindStatus(statusImageView: ImageView, status: NewsFeedViewModel.ApiStatus?) {
    when (status) {
        NewsFeedViewModel.ApiStatus.LOADING ->
        {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        NewsFeedViewModel.ApiStatus.ERROR ->
        {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        NewsFeedViewModel.ApiStatus.DONE ->
        {
            statusImageView.visibility = View.GONE
        }
    }
}