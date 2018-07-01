package com.gnoemes.shikimoriapp.presentation.view.common.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.SwipeBackLayout;

public abstract class BaseSwipeBackActivity<Presenter extends BasePresenter, V extends BaseView> extends BaseActivity<Presenter, V> implements SwipeBackLayout.SwipeBackListener {

    private static final SwipeBackLayout.DragEdge DEFAULT_DRAG_EDGE = SwipeBackLayout.DragEdge.BOTTOM;

    protected AppBarLayout actionBar;
    protected RelativeLayout container;
    protected SwipeBackLayout swipeBackLayout;
    protected Toolbar toolbar;
    protected ProgressBar progressBar;

    private boolean isVisible = true;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isVisible = savedInstanceState.getBoolean(AppExtras.ARGUMENT_SHOW_FLAG);
        }

        actionBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activtiy_base_swipe_back);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        swipeBackLayout = findViewById(R.id.layout_swipe_back);
        container = findViewById(R.id.relative);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_loading);
        actionBar = findViewById(R.id.app_bar);

        swipeBackLayout.setDragEdge(DEFAULT_DRAG_EDGE);
        swipeBackLayout.setOnSwipeBackListener(this);

        Drawable navigationIcon = DrawableHelper.withContext(this)
                .withDrawable(R.drawable.ic_arrow_back)
                .withColor(R.color.white)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        swipeBackLayout.addView(view);
        swipeBackLayout.setScrollChild(view);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(AppExtras.ARGUMENT_SHOW_FLAG, isVisible);
    }

    public void setEnableSwipe(boolean enableSwipe) {
        swipeBackLayout.setEnablePullToBack(enableSwipe);
    }

    public void setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        swipeBackLayout.setDragEdge(dragEdge);
    }

    public void setDragDirectMode(SwipeBackLayout.DragDirectMode dragDirectMode) {
        swipeBackLayout.setDragDirectMode(dragDirectMode);
    }

    protected void toggleAppBarVisibility() {
        if (isVisible) {
            hideAppBar();
        } else {
            showAppBar();
        }
    }

    private void hideAppBar() {
        if (actionBar != null) {
            TransitionManager.beginDelayedTransition(container, new Fade());
            actionBar.setVisibility(View.GONE);
        }
        isVisible = false;
    }

    private void showAppBar() {
        if (actionBar != null) {
            TransitionManager.beginDelayedTransition(container, new Fade());
            actionBar.setVisibility(View.VISIBLE);
        }
        isVisible = true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        container.setAlpha(1 - fractionScreen * 1.5f);
    }

    @Override
    public void onShowLoading() {
        TransitionManager.beginDelayedTransition(container, new Fade());
        container.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        TransitionManager.beginDelayedTransition(container, new Fade());
        container.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
