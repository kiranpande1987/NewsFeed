package com.kprights.infosys.newsfeed.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kprights.infosys.newsfeed.databinding.NewsDetailFragmentBinding


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 13/04/20
 * Time : 11:12 PM
 */

class NewsDetailFragment: Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = NewsDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val news = NewsDetailFragmentArgs.fromBundle(arguments!!).news
        binding.news = news

        return binding.root
    }
}