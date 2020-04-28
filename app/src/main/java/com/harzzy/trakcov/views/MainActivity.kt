package com.harzzy.trakcov.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.FadingCircle
import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.ActivityMainBinding
import com.harzzy.trakcov.utils.Resource
import com.harzzy.trakcov.views.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        val doubleBounce = DoubleBounce()
        doubleBounce.color = ContextCompat.getColor(applicationContext!!, R.color.colorPrimary)
        val progressBar = binding.progressBar
        progressBar.indeterminateDrawable = doubleBounce

    }
}
