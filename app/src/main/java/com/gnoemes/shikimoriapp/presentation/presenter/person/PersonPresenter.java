package com.gnoemes.shikimoriapp.presentation.presenter.person;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.roles.PersonInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.HttpStatusCode;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.ServiceCodeException;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonRelatedItemType;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.person.converter.PersonDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.person.PersonView;

import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class PersonPresenter extends BaseNetworkPresenter<PersonView> {

    private PersonInteractor interactor;
    private PersonDetailsViewModelConverter converter;

    private long personId;
    private PersonDetails currentPerson;

    public PersonPresenter(PersonInteractor interactor, PersonDetailsViewModelConverter converter) {
        this.interactor = interactor;
        this.converter = converter;
    }

    @Override
    public void initData() {
        loadCharacter();
        getViewState().setTitle(R.string.person_info);
    }

    private void loadCharacter() {
        getViewState().onShowLoading();
        getViewState().hideError();
        getViewState().hideNetworkError();

        Disposable disposable = interactor.getPersonDetails(personId)
                .map(this::setCurrentCharacter)
                .doOnEvent((characterDetails, throwable) -> getViewState().onHideLoading())
                .map(converter)
                .subscribe(this::setData, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private PersonDetails setCurrentCharacter(PersonDetails personDetails) {
        this.currentPerson = personDetails;
        return personDetails;
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

    public void onRelatedItemClicked(PersonRelatedItemType type, long id) {
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
        }
    }

    public void onOpenInBrowserClicked() {
        if (currentPerson != null) {
            getRouter().navigateTo(Screens.WEB, currentPerson.getUrl());
        }
    }
//
//    public void onOpenSourceClicked() {
//        if (currentCharacter != null) {
//            String source = currentCharacter.getDescriptionSource();
//            if (TextUtils.isEmpty(source)) {
//                getRouter().showSystemMessage("Источник не найден");
//            } else {
//                getRouter().navigateTo(Screens.WEB, source);
//            }
//        }
//    }

    private void setData(List<BaseItem> baseItems) {
        getViewState().setData(baseItems);
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

}
