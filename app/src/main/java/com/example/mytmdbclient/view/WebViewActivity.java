package com.example.mytmdbclient.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ActivityWebViewBinding;
import com.example.mytmdbclient.util.Constants;

public class WebViewActivity extends AppCompatActivity
{

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ActivityWebViewBinding activityWebViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

        //final ProgressBar loadingIndicator = findViewById(R.id.indicator);
        final ProgressBar loadingIndicator = activityWebViewBinding.indicator;

        loadingIndicator.setVisibility(View.VISIBLE);
        //WebView web = findViewById(R.id.webView);
        WebView web = activityWebViewBinding.webView;

        Intent intent = getIntent();
        url = intent.getStringExtra(Constants.URL_OF_REVIEW);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url);

        web.setWebViewClient(new WebViewClient()
        {
            public void onPageFinished(WebView view, String url)
            {
                // Hide Loading Indicator
                loadingIndicator.setVisibility(View.GONE);
            }
        });


    }
}
