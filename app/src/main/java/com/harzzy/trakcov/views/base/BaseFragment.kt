package com.harzzy.trakcov.views.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.FadingCircle
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
    val progressBar: ProgressBar by lazy {
        activity?.findViewById<ProgressBar>(R.id.progressBar)!!
    }
    val fragment : FrameLayout by lazy {
        activity?.findViewById<FrameLayout>(R.id.frame_layout)!!
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
        progressBar.visibility = View.VISIBLE
        fragment.visibility = View.INVISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = GONE
        fragment.visibility = View.VISIBLE
    }

    fun formatLayout(view : Int){
        val logo = activity?.findViewById<ImageView>(R.id.imageView)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        logo!!.visibility = view
        bottomNavigationView!!.visibility = view
    }


}
