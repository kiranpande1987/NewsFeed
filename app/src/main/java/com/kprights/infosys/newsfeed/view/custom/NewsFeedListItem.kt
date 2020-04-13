package com.kprights.infosys.newsfeed.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kprights.infosys.newsfeed.R
import com.kprights.infosys.newsfeed.databinding.NewsFeedListItemBinding
import com.kprights.infosys.newsfeed.model.News
import com.kprights.infosys.newsfeed.view.fragment.NewsFeedListAdapter


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:56 PM
 */

class NewsFeedListItem(private val binding: NewsFeedListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(item: News, onClickListener: NewsFeedListAdapter.OnClickListener) {
        binding.news = item
        binding.onClickListener = onClickListener
        binding.executePendingBindings()
    }

    companion object
    {
        fun from(parent: ViewGroup): NewsFeedListItem
        {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NewsFeedListItemBinding.inflate(layoutInflater, parent, false)
            return NewsFeedListItem(binding)
        }

        @BindingAdapter("showImage")
        @JvmStatic
        fun showImage(imageViewForNewsFeed: ImageView, imageUrl: String?) {
            imageUrl?.let {
                val imgUri = imageUrl.toUri().buildUpon().scheme("http").build()

                Glide.with(imageViewForNewsFeed.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image))
                    .into(imageViewForNewsFeed)
            }
        }
    }
}