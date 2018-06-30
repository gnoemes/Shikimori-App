package com.gnoemes.shikimoriapp.presentation.view.settings;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.ThemeHelper;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(ThemeHelper.applyTheme(SettingsActivity.this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.common_settings);
        Drawable icon = DrawableHelper
                .withContext(SettingsActivity.this)
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(icon);
        toolbar.setNavigationOnClickListener(v -> SettingsActivity.super.onBackPressed());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .commit();
    }
}
