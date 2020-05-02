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
import com.harzzy.trakcov.databinding.FragmentLocationBinding
import com.harzzy.trakcov.utils.Constants.NETWORK_TAG
import com.harzzy.trakcov.utils.Constants.PREFS_QUERY
import com.harzzy.trakcov.utils.Constants.PREFS_QUERY_NULL
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.adapter.CountryAdapter
import com.harzzy.trakcov.views.adapter.CountryClickListener
import com.harzzy.trakcov.views.base.BaseFragment
import com.harzzy.trakcov.views.viewmodels.CountryViewModel
import com.pixplicity.easyprefs.library.Prefs
import com.harzzy.trakcov.views.viewmodels.CountryViewModelFactory
/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */
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
        showNavigationIcon()
        formatLayout(View.GONE)

        val args = arguments?.let { LocationFragmentArgs.fromBundle(it) }

        val viewModel = ViewModelProvider(activity!!, viewModelFactory)[CountryViewModel::class.java]

        val query = Prefs.getString(PREFS_QUERY, PREFS_QUERY_NULL)

        hideNetworkError()

        if(args?.countries != null){
            val countries : ArrayList<String> = ArrayList()
            val n = args.countries.size
            countries.addAll(args.countries)
            countries.add(0, args.countries[0])
            countries.add(n, args.countries[n-1])
            binding.continentName.text = args.continentName
            viewModel.getContinentCountries(countries)
        }else{
            viewModel.searchCountry(query)
        }

        val adapter = CountryAdapter(context!!, CountryClickListener {
            findNavController().navigate(LocationFragmentDirections.actionLocationFragmentToSingleCountryFragment(it.country))
        })

        binding.countryRecyclerView.adapter = adapter

        viewModel.countryData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                    setPrefsToNull()
                }
                is Resource.Success -> {
                    hideProgress()
                    val sortedList = it.data.sortedByDescending { (_, value) -> value}
                    adapter.submitList(sortedList)
                    if(sortedList.size == 1){
                        binding.continentName.text = sortedList[0].continent
                    }

                }
                is Resource.Failure -> {
                    showNetworkError()
                    networkButton.setOnClickListener {
                        viewModel.getCountryData()
                    }
                    Log.e(NETWORK_TAG , it.message)
                }
            }
        })

    }

    private fun setPrefsToNull() {
        Prefs.putString(PREFS_QUERY, PREFS_QUERY_NULL)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_location
}

