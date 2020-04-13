package com.kprights.infosys.newsfeed.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kprights.infosys.newsfeed.common.DatabaseService
import com.kprights.infosys.newsfeed.databinding.FragmentNewsFeedBinding
import com.kprights.infosys.newsfeed.viewmodel.NewsFeedViewModel


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:54 PM
 */

class NewsFeedFragment: Fragment()
{
    private val viewModel: NewsFeedViewModel by lazy {
        ViewModelProvider(this).get(NewsFeedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentNewsFeedBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Set Database Access Object for NewsFeed
        val application = requireNotNull(this.activity).application
        viewModel.database = DatabaseService.getInstance(application).newsFeedDao
        viewModel.set(viewLifecycleOwner, DatabaseService.getInstance(application).newsFeedDao)

        binding.recyclerViewForNewsFeed.adapter = NewsFeedListAdapter()//(PhotoGridAdapter.OnClickListener { viewModel.displayPropertyDetails(it)})

        viewModel.newsTitle.observe(viewLifecycleOwner, Observer {
            activity?.title = it
        })

        return binding.root
    }
}