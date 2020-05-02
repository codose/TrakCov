package com.harzzy.trakcov.views.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.FadingCircle
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.harzzy.trakcov.R
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment<DB : ViewDataBinding> : Fragment(){
    open lateinit var binding: DB
    lateinit var doubleBounce : DoubleBounce
    private val progressBar: ProgressBar by lazy {
        activity?.findViewById<ProgressBar>(R.id.progressBar)!!
    }
    val fragment : FrameLayout by lazy {
        activity?.findViewById<FrameLayout>(R.id.frame_layout)!!
    }
    private val networkLayout : FrameLayout by lazy{
        activity?.findViewById<FrameLayout>(R.id.network_frame)!!
    }

    val networkButton : Button by lazy {
        activity?.findViewById<Button>(R.id.networ_error_retry_button)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doubleBounce = DoubleBounce()
        doubleBounce.color = ContextCompat.getColor(context!!, R.color.colorPrimary)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init(inflater, container)
        super.onCreateView(inflater, container, savedInstanceState)
        progressBar.indeterminateDrawable = doubleBounce
        return binding.root
    }


    private fun init(inflater : LayoutInflater, container : ViewGroup?){
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    fun showToast(message : String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(message: String){
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

    fun showProgress() {
        progressBar.visibility = VISIBLE
        fragment.visibility = INVISIBLE
        networkLayout.visibility = GONE

    }

    fun hideProgress() {
        progressBar.visibility = GONE
        fragment.visibility = VISIBLE
    }
    fun showNetworkError(){
        progressBar.visibility = GONE
        networkLayout.visibility = VISIBLE
    }
    fun hideNetworkError(){
        networkLayout.visibility = GONE
    }

    fun formatLayout(view : Int){
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView!!.visibility = view
    }

    fun showNavigationIcon(){
        val appbar = activity!!.findViewById<MaterialToolbar>(R.id.topAppBar)
        appbar.setNavigationIcon(R.drawable.back)
        appbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
    }

    fun hideNavigationIcon(){
        val appbar = activity!!.findViewById<MaterialToolbar>(R.id.topAppBar)
        appbar.navigationIcon = null
    }


}
