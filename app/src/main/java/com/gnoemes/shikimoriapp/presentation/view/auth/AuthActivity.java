package com.gnoemes.shikimoriapp.presentation.view.auth;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;
import com.gnoemes.shikimoriapp.entity.app.domain.AuthType;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.presentation.presenter.auth.AuthPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class AuthActivity extends BaseActivity<AuthPresenter, AuthView> implements AuthView {

    private static final String PATTERN = "https?://(?:www\\.)?shikimori\\.org/oauth/authorize/(?:.*)";
    private static final String SIGN_UP_URL = "https://shikimori.org/users/sign_up";
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectPresenter
    AuthPresenter presenter;
    @Inject
    NavigatorHolder navigatorHolder;

    public static Intent newIntent(Context context, AuthType data) {
        Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra(AppExtras.ARGUMENT_AUTH_TYPE, data);
        return intent;
    }

    @ProvidePresenter
    AuthPresenter provideAuthPresenter() {
        presenter = presenterProvider.get();
        if (getIntent() != null) {
            presenter.setAuthType((AuthType) getIntent().getSerializableExtra(AppExtras.ARGUMENT_AUTH_TYPE));
        }
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWebView();
    }

    @Override
    protected void onDestroy() {
        webView.setWebViewClient(null);
        webView.stopLoading();
        webView.destroy();
        webView = null;
        super.onDestroy();
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_auth;
    }

    @Override
    protected Navigator getLocalNavigator() {
        return null;
    }

    @Override
    protected NavigatorHolder getNavigatorHolder() {
        return navigatorHolder;
    }

    @Override
    protected AuthPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSignIn() {
        webView.loadUrl(AppConfig.AUTH_URL);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Matcher matcher = Pattern.compile(PATTERN).matcher(url);
                if (matcher.find()) {
                    String authCode = matcher.group(0) == null ? "" : url.substring(url
                            .lastIndexOf('/'))
                            .replaceFirst("/", "");
                    getPresenter().onAuthCodeReceived(authCode);
                    webView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
                Log.i("DEVE", "shouldOverrideUrlLoading: " + url);

                return false;
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                Matcher matcher = Pattern.compile(PATTERN).matcher(url);
                if (matcher.find()) {
                    String authCode = matcher.group(0) == null ? "" :
                            url.substring(url
                                    .lastIndexOf('/'))
                                    .replaceFirst("/", "");
                    getPresenter().onAuthCodeReceived(authCode);
                    webView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
                Log.i("DEVE", "shouldOverrideUrlLoading: " + request.getUrl());
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);

                Log.i("DEVE", "onPageStarted: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onSignUp() {
        webView.loadUrl(SIGN_UP_URL);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Matcher matcher = Pattern.compile(PATTERN).matcher(url);
                if (matcher.find()) {
                    String authCode = matcher.group(0) == null ? "" : url.substring(url
                            .lastIndexOf('/'))
                            .replaceFirst("/", "");
                    getPresenter().onAuthCodeReceived(authCode);
                    webView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
                Log.i("DEVE", "shouldOverrideUrlLoading: " + url);

                return false;
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                Matcher matcher = Pattern.compile(PATTERN).matcher(url);
                if (matcher.find()) {
                    String authCode = matcher.group(0) == null ? "" :
                            url.substring(url
                                    .lastIndexOf('/'))
                                    .replaceFirst("/", "");
                    getPresenter().onAuthCodeReceived(authCode);
                    webView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
                Log.i("DEVE", "shouldOverrideUrlLoading: " + request.getUrl());
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                if (url.equals(BuildConfig.ShikimoriBaseUrl)) {
                    view.loadUrl(AppConfig.AUTH_URL);
                }
                Log.i("DEVE", "onPageStarted: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initWebView() {
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setVisibility(View.VISIBLE);
    }
}
