package com.gnoemes.shikimoriapp.presentation.view.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.utils.view.VideoWebChromeClient;

import java.util.HashMap;
import java.util.Map;

//TODO fix SSL/ PREVIEW_MAGE /Security exceptions
public class WebPlayerActivity extends MvpAppCompatActivity {

    private WebView webView;
    private FrameLayout layout;
    private VideoWebChromeClient client;
    private View decorView;

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, WebPlayerActivity.class);
        intent.putExtra(AppExtras.ARGUMENT_URL, url);
        return intent;
    }

    private WindowCallback windowCallback = new WindowCallback() {
        @Override
        public void onFullscreenMode() {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        @Override
        public void onNormalMode() {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    };


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                hideSystemUi();
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_player);

        layout = findViewById(R.id.frame);
        webView = new WebView(getApplicationContext());
        layout.addView(webView);

        client = new VideoWebChromeClient(webView, windowCallback);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(client);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //fix fullscreen mode on 4.4
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");

        if (getIntent() != null) {
            String url = getIntent().getStringExtra(AppExtras.ARGUMENT_URL);
            Map<String, String> map = new HashMap<>();
            map.put("Access-Control-Allow-Origin", "https://smotret-anime.ru");
            webView.loadUrl(url, map);
        } else {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideSystemUi() {
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    private void showSystemUI() {
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        webView.destroy();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Log.i("DEVE", "onDestroy: ");
        layout.removeAllViews();
        webView.setWebViewClient(null);
        client = null;
        decorView.destroyDrawingCache();
        decorView = null;
        webView.destroy();
        webView = null;
        windowCallback = null;
        super.onDestroy();
    }

    public interface WindowCallback {
        void onFullscreenMode();

        void onNormalMode();
    }
}
