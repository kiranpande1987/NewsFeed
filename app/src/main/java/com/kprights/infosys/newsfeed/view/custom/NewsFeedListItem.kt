package com.kprights.infosys.newsfeed.view.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kprights.infosys.newsfeed.databinding.NewsFeedListItemBinding
import com.kprights.infosys.newsfeed.model.News


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:56 PM
 */

class NewsFeedListItem(private val binding: NewsFeedListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(item: News) {
        binding.news = item

//        binding.clickListener = clickListener
//        binding.executePendingBindings()
    }

    companion object
    {
        fun from(parent: ViewGroup): NewsFeedListItem
        {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NewsFeedListItemBinding.inflate(layoutInflater, parent, false)
            return NewsFeedListItem(binding)
        }
    }
}