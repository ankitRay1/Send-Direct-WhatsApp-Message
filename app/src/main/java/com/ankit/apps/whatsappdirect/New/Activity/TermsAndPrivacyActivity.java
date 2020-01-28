package com.ankit.apps.whatsappdirect.New.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ankit.apps.whatsappdirect.New.R;

public class TermsAndPrivacyActivity extends AppCompatActivity {

    private WebView webView;
    private Toolbar toolbar;
    private ImageView iv_back;
    private TextView tv_title;

    String url = "https://ylight.xyz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_privacy);

        getSupportActionBar().hide();

        webView = findViewById(R.id.web_view);
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_tooltitle);
        tv_title.setText(R.string.menu_name4);

        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
