package com.kprights.infosys.newsfeed.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kprights.infosys.newsfeed.common.DatabaseService
import com.kprights.infosys.newsfeed.databinding.FragmentNewsFeedBinding
import com.kprights.infosys.newsfeed.viewmodel.NewsFeedViewModel
import com.kprights.infosys.newsfeed.viewmodel.NewsFeedViewModelFactory


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 10/04/20
 * Time : 4:54 PM
 */

class NewsFeedFragment: Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentNewsFeedBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Set Database Access Object for NewsFeed
        val application = requireNotNull(this.activity).application
        val newsFeedViewModelFactory = NewsFeedViewModelFactory(this, DatabaseService.getInstance(application).newsFeedDao)
        val viewModel = ViewModelProvider(this, newsFeedViewModelFactory).get(NewsFeedViewModel::class.java)

        // Giving the binding access to the ViewModel
        binding.viewModel = viewModel

        binding.recyclerViewForNewsFeed.adapter = NewsFeedListAdapter(NewsFeedListAdapter.OnClickListener {
                this.findNavController().navigate(NewsFeedFragmentDirections.actionShowDetail(it))
        })

        viewModel.newsTitle.observe(viewLifecycleOwner, Observer {
            activity?.title = it
        })

        return binding.root
    }
}