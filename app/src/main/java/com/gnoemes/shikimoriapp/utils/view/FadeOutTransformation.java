package com.gnoemes.shikimoriapp.utils.view;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class FadeOutTransformation implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {

        page.setTranslationX(-position * page.getWidth());
        page.setAlpha(1 - Math.abs(position));
    }
}