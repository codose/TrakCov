package com.harzzy.trakcov.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.ActivityMainBinding
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.countryData.observe(this, Observer {
            when(it){
                is Resource.Loading -> {
                    binding.progressBar.visibility = VISIBLE
                }

                is Resource.Success -> {

                    binding.apply {
                        binding.progressBar.visibility = GONE
                        numberCases.text = it.data.total_cases.toString()
                        activeCases.text = it.data.total_active_cases.toString()
                        recovered.text = it.data.total_recovered.toString()
                        deaths.text = it.data.total_deaths.toString()
                        newCases.text = it.data.total_new_cases_today.toString()
                    }

                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = GONE
                    binding.errorText.text = it.message
                }
            }
        })

    }
}
