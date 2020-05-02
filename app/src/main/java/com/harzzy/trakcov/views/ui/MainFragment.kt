package com.harzzy.trakcov.views.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar

import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.FragmentMainBinding
import com.harzzy.trakcov.utils.Constants
import com.harzzy.trakcov.utils.Constants.NETWORK_TAG
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.adapter.MainAdapter
import com.harzzy.trakcov.views.adapter.StateClickListener
import com.harzzy.trakcov.views.base.BaseFragment
import com.harzzy.trakcov.views.viewmodels.MainViewModel
import com.pixplicity.easyprefs.library.Prefs
import com.harzzy.trakcov.views.viewmodels.MainViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        // Inflate the layout for this fragment
        hideNavigationIcon()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formatLayout(VISIBLE)
        val viewModelFactory = MainViewModelFactory(context!!)
        viewModel = ViewModelProvider(activity!!,viewModelFactory)[MainViewModel::class.java]
        val appbar = activity!!.findViewById<MaterialToolbar>(R.id.topAppBar)
        appbar.visibility = VISIBLE
        hideNetworkError()

        val adapter = MainAdapter(context!!, StateClickListener {

        })

        setPrefsToNull()

        binding.statesRecyclerView.adapter = adapter

        binding.worldWideCardView.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSingleCountryFragment("Global"))
        }

        binding.mainSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllData()
        }

        binding.nameTextView.text = Prefs.getString("USERNAME", "Not Set")

        viewModel.nigeriaData.observe(activity!! , Observer {
            when(it){
                is Resource.Loading -> {
                    if(!binding.mainSwipeRefreshLayout.isRefreshing){
                        showProgress()
                    }
                }

                is Resource.Success -> {
                    hideProgress()
                    binding.apply {
                        numberCases.text = it.data.cases.toString()
                        activeCases.text = it.data.active.toString()
                        recovered.text = it.data.recovered.toString()
                        deaths.text = it.data.deaths.toString()
                    }
                    binding.mainSwipeRefreshLayout.isRefreshing = false
                    if(Prefs.getString("USERNAME","Not Set") == "Not Set"){
                        findNavController().navigate(MainFragmentDirections.actionMainFragmentToStarterFragment())
                    }
                }

                is Resource.Failure -> {
                    showNetworkError()
                    Log.e(NETWORK_TAG,it.message)
                    networkButton.setOnClickListener {
                        viewModel.getAllData()
                    }
                }
            }
        })

        viewModel.stateData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading ->{
                    hideError()
                }
                is Resource.Success -> {
                    adapter.submitList(it.data)
                    binding.mainSwipeRefreshLayout.isRefreshing = false
                    binding.errorProgressBar.visibility = GONE
                }
                is Resource.Failure -> {
                    Log.e(NETWORK_TAG,it.message)
                    showToast("Unable to load states data")
                    showError()
                }
            }
        })

        viewModel.globalData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    binding.apply {
                        casesWorld.text = it.data.cases.toString()
                        activeCasesWorld.text = it.data.active.toString()
                        recoveredCasesWorld.text = it.data.recovered.toString()
                        deathCasesWorld.text = it.data.deaths.toString()
                        affectedCountriesTextView.text = it.data.affectedCountries.toString()
                        totalTestsTextView.text = it.data.tests.toString()
                        todayCasesTextView.text = it.data.todayCases.toString()
                        todayDeathsTextView.text = it.data.todayDeaths.toString()
                        binding.mainSwipeRefreshLayout.isRefreshing = false
                    }
                }
            }
        })
    }

    private fun showError() {
        binding.apply {
            errorImage.visibility = VISIBLE
            errorText.visibility = VISIBLE
            errorProgressBar.visibility = GONE
            errorText.setOnClickListener {
                binding.mainSwipeRefreshLayout.isRefreshing = true
                viewModel.getAllData()
            }
        }
    }

    private fun hideError() {
        binding.apply {
            errorImage.visibility = INVISIBLE
            errorText.visibility = INVISIBLE
            errorProgressBar.visibility = VISIBLE
        }
    }

    private fun setPrefsToNull() {
        Prefs.putString(Constants.PREFS_QUERY, Constants.PREFS_QUERY_NULL)
    }



    override fun getLayoutRes(): Int = R.layout.fragment_main

}
