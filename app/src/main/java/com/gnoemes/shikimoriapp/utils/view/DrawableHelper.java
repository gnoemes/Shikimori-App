package com.gnoemes.shikimoriapp.utils.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.ImageView;

public class DrawableHelper {

    @NonNull
    private Context context;
    private int color;
    private Drawable drawable;
    private Drawable wrappedDrawable;

    public DrawableHelper(@NonNull Context context) {
        this.context = context;
    }

    public static DrawableHelper withContext(@NonNull Context context) {
        return new DrawableHelper(context);
    }

    public DrawableHelper withDrawable(@DrawableRes int drawableRes) {
        drawable = ContextCompat.getDrawable(context, drawableRes);
        return this;
    }

    public DrawableHelper withDrawable(Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    public DrawableHelper withColor(@ColorRes int colorRes) {
        color = ContextCompat.getColor(context, colorRes);
        return this;
    }

    public DrawableHelper withAttributeColor(@AttrRes int attributeColor) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attributeColor, typedValue, true);
        color = typedValue.data;
        return this;
    }

    public DrawableHelper tint() {
        if (drawable == null) {
            throw new NullPointerException();
        }

        if (color == 0) {
            throw new IllegalArgumentException();
        }

        wrappedDrawable = drawable.mutate();
        wrappedDrawable = DrawableCompat.wrap(wrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_IN);
        return this;
    }

    public DrawableHelper stroke(int width) {
        if (drawable instanceof GradientDrawable) {
            GradientDrawable strokeDrawable = (GradientDrawable) drawable;
            strokeDrawable.setStroke(width, color);
            wrappedDrawable = strokeDrawable;
        }

        return this;
    }

    public void applyTo(@NonNull ImageView imageView) {
        if (wrappedDrawable == null) {
            throw new NullPointerException();
        }

        imageView.setImageDrawable(wrappedDrawable);
    }

    public void applyTo(@NonNull MenuItem menuItem) {
        if (wrappedDrawable == null) {
            throw new NullPointerException();
        }

        menuItem.setIcon(wrappedDrawable);
    }

    public Drawable get() {
        if (wrappedDrawable == null) {
            throw new NullPointerException();
        }

        return wrappedDrawable;
    }
}
