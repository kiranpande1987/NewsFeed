package com.kprights.infosys.newsfeed.view.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.view.custom.NewsFeedListItem


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:55 PM
 */

class NewsFeedListAdapter(val onClickListener: OnClickListener): ListAdapter<News, NewsFeedListItem>(NewsFeedDiffCallback())  //: RecyclerView.Adapter<NewsFeedListItem>()
{
    // To create News Feed List Item View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsFeedListItem.from(parent)

    // Bind Single News to NewsListItem
    override fun onBindViewHolder(holder: NewsFeedListItem, position: Int) {
        val news = getItem(position)
        holder.bind(news, onClickListener)
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

    class OnClickListener(val clickListener: (news: News) -> Unit) {
        fun onClick(news: News) = clickListener(news)
    }
}

