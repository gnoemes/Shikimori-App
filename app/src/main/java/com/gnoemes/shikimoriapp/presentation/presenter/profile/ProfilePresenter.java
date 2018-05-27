package com.gnoemes.shikimoriapp.presentation.presenter.profile;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.entity.user.domain.UserProfile;
import com.gnoemes.shikimoriapp.entity.user.domain.UserStats;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileAction;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileHeadItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileOtherItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileRatesInfoItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileShadowDivider;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileSocialItem;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.profile.converter.ProfileViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.profile.ProfileView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class ProfilePresenter extends BaseNetworkPresenter<ProfileView> {

    private UserInteractor interactor;
    private ProfileViewModelConverter converter;

    private long userId;
    private UserProfile user;

    public ProfilePresenter(UserInteractor interactor,
                            ProfileViewModelConverter converter) {
        this.interactor = interactor;
        this.converter = converter;
    }

    @Override
    public void initData() {
        getViewState().setTitle(R.string.common_profile);
        initList();
        loadUserProfile();
        loadSocial();
        loadFavorites();
    }


    private void initList() {
        List<BaseProfileItem> items = new ArrayList<>();
        items.add(new ProfileShadowDivider());
        items.add(new ProfileHeadItem());
        items.add(new ProfileShadowDivider());
        items.add(new ProfileRatesInfoItem());
        items.add(new ProfileShadowDivider());
        items.add(new ProfileSocialItem());
        items.add(new ProfileShadowDivider());
        items.add(new ProfileOtherItem());
        items.add(new ProfileShadowDivider());
        getViewState().setProfile(items);
    }

    private void loadSocial() {
        Disposable disposable = interactor.getUserClubs(userId)
                .flatMap(clubs -> interactor.getUserFriends(userId)
                        .map(userBriefs -> converter.convertSocialItem(userBriefs, clubs)))
                .subscribe(this::updateSocial, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void updateSocial(BaseProfileItem baseProfileItem) {
        getViewState().updateSocial(baseProfileItem);
    }

    private void loadFavorites() {
        Disposable disposable = interactor.getFavorites(userId)
                .map(converter::convertOtherItem)
                .subscribe(this::updateOther, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void updateOther(BaseProfileItem item) {
        getViewState().updateOther(item);
    }

    private void loadUserProfile() {
        Disposable disposable = interactor.getUserProfile(userId)
                .doOnSuccess(profile -> loadRateStatuses(profile.getUserStats()))
                .map(this::setCurrentUser)
                .map(converter::convertHeadItem)
                .subscribe(this::updateHead, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void updateHead(BaseProfileItem baseProfileItem) {
        ProfileHeadItem item = (ProfileHeadItem) baseProfileItem;
        getViewState().setTitle(item.getNickname());
        getViewState().updateHead(item);
    }

    private void loadRateStatuses(UserStats userStats) {
        getViewState().updateRates(converter.convertRatesInfoItem(userStats));
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    private UserProfile setCurrentUser(UserProfile profile) {
        this.user = profile;
        return profile;
    }

    public void onAction(ProfileAction action, long id) {
        switch (action) {
            case BANS:
                onBansClicked(id);
                break;
            case ADD_TO_FRIENDS:
                onAddFriend(id);
                break;
            case CLUB:
                onClubClicked(id);
                break;
            case RATES:
                onRatesClicked();
                break;
            case FRIEND:
                onFriendClicked(id);
                break;
            case IGNORE:
                onIgnoreClicked(id);
                break;
            case HISTORY:
                onHistoryClicked(id);
                break;
            case MAILBOX:
                onMailBoxClicked(id);
                break;
            case UN_IGNORE:
                onUnIgnoreClicked(id);
                break;
            case SEND_MESSAGE:
                onSendMessageClicked(id);
                break;
            case REMOVE_FRIEND:
                onRemoveFriendClicked(id);
                break;
            case FAVORITE_ANIME:
                onAnimeClicked(id);
                break;
            case FAVORITE_MANGAKAS:
                onMangakasClicked(id);
                break;
            case FAVORITE_SEYU:
                onSeyuClicked(id);
                break;
            case FAVORITE_MANGA:
                onMangaClicked(id);
                break;
            case FAVORITE_PEOPLE:
                onPeopleClicked(id);
                break;
            case FAVORITE_PRODUCERS:
                onProducersClicked(id);
                break;
            case FAVORITE_CHARACTERS:
                onCharactersClicked(id);
                break;
        }
    }

    private void onCharactersClicked(long id) {
        //TODO add
        getRouter().showSystemMessage("Просмотр персонажей в разработке");
    }

    private void onProducersClicked(long id) {
//TODO add
        getRouter().showSystemMessage("Просмотр людей в разработке");
    }

    private void onPeopleClicked(long id) {
//TODO add
        getRouter().showSystemMessage("Просмотр людей в разработке");
    }

    private void onMangaClicked(long id) {
//TODO add
        getRouter().showSystemMessage("Манга в разработке");
    }

    private void onSeyuClicked(long id) {
//TODO add
        getRouter().showSystemMessage("Просмотр людей в разработке");
    }

    private void onMangakasClicked(long id) {
//TODO add
        getRouter().showSystemMessage("Просмотр людей в разработке");
    }

    private void onAnimeClicked(long id) {
        getRouter().navigateTo(Screens.ANIME_DETAILS, id);
    }

    private void onSendMessageClicked(long id) {
//TODO add
        getRouter().showSystemMessage("Обмен сообщениями в разработке");
    }

    private void onMailBoxClicked(long id) {
//TODO add
        getRouter().showSystemMessage("Почта в разработке");
    }

    private void onHistoryClicked(long id) {
        getRouter().navigateTo(Screens.HISTORY, id);
    }

    private void onIgnoreClicked(long id) {
        Disposable disposable = interactor.ignoreUser(id)
                .doOnSubscribe(disposable1 -> userIgnored())
                .subscribe(this::loadUserProfile, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    //TODO resource provider
    private void userIgnored() {
        getRouter().showSystemMessage(user.getNickname() + " добавлен в игнор");
        loadUserProfile();
    }

    private void onUnIgnoreClicked(long id) {
        Disposable disposable = interactor.unignoreUser(id)
                .doOnSubscribe(disposable1 -> userUnIgnored())
                .subscribe(this::loadUserProfile, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void userUnIgnored() {
        getRouter().showSystemMessage(user.getNickname() + " удален из игнора");
    }

    private void onFriendClicked(long id) {
        getRouter().navigateTo(Screens.PROFILE, id);
    }

    private void onAddFriend(long id) {
        Disposable disposable = interactor.addToFriends(id)
                .doOnSubscribe(disposable1 -> friendAdded())
                .subscribe(this::loadUserProfile, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void friendAdded() {
        getRouter().showSystemMessage(user.getNickname() + " добавлен в друзья");
    }

    private void onRemoveFriendClicked(long id) {
        Disposable disposable = interactor.deleteFriend(id)
                .doOnSubscribe(disposable1 -> friendRemoved())
                .subscribe(this::loadUserProfile, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void friendRemoved() {
        getRouter().showSystemMessage(user.getNickname() + " удален из друзей");
    }

    private void onRatesClicked() {
        getRouter().navigateTo(BottomScreens.FAVORITE, userId);
    }

    private void onClubClicked(long id) {
        //TODO clubs
        getRouter().showSystemMessage("Клубы в разработке");
    }

    private void onBansClicked(long id) {
        //TODO bans
        getRouter().showSystemMessage("История бананов в разработке");
    }
}
