package com.sideblind.newstest

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.sideblind.newstest.databinding.ActivityWebViewBinding
import web.WebBridge

class WebViewActivity : AppCompatActivity() {
    private lateinit var webUrl: String
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webUrl = intent.getStringExtra("webUrl")!!
        setWebView()
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    private fun setWebView() {
        val webViewSettings = binding.webview.settings
        webViewSettings.javaScriptEnabled = true
        webViewSettings.domStorageEnabled = true // 로컬 스토리지 등 브라우저 저장소 활성화
        webViewSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        binding.webview.setLayerType(View.LAYER_TYPE_HARDWARE, null) // 하드웨어 가속 활성
        binding.webview.webViewClient = WebViewClient()
        webViewSettings.allowFileAccess = true
        val cookieManager = CookieManager.getInstance()
        webViewSettings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        binding.webview.addJavascriptInterface(WebBridge(this), "UUID")
        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
                Log.e(
                    "error : ",
                    cm.message() + " -- From line "
                            + cm.lineNumber() + " of "
                            + cm.sourceId()
                )
                return true
            }
        }
        if (0 != (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        binding.webview.loadUrl(webUrl)
    }
}