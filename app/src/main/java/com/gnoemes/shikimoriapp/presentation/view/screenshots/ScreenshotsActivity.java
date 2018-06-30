package com.gnoemes.shikimoriapp.presentation.view.screenshots;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.ScreenshotNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.screenshots.ScreenshotsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseSwipeBackActivity;
import com.gnoemes.shikimoriapp.utils.view.SwipeBackLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class ScreenshotsActivity extends BaseSwipeBackActivity<ScreenshotsPresenter, ScreenshotsView>
        implements ScreenshotsView {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @InjectPresenter
    ScreenshotsPresenter presenter;
    @Inject
    NavigatorHolder navigatorHolder;
    private ScreenshotPagerAdapter adapter;

    public static Intent newIntent(Context context, ScreenshotNavigationData data) {
        Intent intent = new Intent(context, ScreenshotsActivity.class);
        intent.putExtra(AppExtras.ARGUMENT_ANIME_ID, data.getAnimeId());
        intent.putExtra(AppExtras.ARGUMENT_URL, data.getPosterUrl());
        intent.putExtra(AppExtras.ARGUMENT_NAME, data.getName());
        return intent;
    }

    @ProvidePresenter
    ScreenshotsPresenter provideScreenshotsPresenter() {
        presenter = presenterProvider.get();

        if (getIntent() != null) {
            long animeId = getIntent().getLongExtra(AppExtras.ARGUMENT_ANIME_ID, Constants.NO_ID);
            String name = getIntent().getStringExtra(AppExtras.ARGUMENT_NAME);
            String posterUrl = getIntent().getStringExtra(AppExtras.ARGUMENT_URL);
            presenter.setAnimeId(animeId);
            presenter.setName(name);
            presenter.setPosterUrl(posterUrl);
        }

        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ScreenshotTheme);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutActivity());
        setDragDirectMode(SwipeBackLayout.DragDirectMode.EDGE);
        ButterKnife.bind(this);
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setSubtitle(String subtitle) {
        toolbar.setSubtitle(subtitle);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            getPresenter().updateScreenCounter(position + 1);
        }
    };

    @Override
    public void initToolbar() {

    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected Navigator getNavigator() {
        return null;
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_screenshots;
    }

    @Override
    protected NavigatorHolder getNavigatorHolder() {
        return navigatorHolder;
    }

    @Override
    protected ScreenshotsPresenter getPresenter() {
        return presenter;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onDestroy() {
        viewPager.removeOnPageChangeListener(pageChangeListener);
        super.onDestroy();
    }

    @Override
    public void setScreenshots(List<String> urls) {
        List<Uri> uries = new ArrayList<>();
        for (String s : urls) {
            uries.add(Uri.parse(s));
        }

        adapter = new ScreenshotPagerAdapter(uries);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    class ScreenshotPagerAdapter extends PagerAdapter {

        private final List<Uri> uries;

        ScreenshotPagerAdapter(List<Uri> uries) {
            this.uries = uries;
            BigImageViewer.prefetch(uries.toArray(new Uri[uries.size()]));
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_screenshot_item, container, false);
            createScreenshot(layout, uries.get(position));
            container.addView(layout);
            return layout;
        }

        private void createScreenshot(ViewGroup layout, Uri uri) {
            CoordinatorLayout container = layout.findViewById(R.id.container);
            BigImageView screenshot = layout.findViewById(R.id.view_screenshot);
            ProgressBar progressBar = layout.findViewById(R.id.progress_loading);
            screenshot.setOnClickListener(v -> toggleAppBarVisibility());
            screenshot.showImage(uri);
            screenshot.setImageLoaderCallback(new ImageLoader.Callback() {
                @Override
                public void onCacheHit(File image) {

                }

                @Override
                public void onCacheMiss(File image) {

                }

                @Override
                public void onStart() {
                    TransitionManager.beginDelayedTransition(container, new Fade());
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onProgress(int progress) {

                }

                @Override
                public void onFinish() {
                    TransitionManager.beginDelayedTransition(container, new Fade());
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(File image) {

                }

                @Override
                public void onFail(Exception error) {
                    TransitionManager.beginDelayedTransition(container, new Fade());
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), R.string.loading_screenshot_error, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getCount() {
            return uries.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public final void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
