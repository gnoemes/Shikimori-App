package com.gnoemes.shikimoriapp.presentation.view.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;

import butterknife.BindView;

public class RateProgressView extends BaseView {

    @BindView(R.id.layout)
    LinearLayout layout;

    public RateProgressView(@NonNull Context context) {
        super(context);
    }

    public RateProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RateProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_rates_progress;
    }

    public void addRate(@NonNull RateBarStatus status, int value) {
        switch (status) {
            case WATCHED:
                createWatchedBar(value);
                break;
            case IN_PROGRESS:
                createInProgressBar(value);
                break;
            case DROPPED:
                createDroppedBar(value);
                break;
        }
    }

    private void createDroppedBar(int value) {
        TextView dropped = createBar(value);
        dropped.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        layout.addView(dropped);
        layout.invalidate();
    }

    private void createInProgressBar(int value) {
        TextView inProgress = createBar(value);
        inProgress.setBackgroundColor(getResources().getColor(R.color.cyan));
        layout.addView(inProgress);
        layout.invalidate();
    }

    private void createWatchedBar(int value) {
        TextView watched = createBar(value);
        watched.setBackgroundColor(getResources().getColor(R.color.light_cyan));
        layout.addView(watched);
        layout.invalidate();
    }

    private TextView createBar(int value) {
        TextView view = new TextView(getContext());
        view.setText(String.valueOf(value));
        view.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, (float) value);
        view.setGravity(Gravity.CENTER);
        view.setLayoutParams(params);
        return view;
    }

    @Override
    protected void onDetachedFromWindow() {
        layout.removeAllViews();
        super.onDetachedFromWindow();
    }

    public enum RateBarStatus {
        WATCHED,
        IN_PROGRESS,
        DROPPED
    }
}
