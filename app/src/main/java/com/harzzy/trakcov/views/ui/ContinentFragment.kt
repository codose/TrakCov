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

import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.FragmentContinentBinding
import com.harzzy.trakcov.utils.Constants
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.adapter.ContinentAdapter
import com.harzzy.trakcov.views.adapter.ContinentClickListener
import com.harzzy.trakcov.views.base.BaseFragment
import com.harzzy.trakcov.views.viewmodels.CountryViewModel
import com.harzzy.trakcov.views.viewmodels.CountryViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class ContinentFragment : BaseFragment<FragmentContinentBinding>() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_continent, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = CountryViewModelFactory(context!!)
        hideNavigationIcon()
        formatLayout(View.VISIBLE)

        val viewModel = ViewModelProvider(activity!!, viewModelFactory)[CountryViewModel::class.java]

        val adapter = ContinentAdapter(context!!, ContinentClickListener {
            findNavController().navigate(ContinentFragmentDirections.actionContinentFragmentToLocationFragment(it.countries.toTypedArray(),it.continent))
        })

        binding.continentSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getContinents()
        }

        binding.continentRecyclerView.adapter = adapter

        viewModel.continentData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    if(!binding.continentSwipeRefreshLayout.isRefreshing){
                        showProgress()
                    }

                }
                is Resource.Success -> {
                    adapter.submitList(it.data)
                    binding.continentSwipeRefreshLayout.isRefreshing = false
                    hideProgress()
                }
                is Resource.Failure -> {
                    showNetworkError()
                    networkButton.setOnClickListener {
                        viewModel.getContinents()
                    }
                    Log.e(Constants.NETWORK_TAG, it.message)
                }
            }
        })
    }

    override fun getLayoutRes(): Int = R.layout.fragment_continent

}
