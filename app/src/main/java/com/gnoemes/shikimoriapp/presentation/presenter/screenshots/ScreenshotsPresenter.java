package com.gnoemes.shikimoriapp.presentation.presenter.screenshots;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.AnimeInteractor;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.Screenshot;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.screenshots.ScreenshotsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class ScreenshotsPresenter extends BaseNetworkPresenter<ScreenshotsView> {

    private AnimeInteractor interactor;

    private String posterUrl;
    private long animeId;
    private String name;

    private long size;

    public ScreenshotsPresenter(AnimeInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void initData() {
        super.initData();
        loadScreenshots();
        getViewState().setTitle(name);
    }

    private void loadScreenshots() {
        getViewState().onShowLoading();

        Disposable disposable = interactor.getScreenshots(animeId)
                .doOnEvent((screenshots, throwable) -> getViewState().onHideLoading())
                .subscribe(this::setScreenshots, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void setScreenshots(List<Screenshot> screenshots) {
        List<String> urls = new ArrayList<>();
        if (!TextUtils.isEmpty(posterUrl)) {
            urls.add(posterUrl);
        }
        for (Screenshot screenshot : screenshots) {
            urls.add(screenshot.getOriginal());
        }

        size = urls.size();
        updateScreenCounter(1);
        getViewState().setScreenshots(urls);
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    //TODO resource provider
    public void updateScreenCounter(int position) {
        String subtitle = String.format(Locale.getDefault(), "%d из %d", position, size);
        getViewState().setSubtitle(subtitle);
    }
}
