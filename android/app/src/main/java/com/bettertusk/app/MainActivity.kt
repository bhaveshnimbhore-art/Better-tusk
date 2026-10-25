package com.bettertusk.app

import android.graphics.Color
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader

// Proper Android host, following Better DeepSeek's model:
// a single WebView loads the real site (here new.tusksearch.com/ai/)
// and injects the same v3 skin bundle used by the browser extension.
// Sign-in stays inside the WebView session, so Clerk/Google
// full-page redirects work without losing cookies.
// Black-screen hardening: white WebView background, hardware
// acceleration via manifest, mixed-content allowed, wide viewport,
// and all tusk-domain navigations kept inside the WebView.
class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        webView.setBackgroundColor(Color.WHITE)
        setContentView(webView)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        val script = assets.open("better-tusk.js").bufferedReader().use(BufferedReader::readText)
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val host = request.url.host ?: return false
                // Keep Tusk + sign-in hosts inside the WebView; everything else loads normally too
                // (returning false lets the WebView handle it, never a blank external intent).
                return false
            }
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.setBackgroundColor(Color.WHITE)
                if (url.contains("tusksearch.com") || url.contains("tuskcentral.ai")) {
                    try { view.evaluateJavascript(script, null) } catch (e: Exception) { /* skin is optional, page must still show */ }
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
