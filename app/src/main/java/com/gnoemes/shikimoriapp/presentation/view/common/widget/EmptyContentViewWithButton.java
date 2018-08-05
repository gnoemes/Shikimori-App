package com.gnoemes.shikimoriapp.presentation.view.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;

import butterknife.BindView;

public class EmptyContentViewWithButton extends BaseView {

    @BindView(R.id.image_title)
    ImageView imageView;
    @BindView(R.id.text_description)
    TextView textView;
    @BindView(R.id.btn_find_all)
    Button button;

    private OnClickListener callback;

    public EmptyContentViewWithButton(@NonNull Context context) {
        super(context);
    }

    public EmptyContentViewWithButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyContentViewWithButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        button.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(v);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.view_empty_with_button;
    }

    public void setVisibility(int visibility) {
        imageView.setVisibility(visibility);
        textView.setVisibility(visibility);
        button.setVisibility(visibility);
    }

    public void setCallback(OnClickListener callback) {
        this.callback = callback;
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setText(@StringRes int textRes) {
        textView.setText(textRes);
    }

    public void setButtonText(@StringRes int textRes) {
        button.setText(textRes);
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        callback = null;
    }
}
