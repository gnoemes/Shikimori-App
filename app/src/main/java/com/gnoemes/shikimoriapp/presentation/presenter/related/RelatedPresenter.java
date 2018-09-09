package com.gnoemes.shikimoriapp.presentation.presenter.related;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.related.RelatedInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.related.RelatedView;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class RelatedPresenter extends BaseNetworkPresenter<RelatedView> {

    private RelatedInteractor interactor;
    private RelatedViewModelConverter converter;

    private long id;
    private Type type;

    public RelatedPresenter(RelatedInteractor interactor,
                            RelatedViewModelConverter converter) {
        this.interactor = interactor;
        this.converter = converter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().setTitle(R.string.related);

        switch (type) {
            case ANIME:
                loadAnimeRelated();
                break;
            case MANGA:
                loadMangaRelated();
                break;
        }
    }

    private void loadMangaRelated() {
        //TODO add
    }

    private void loadAnimeRelated() {
        getViewState().onShowLoading();

        Disposable disposable = interactor.getRelatedAnime(id)
                .doOnEvent((relateds, throwable) -> getViewState().onHideLoading())
                .map(converter)
                .subscribe(this::showData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void showData(List<BaseItem> items) {
        getViewState().showList(items);
    }


    public void onRefresh() {
        switch (type) {
            case ANIME:
                loadAnimeRelated();
                break;
            case MANGA:
                loadMangaRelated();
                break;
        }
    }

    public void onItemClicked(Type type, long id) {
        switch (type) {
            case MANGA:
                onMangaClicked(id);
                break;
            case ANIME:
                onAnimeClicked(id);
                break;
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
