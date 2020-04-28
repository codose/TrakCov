package com.harzzy.trakcov.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.FragmentLocationBinding
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.adapter.CountryAdapter
import com.harzzy.trakcov.views.adapter.CountryClickListener
import com.harzzy.trakcov.views.base.BaseFragment
import com.harzzy.trakcov.views.viewmodels.CountryViewModel
import ng.educo.views.categories.CountryViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = CountryViewModelFactory(context!!)

        val viewModel = ViewModelProvider(activity!!, viewModelFactory)[CountryViewModel::class.java]

        val adapter = CountryAdapter(context!!, CountryClickListener {

        })
        binding.countryRecyclerView.adapter = adapter

        viewModel.countrydata.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success -> {
                    hideProgress()
                    adapter.submitList(it.data)
                }
                is Resource.Failure -> {
                    hideProgress()
                    showToast(it.message)
                }
            }
        })

    }
    override fun getLayoutRes(): Int = R.layout.fragment_location
}

