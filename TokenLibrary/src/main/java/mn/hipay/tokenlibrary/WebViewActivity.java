package mn.hipay.tokenlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String initId = extras.getString("initId");
            //The key argument here must match that used in the other activity
            WebView webView = (WebView) findViewById(R.id.hps_webview);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl("https://test.hipay.mn/v2/card/form/" + initId);
            webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        } else {
            ToasterMessage.show(this, "SOME ERROR.");
            finish();
        }
    }
}

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void processCard(String value) {
        ToasterMessage.show(mContext, value)
    }
}