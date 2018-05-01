package com.gnoemes.shikimoriapp.presentation.view.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyContentViewWithButton extends FrameLayout {

    @BindView(R.id.image_title)
    ImageView imageView;
    @BindView(R.id.text_description)
    TextView textView;
    @BindView(R.id.btn_find_all)
    Button button;

    private OnClickListener callback;

    public EmptyContentViewWithButton(Context context) {
        super(context);
        init(context);
    }

    public EmptyContentViewWithButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyContentViewWithButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ButterKnife.bind(inflate(context, R.layout.view_empty_with_button, this));

        button.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(v);
            }
        });
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


}
