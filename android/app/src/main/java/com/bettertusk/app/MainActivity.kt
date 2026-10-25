package com.bettertusk.app
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = WebView(this)
        setContentView(webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        // Cookies stay in the WebView, so Tusk sign-in (Google/Apple/Facebook full-page redirect) works on the real site.
        android.webkit.CookieManager.getInstance().setAcceptCookie(true)
        android.webkit.CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        val script = assets.open("better-tusk.js").bufferedReader().use(BufferedReader::readText)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                // Inject the same v3 Claude skin that the extension uses, on the real Tusk site only.
                if (url.contains("tusksearch.com") || url.contains("tuskcentral.ai")) {
                    view.evaluateJavascript(script, null)
                }
            }
        }
        webView.loadUrl("https://new.tusksearch.com/ai")
    }
}
