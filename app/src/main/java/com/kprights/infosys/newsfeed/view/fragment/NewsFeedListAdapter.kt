package com.kprights.infosys.newsfeed.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.view.custom.NewsFeedListItem


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:55 PM
 */

class NewsFeedListAdapter(private val listOfNews: List<News>): ListAdapter<News, NewsFeedListItem>(NewsFeedDiffCallback())  //: RecyclerView.Adapter<NewsFeedListItem>()
{
    // To create News Feed List Item View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsFeedListItem.from(parent)

    // Get Total number of News from @listOfNews
    override fun getItemCount() = listOfNews.size

    // Bind Single News to NewsListItem
    override fun onBindViewHolder(holder: NewsFeedListItem, position: Int) = holder.bind(listOfNews[position])
}

// New Difference Callback : To check differences between old list and new list of News.
class NewsFeedDiffCallback: DiffUtil.ItemCallback<News>()
{
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.strTitle == newItem.strTitle
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}