package mn.hipay.tokenlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

public class WebViewActivity extends AppCompatActivity {
    public WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String initId = extras.getString("initId");
            //The key argument here must match that used in the other activity
            webView = (WebView) findViewById(R.id.hps_webview);

//            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//            this.deleteDatabase("webviewCache.db");
//            this.deleteDatabase("webview.db");
//            webView.clearCache(true);
//            webView.clearHistory();
//            webView.clearFormData();
//            webView.destroy();
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                boolean timeout;
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:
                    view.loadUrl(url);
                    return false; // then it is not handled by default action
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    ToasterMessage.show(WebViewActivity.this, description);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
                    handler.proceed(); // Ignore SSL certificate errors
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.i("TOKEN PAGEFINISH", url);
                    String url_new = view.getUrl();
                    Log.i("","TOKEN  Function URL: "+url_new);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    super.onPageStarted(view, url, favicon);
                    String url_new = view.getUrl();
                    Log.i("","Webview Function URL: "+url_new);
                    Log.i("","Webview Function URLOLD: " +url_new);
                }
            });
            webView.loadUrl("https://test.hipay.mn/v2/card/form/" + initId);
            webView.addJavascriptInterface(new WebAppInterface(this), "HPSSDK");
        } else {
            ToasterMessage.show(this, "SOME ERROR.");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            this.deleteDatabase("webviewCache.db");
            this.deleteDatabase("webview.db");
            webView.clearCache(true);
            webView.clearHistory();
            webView.clearFormData();
            webView.destroy();
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

    }
}

class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void processCard(String result, String msg) {
        Log.i("TOKEN CARDADD RESULT", msg);
        ToasterMessage.show(mContext, msg);
        ((Activity) mContext).finish();
    }
    /** Show a toast from the web page */
    @JavascriptInterface
    public void postMessage(String msg) {
        Log.i("TOKEN CARDADD RESULT", msg);
        ToasterMessage.show(mContext, msg);
        ((Activity) mContext).finish();
    }
    @JavascriptInterface
    public void processCardBack() {
        ToasterMessage.show(mContext, "Card back");
        ((Activity) mContext).finish();
    }
}