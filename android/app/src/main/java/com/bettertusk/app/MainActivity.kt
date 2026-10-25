package com.bettertusk.app

import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader

// Proper Android host, following Better DeepSeek's model:
// a single WebView loads the real site (here new.tusksearch.com/ai)
// and injects the same v3 skin bundle used by the browser extension.
// Sign-in stays inside the WebView session, so Clerk/Google
// full-page redirects work without losing cookies.
class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        val script = assets.open("better-tusk.js").bufferedReader().use(BufferedReader::readText)
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (url.contains("tusksearch.com") || url.contains("tuskcentral.ai")) {
                    view.evaluateJavascript(script, null)
                }
            }
        }
        if (savedInstanceState == null) {
            webView.loadUrl("https://new.tusksearch.com/ai/")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}
