package com.example.yy.widgetdemos.IntentDemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.yy.widgetdemos.R;

public class WebPageActivity extends AppCompatActivity {

    private static final String TAG = "web";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = findViewById(R.id.web_page_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        //自己访问
        //mWebView.loadUrl("http://www.baidu.com");

        //从BasicActivity获取网页并访问
        Intent intent = getIntent();
        Uri uri = intent.getData();
        String page = uri.getScheme() + "://" + uri.getAuthority();
        Log.e(TAG,"page: " + page);
        mWebView.loadUrl(page);
    }
}
