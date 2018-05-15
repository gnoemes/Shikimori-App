package com.gnoemes.shikimoriapp.utils.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.AttributeSet;

public class SwitchFixCompat extends SwitchPreferenceCompat {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwitchFixCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SwitchFixCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwitchFixCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchFixCompat(Context context) {
        super(context);
    }
}
