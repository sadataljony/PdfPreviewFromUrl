package com.sadataljony.app.android.pdfpreviewfromurl

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sadataljony.app.android.pdfpreviewfromurl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadTermsAndConditions()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadTermsAndConditions() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading PDF...")
        progressDialog.setCancelable(false)

        binding.webView.requestFocus()
        binding.webView.settings.javaScriptEnabled = true
        val strPdfContentUrl =
            "http://passport.gov.bd/Reports/MRP_Application_Form[Hard%20Copy].pdf"
        val url =
            "https://docs.google.com/viewer?embedded=true&url=$strPdfContentUrl"
        binding.webView.loadUrl(url)

        object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }.also { binding.webView.webViewClient = it }

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100) {
                    progressDialog.show()
                }
                if (progress == 100) {
                    progressDialog.dismiss()
                }
            }
        }
    }
}