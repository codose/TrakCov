package com.harzzy.trakcov.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.FragmentStarterBinding
import com.harzzy.trakcov.views.base.BaseFragment
import com.pixplicity.easyprefs.library.Prefs

/**
 * A simple [Fragment] subclass.
 */
class StarterFragment : BaseFragment<FragmentStarterBinding>() {

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
        formatLayout(GONE)
        if(Prefs.getString("USERNAME","Not Set") != "Not Set"){
            findNavController().navigate(StarterFragmentDirections.actionStarterFragmentToMainFragment())
        }
        binding.nextButton.setOnClickListener {
            val name = binding.nameTextInputEditText.text.toString()
            if(name.isNotBlank()){
                Prefs.putString("USERNAME",name)
                findNavController().navigate(StarterFragmentDirections.actionStarterFragmentToMainFragment())
            }else{
                binding.nameTextInputLayout.error = "Please input your name"
            }
        }
    }


    override fun getLayoutRes(): Int = R.layout.fragment_starter
}
