package com.harzzy.trakcov.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.FragmentStatsBinding
import com.harzzy.trakcov.views.base.BaseFragment

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */
class StatsFragment : BaseFragment<FragmentStatsBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun getLayoutRes(): Int = R.layout.fragment_stats
}
