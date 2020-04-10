package com.kprights.infosys.newsfeed.common

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.model.NewsFeed
import com.kprights.infosys.newsfeed.view.fragment.NewsFeedListAdapter


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