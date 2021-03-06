package com.harzzy.trakcov.views.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.APIlib
import com.anychart.AnyChartView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.harzzy.trakcov.R
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.databinding.FragmentSingleCountryBinding
import com.harzzy.trakcov.utils.ChartSetup
import com.harzzy.trakcov.utils.Constants.NETWORK_TAG
import com.harzzy.trakcov.utils.Constants.PREFS_QUERY
import com.harzzy.trakcov.utils.Constants.PREFS_QUERY_NULL
import com.harzzy.trakcov.utils.Converter.formatDate
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.base.BaseFragment
import com.harzzy.trakcov.views.viewmodels.CountryViewModel
import com.pixplicity.easyprefs.library.Prefs
import com.harzzy.trakcov.views.viewmodels.CountryViewModelFactory


/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */
class SingleCountryFragment : BaseFragment<FragmentSingleCountryBinding>() {

    lateinit var pieChart : AnyChartView

    lateinit var lineChart : AnyChartView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate( inflater, getLayoutRes(), container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavigationIcon()
        formatLayout(GONE)
        val args = SingleCountryFragmentArgs.fromBundle(arguments!!)
        hideNetworkError()
        Prefs.putString(PREFS_QUERY, "String")

        pieChart = binding.pieChart
        pieChart.setProgressBar(binding.chartProgress)

        lineChart = binding.lineChart


        val countryName = args.countryName
        val factory = CountryViewModelFactory(context!!)
        val viewModel = ViewModelProvider(this, factory)[CountryViewModel::class.java]

        if(countryName == "Global"){
            viewModel.getGlobalData()
        }else {
            viewModel.searchCountry(countryName)
        }

        viewModel.countryData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success -> {
                    val countryData = it.data[0]
                    setCountryData(countryData)
                    viewModel.getTimeline(countryName)
                    Prefs.putString(PREFS_QUERY, PREFS_QUERY_NULL)
                }
                is Resource.Failure -> {
                    showNetworkError()
                    networkButton.setOnClickListener {
                        viewModel.searchCountry(countryName)
                    }
                    Log.e(NETWORK_TAG,it.message)
                }
            }
        })

        viewModel.globalData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading ->{
                    showProgress()
                }
                is Resource.Success -> {
                    val data = it.data
                    APIlib.getInstance().setActiveAnyChartView(pieChart)
                    pieChart.setChart(ChartSetup.setUpPieChart(data.active, data.deaths, data.recovered))
                    binding.apply {
                        countryNameTextView.text = "Global"
                        totalCasesTxtView.text = "${data.cases} confirmed cases"
                        singleCasesTextView.text = data.cases.toString()
                        activeCasesTxtView.text = data.active.toString()
                        singleRecoveryTextView.text = data.recovered.toString()
                        singleDeathsTextView.text = data.deaths.toString()
                        todayCases.text = data.todayCases.toString()
                        todayDeaths.text = data.todayDeaths.toString()
                        casesPerMillion.text = data.casesPerOneMillion.toString()
                        deathsPerMillion.text = data.deathsPerOneMillion.toString()
                    }
                    viewModel.getGlobalTimeline()
                }

                is Resource.Failure -> {
                    showNetworkError()
                    networkButton.setOnClickListener {
                        viewModel.getGlobalData()
                    }
                    Log.e(NETWORK_TAG,it.message)
                }
            }
        })

        viewModel.countryTrend.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success -> {
                    val data = it.data
                    APIlib.getInstance().setActiveAnyChartView(lineChart)
                    val cartesian = ChartSetup.setUpLineChart(data)
                    lineChart.setChart(cartesian)
                    hideProgress()
                }
                is Resource.Failure -> {
                    showNetworkError()
                    networkButton.setOnClickListener {
                        viewModel.searchCountry(countryName)
                    }
                    showToast(it.message)
                    Log.e(NETWORK_TAG,it.message)
                }
            }
        })
    }

    private fun setCountryData(countryData: InternationalResponseItem) {
        binding.apply {
            totalCasesTxtView.text = "${countryData.cases} confirmed cases"
            countryNameTextView.text = countryData.country
            singleCasesTextView.text = countryData.cases.toString()
            singleDeathsTextView.text = countryData.deaths.toString()
            singleRecoveryTextView.text = countryData.recovered.toString()
            activeCasesTxtView.text = countryData.active.toString()
            todayCases.text = countryData.todayCases.toString()
            todayDeaths.text = countryData.todayDeaths.toString()
            casesPerMillion.text = countryData.casesPerOneMillion.toString()
            deathsPerMillion.text = countryData.deathsPerOneMillion.toString()
            updatedTextView.text = "Last Updated : ${formatDate(countryData.updated)}"
        }
        APIlib.getInstance().setActiveAnyChartView(pieChart)
        pieChart.setChart(ChartSetup.setUpPieChart(countryData.active,countryData.deaths,countryData.recovered))
        Glide.with(context!!)
            .load(countryData.countryInfo.flag)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.flagImageView)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_single_country
}
