package com.harzzy.trakcov.views.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.harzzy.trakcov.R
import com.harzzy.trakcov.databinding.FragmentWebViewBinding
import com.harzzy.trakcov.views.base.BaseFragment


/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */

class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formatLayout(GONE)
        hideNetworkError()
        showNavigationIcon()
        val appbar = activity!!.findViewById<MaterialToolbar>(R.id.topAppBar)
        appbar.menu.setGroupVisible(0, false)
        val args = WebViewFragmentArgs.fromBundle(arguments!!)
        val url = args.url
        val webView = binding.newsWebView
        webView.settings.javaScriptEnabled = true
        binding.webViewProgress.visibility = VISIBLE
        binding.webViewProgress.progress = webView.progress
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                binding.webViewProgress.visibility = GONE
            }
        }
        var loadingFinished = true
        var redirect = false

//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
//                if (!loadingFinished) {
//                    redirect = true
//                }
//                loadingFinished = false
//                webView.loadUrl(url)
//                return true
//            }
//
//            override fun onPageStarted(
//                view: WebView, url: String, favicon: Bitmap
//            ) {
//                super.onPageStarted(view, url, favicon)
//                loadingFinished = false
//            }
//
//            override fun onPageFinished(view: WebView, url: String) {
//                if (!redirect) {
//                    loadingFinished = true
//                    binding.webViewProgress.visibility = GONE
//                } else {
//                    redirect = false
//                }
//            }
//        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_web_view

}
