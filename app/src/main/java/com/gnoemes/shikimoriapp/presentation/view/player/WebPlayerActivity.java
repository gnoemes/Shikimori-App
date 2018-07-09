package com.gnoemes.shikimoriapp.presentation.view.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.series.presentation.PlayVideoNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.player.WebPlayerPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;
import com.gnoemes.shikimoriapp.utils.view.VideoWebChromeClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class WebPlayerActivity extends BaseActivity<WebPlayerPresenter, WebPlayerView>
        implements WebPlayerView {

    private WebView webView;
    private FrameLayout layout;
    private VideoWebChromeClient client;
    private View decorView;

    @BindView(R.id.constraint)
    ConstraintLayout container;

    @BindView(R.id.progress_loading)
    ProgressBar progressBar;

    @Inject
    NavigatorHolder navigatorHolder;

    @InjectPresenter
    WebPlayerPresenter presenter;

    public static Intent newIntent(Context context, PlayVideoNavigationData data) {
        Intent intent = new Intent(context, WebPlayerActivity.class);
        intent.putExtra(AppExtras.ARGUMENT_PLAY_VIDEO_DATA, data);
        return intent;
    }

    @ProvidePresenter
    WebPlayerPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getIntent() != null) {
            presenter.setPlayData((PlayVideoNavigationData) getIntent().getSerializableExtra(AppExtras.ARGUMENT_PLAY_VIDEO_DATA));
        }

        return presenter;
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
        ButterKnife.bind(this);
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

    @Override
    public void showError() {
        Toast.makeText(WebPlayerActivity.this, "Произошла ошибка во время загрузки видео", Toast.LENGTH_LONG).show();
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_web_player;
    }

    @Override
    protected Navigator getNavigator() {
        return null;
    }

    @Override
    protected NavigatorHolder getNavigatorHolder() {
        return navigatorHolder;
    }

    @Override
    protected WebPlayerPresenter getPresenter() {
        return presenter;
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
        super.finish();
    }

    @Override
    protected void onDestroy() {
        Log.i("DEVE", "onDestroy: ");
        if (layout != null) {
            layout.removeAllViews();
        }

        if (decorView != null) {
            decorView.destroyDrawingCache();
        }

        if (webView != null) {
            webView.setWebViewClient(null);
            client = null;
            webView.destroy();
        }

        decorView = null;
        webView = null;
        windowCallback = null;
        super.onDestroy();
    }

    @Override
    public void playVideo(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    public interface WindowCallback {
        void onFullscreenMode();

        void onNormalMode();
    }
}
