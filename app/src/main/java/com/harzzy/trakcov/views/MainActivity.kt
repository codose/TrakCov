package com.harzzy.trakcov.views

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import br.com.mauker.materialsearchview.MaterialSearchView
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.ActivityMainBinding
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener {

    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_fragment)
        searchView = findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.closeSearch()
                Prefs.putString("Query", query)
                navController.navigate(R.id.locationFragment)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })


        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        val doubleBounce = DoubleBounce()
        doubleBounce.color = ContextCompat.getColor(applicationContext!!, R.color.colorPrimary)
        val progressBar = binding.progressBar
        progressBar.indeterminateDrawable = doubleBounce

        binding.topAppBar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.app_bar_search -> {
                    searchView.openSearch()
                    true
                }
                else -> false
            }
        }


    }

    override fun onBackPressed() {
        if (searchView.isOpen) { // Close the search on the back button press.
            searchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }


    private fun showToast(s: String) {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show()
    }




    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return true
    }
}
