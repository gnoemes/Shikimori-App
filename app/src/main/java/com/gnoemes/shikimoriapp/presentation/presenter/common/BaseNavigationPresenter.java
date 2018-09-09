package com.gnoemes.shikimoriapp.presentation.presenter.common;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseView;

import ru.terrakok.cicerone.Router;

public abstract class BaseNavigationPresenter<View extends BaseView> extends BasePresenter<View> {

    private Router localRouter;

    @Override
    public Router getRouter() {
        return localRouter;
    }

    public void setLocalRouter(Router localRouter) {
        this.localRouter = localRouter;
    }

    @Override
    public void onBackPressed() {
        getRouter().exit();
    }

    public void onAnimeClicked(long id) {
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    public void onMangaClicked(long id) {
        getRouter().showSystemMessage("Манга в разработке");
    }

    public void onCharacterClicked(long id) {
        getRouter().navigateTo(Screens.CHARACTER_DETAILS, id);
    }

    public void onUserClicked(long id) {
        getRouter().navigateTo(Screens.PROFILE, id);
    }

    public void onPersonClicked(long id) {
        getRouter().navigateTo(Screens.PERSON_DETAILS, id);
    }

    public void onContentClicked(Type type, long id) {
        switch (type) {
            case ANIME:
                onAnimeClicked(id);
                break;
            case MANGA:
                onMangaClicked(id);
                break;
            case CHARACTER:
                onCharacterClicked(id);
                break;
            case USER:
                onUserClicked(id);
                break;
            case PERSON:
                onPersonClicked(id);
                break;

        }
    }

}
