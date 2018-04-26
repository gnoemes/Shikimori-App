package com.gnoemes.shikimoriapp.utils.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public class AttributesHelper {

    private Context context;
    private int color;

    public AttributesHelper(@NonNull Context context) {
        this.context = context;
    }

    public static AttributesHelper withContext(@NonNull Context context) {
        return new AttributesHelper(context);
    }

    public int getColor(@AttrRes int colorRes) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(colorRes, typedValue, true);
        color = typedValue.data;
        return color;
    }
}
