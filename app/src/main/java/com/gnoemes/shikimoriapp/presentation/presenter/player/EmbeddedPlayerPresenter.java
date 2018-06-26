package com.gnoemes.shikimoriapp.presentation.presenter.player;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.domain.anime.series.SeriesInteractor;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationWithSources;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.player.embedded.EmbeddedPlayerView;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class EmbeddedPlayerPresenter extends BaseNetworkPresenter<EmbeddedPlayerView> {

    private SeriesInteractor seriesInteractor;

    private long translationId;
    private TranslationWithSources current;

    public EmbeddedPlayerPresenter(SeriesInteractor seriesInteractor) {
        this.seriesInteractor = seriesInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadTranslation();
    }

    private void loadTranslation() {

        Disposable disposable = seriesInteractor.getTranslationWithSources(translationId)
                .subscribe(this::setTranslationWithSources, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void setTranslationWithSources(TranslationWithSources translation) {
        this.current = translation;
        getViewState().setPlayerData(translation, 0);
    }

    public void setTranslationId(long translationId) {
        this.translationId = translationId;
    }

    public void onResolutionChanged(int newResolution) {
        int position = 0;
        for (PlayEpisode episode : current.getSources()) {
            if (episode.getResolution() == newResolution) {
                getViewState().setPlayerData(current, position);
            }
            position++;
        }
    }
}
