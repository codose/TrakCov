package com.harzzy.trakcov.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.FragmentMainBinding
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.adapter.MainAdapter
import com.harzzy.trakcov.views.adapter.StateClickListener
import com.harzzy.trakcov.views.base.BaseFragment
import com.harzzy.trakcov.views.viewmodels.MainViewModel
import com.pixplicity.easyprefs.library.Prefs
import ng.educo.views.categories.MainViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment<FragmentMainBinding>() {


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
        formatLayout(VISIBLE)
        val viewModelFactory = MainViewModelFactory(context!!)
        val viewModel = ViewModelProvider(activity!!,viewModelFactory)[MainViewModel::class.java]

        val adapter = MainAdapter(context!!, StateClickListener {

        })

        binding.statesRecyclerView.adapter = adapter

        binding.nameTextView.text = Prefs.getString("USERNAME", "Not Set")

        viewModel.countryData.observe(activity!! , Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }

                is Resource.Success -> {
                    hideProgress()
                    binding.apply {
                        numberCases.text = it.data.total_cases.toString()
                        activeCases.text = it.data.total_active_cases.toString()
                        recovered.text = it.data.total_recovered.toString()
                        deaths.text = it.data.total_deaths.toString()
                    }
                }

                is Resource.Failure -> {
                    hideProgress()
                }
            }
        })

        viewModel.stateData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    adapter.submitList(it.data)
                }
            }
        })

        viewModel.globalData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    binding.apply {
                        casesWorld.text = it.data.total_cases.toString()
                        activeCasesWorld.text = it.data.total_active_cases.toString()
                        recoveredCasesWorld.text = it.data.total_recovered.toString()
                        deathCasesWorld.text = it.data.total_deaths.toString()
                    }
                }
            }
        })
    }



    override fun getLayoutRes(): Int = R.layout.fragment_main

}
