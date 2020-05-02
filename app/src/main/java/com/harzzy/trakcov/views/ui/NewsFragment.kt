package com.harzzy.trakcov.views.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.FragmentNewsBinding
import com.harzzy.trakcov.utils.Constants
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.adapter.ArticleClickListener
import com.harzzy.trakcov.views.adapter.NewsAdapter
import com.harzzy.trakcov.views.base.BaseFragment
import com.harzzy.trakcov.views.viewmodels.NewsViewModel
import com.harzzy.trakcov.views.viewmodels.NewsViewModelFactory
import com.pixplicity.easyprefs.library.Prefs

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */

class NewsFragment : BaseFragment<FragmentNewsBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutRes(), container, false)
        hideNavigationIcon()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = NewsViewModelFactory(context!!)

        val viewModel = ViewModelProvider(activity!!, viewModelFactory)[NewsViewModel::class.java]

        val adapter = NewsAdapter(context!!, ArticleClickListener {
            findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToWebViewFragment(it.url))
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getNewsData()
        }
        hideNetworkError()

        val linearLayoutManager = LinearLayoutManager(context)

        binding.newsRecyclerView.layoutManager = linearLayoutManager
        binding.newsRecyclerView.adapter = adapter

        viewModel.newsData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    if(!binding.swipeRefreshLayout.isRefreshing){
                        showProgress()
                    }
                }
                is Resource.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    hideProgress()
                    binding.newUpButton.setOnClickListener {
                        binding.nestedScrollView.smoothScrollTo(0,0)
                    }
                    adapter.submitList(it.data)
                }
                is Resource.Failure -> {
                    showNetworkError()
                    networkButton.setOnClickListener {
                        viewModel.getNewsData()
                    }
                    Log.e(Constants.NETWORK_TAG, it.message)
                }
            }
        })

        formatLayout(View.VISIBLE)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_news

}
