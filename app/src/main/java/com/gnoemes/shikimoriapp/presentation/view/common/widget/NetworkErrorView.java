package com.gnoemes.shikimoriapp.presentation.view.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkErrorView extends FrameLayout {

    @BindView(R.id.image_title)
    ImageView imageView;
    @BindView(R.id.text_description)
    TextView textView;

    public NetworkErrorView(Context context) {
        super(context);
        init(context);
    }

    public NetworkErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NetworkErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ButterKnife.bind(inflate(context, R.layout.view_network_error, this));
    }

    public void setVisibility(int visibility) {
        imageView.setVisibility(visibility);
        textView.setVisibility(visibility);
    }
}
