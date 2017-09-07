package zhanghegang.com.bawei.zhanghegang1507c20170830.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import zhanghegang.com.bawei.zhanghegang1507c20170830.R;

public class WebActivity extends AppCompatActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initSet();
        initload();
    }

    private void initload() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        wv.loadUrl(url);
    }

    private void initSet() {
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private void initView() {
        wv = (WebView) findViewById(R.id.wv_detail);
    }
}
