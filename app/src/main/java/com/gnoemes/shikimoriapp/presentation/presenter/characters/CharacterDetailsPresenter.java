package com.gnoemes.shikimoriapp.presentation.presenter.characters;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.roles.CharacterInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.HttpStatusCode;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.ServiceCodeException;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterRelatedType;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.characters.CharacterDetailsView;
import com.gnoemes.shikimoriapp.presentation.view.characters.converter.CharacterViewModelConverter;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class CharacterDetailsPresenter extends BaseNetworkPresenter<CharacterDetailsView> {

    private CharacterInteractor interactor;
    private CharacterViewModelConverter viewModelConverter;

    private long characterId;
    private CharacterDetails currentCharacter;

    public CharacterDetailsPresenter(CharacterInteractor interactor,
                                     CharacterViewModelConverter viewModelConverter) {
        this.interactor = interactor;
        this.viewModelConverter = viewModelConverter;
    }

    @Override
    public void initData() {
        loadCharacter();
        getViewState().setTitle(R.string.character_info);
    }

    private void loadCharacter() {
        getViewState().onShowLoading();
        getViewState().hideError();
        getViewState().hideNetworkError();

        Disposable disposable = interactor.getCharacterDetails(characterId)
                .map(this::setCurrentCharacter)
                .doOnEvent((characterDetails, throwable) -> getViewState().onHideLoading())
                .map(viewModelConverter)
                .subscribe(this::setData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private CharacterDetails setCurrentCharacter(CharacterDetails characterDetails) {
        this.currentCharacter = characterDetails;
        return characterDetails;
    }

    @Override
    protected void processErrors(Throwable throwable) {
        BaseException baseException = (BaseException) throwable;
        switch (baseException.getTag()) {
            case NetworkException.TAG:
                processNetworkError(throwable);
                break;
            case ServiceCodeException.TAG:
                processServiceError((ServiceCodeException) throwable);
            default:
                super.processErrors(throwable);
        }
    }

    private void processServiceError(ServiceCodeException throwable) {
        switch (throwable.getServiceCode()) {
            case HttpStatusCode.UNPROCESSABLE_ENTITY:
                getViewState().showError();
                break;
            default:
                super.processErrors(throwable);
        }
    }

    private void processNetworkError(Throwable throwable) {
        getViewState().showNetworkError();
    }

    public void onRelatedItemClicked(CharacterRelatedType type, long id) {
        switch (type) {
            case ANIME:
                onAnimeClicked(id);
                break;
            case MANGA:
                onMangaClicked(id);
                break;
            case SEYU:
                onSeyuClicked(id);
                break;
        }
    }

    public void onOpenInBrowserClicked() {
        if (currentCharacter != null) {
            getRouter().navigateTo(Screens.WEB, currentCharacter.getUrl());
        }
    }

    public void onOpenSourceClicked() {
        if (currentCharacter != null) {
            String source = currentCharacter.getDescriptionSource();
            if (TextUtils.isEmpty(source)) {
                getRouter().showSystemMessage("Источник не найден");
            } else {
                getRouter().navigateTo(Screens.WEB, source);
            }
        }
    }

    private void onSeyuClicked(long id) {
        getRouter().navigateTo(Screens.PERSON_DETAILS, id);
    }

    private void onMangaClicked(long id) {
        getRouter().showSystemMessage("Манга в разработке");
    }

    private void onAnimeClicked(long id) {
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    private void setData(List<BaseItem> baseItems) {
        getViewState().setData(baseItems);
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }
}
