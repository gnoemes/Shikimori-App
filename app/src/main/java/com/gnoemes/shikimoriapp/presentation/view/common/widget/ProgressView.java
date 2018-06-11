package com.gnoemes.shikimoriapp.presentation.view.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;
import com.gnoemes.shikimoriapp.R;

import butterknife.BindView;
import butterknife.OnTouch;

public class ProgressView extends BaseView {

    @BindView(R.id.progress_container)
    ConstraintLayout layout;
    @BindView(R.id.progress)
    SpinKitView progressView;

    public ProgressView(@NonNull Context context) {
        super(context);
    }

    public ProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_progress_fullscreen;
    }

    @Override
    public void setVisibility(int visibility) {
        layout.setVisibility(visibility);
        progressView.setVisibility(visibility);
    }

    @OnTouch(R.id.progress_container)
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}
