package com.harzzy.trakcov.utils

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient(var url : String) : WebViewClient() {

    var loading = true
    var redirect = false

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

        if(!loading){
            redirect = true
        }
        loading = false
        view?.loadUrl(url)

        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        loading =  false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if(!redirect){
            loading = true
        } else {
            redirect = false
        }
    }
}