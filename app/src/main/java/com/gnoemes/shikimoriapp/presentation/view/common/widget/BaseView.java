package com.gnoemes.shikimoriapp.presentation.view.common.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseView extends FrameLayout {

    private Unbinder unbinder;

    public BaseView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        unbinder = ButterKnife.bind(inflate(context, getLayout(), this));
    }

    @LayoutRes
    protected abstract int getLayout();

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
