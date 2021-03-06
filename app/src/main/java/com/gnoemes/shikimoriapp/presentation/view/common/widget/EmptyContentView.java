package com.gnoemes.shikimoriapp.presentation.view.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;

import butterknife.BindView;

public class EmptyContentView extends BaseView {
    @BindView(R.id.image_title)
    ImageView imageView;
    @BindView(R.id.text_description)
    TextView textView;

    public EmptyContentView(@NonNull Context context) {
        super(context);
    }

    public EmptyContentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_empty;
    }

    public void setVisibility(int visibility) {
        imageView.setVisibility(visibility);
        textView.setVisibility(visibility);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setText(@StringRes int textRes) {
        textView.setText(textRes);
    }
}
