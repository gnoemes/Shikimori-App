package com.gnoemes.shikimoriapp.presentation.presenter.menu;

import com.arellomobile.mvp.InjectViewState;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.domain.app.AnalyticsInteractor;
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor;
import com.gnoemes.shikimoriapp.domain.user.UserInteractor;
import com.gnoemes.shikimoriapp.entity.app.domain.AnalyticsEvent;
import com.gnoemes.shikimoriapp.entity.app.domain.AuthType;
import com.gnoemes.shikimoriapp.entity.app.domain.UserSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.entity.menu.domain.MenuCategory;
import com.gnoemes.shikimoriapp.entity.menu.presentration.BaseMenuItem;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuCategoryViewModel;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuCategoryWithBadgeViewModel;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuDividerViewModel;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuProfileViewModel;
import com.gnoemes.shikimoriapp.entity.user.domain.UserBrief;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BaseNetworkPresenter;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class MenuPresenter extends BaseNetworkPresenter<MenuView> {

    private UserSettingsInteractor settingsInteractor;
    private UserInteractor userInteractor;
    private AnalyticsInteractor analyticsInteractor;

    private UserSettings settings;

    public MenuPresenter(UserSettingsInteractor settingsInteractor,
                         UserInteractor userInteractor,
                         AnalyticsInteractor analyticsInteractor) {
        this.settingsInteractor = settingsInteractor;
        this.userInteractor = userInteractor;
        this.analyticsInteractor = analyticsInteractor;
    }

    @Override
    public void initData() {
        loadList();
        loadUserSettings();
        getViewState().setTitle(R.string.common_menu);
    }

    private void loadList() {
        List<BaseMenuItem> items = new ArrayList<>();

        items.add(new MenuProfileViewModel(UserStatus.GUEST, null, null));
        items.add(new MenuDividerViewModel());
        items.add(new MenuCategoryWithBadgeViewModel(MenuCategory.NEWS, false, 0));
        items.add(new MenuCategoryWithBadgeViewModel(MenuCategory.NOTIFICATIONS, false, 0));
        items.add(new MenuCategoryWithBadgeViewModel(MenuCategory.MESSAGES, false, 0));
        items.add(new MenuCategoryWithBadgeViewModel(MenuCategory.FRIENDS, false, 0));
        items.add(new MenuDividerViewModel());
        items.add(new MenuCategoryViewModel(MenuCategory.SETTINGS));
        items.add(new MenuDividerViewModel());
        items.add(new MenuCategoryViewModel(MenuCategory.FOUR_PDA));
        items.add(new MenuCategoryViewModel(MenuCategory.SHIKIMORI_CLUB));

        getViewState().showList(items);
    }

    private void loadUserProfile() {
        Disposable disposable = userInteractor.getMyUser()
                .subscribe(this::setUserProfile, this::processErrors);

        unsubscribeOnDestroy(disposable);
    }

    private void setUserProfile(UserBrief userBrief) {
        getViewState().updateUser(new MenuProfileViewModel(
                settings.getStatus(),
                userBrief.getNickname(),
                userBrief.getAvatarUrl()));
    }

    private void loadUserSettings() {
        Disposable disposable = settingsInteractor.getUserSettings()
                .forEach(this::setUserSettings);

        unsubscribeOnDestroy(disposable);
    }

    public void updateSettings() {
        Disposable disposable = settingsInteractor.saveUserSettings(new UserSettings())
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }


    //TODO add navigation
    public void onAction(MenuCategory category) {
        switch (category) {
            case PROFILE:
                onProfileClicked();
                break;
            case NEWS:
                onNewsClicked();
                break;
            case NOTIFICATIONS:
                onNotificationsClicked();
                break;
            case MESSAGES:
                onMessagesClicked();
                break;
            case FRIENDS:
                onFriendsClicked();
                break;
            case SETTINGS:
                onSettingsClicked();
                break;
            case FOUR_PDA:
                getRouter().navigateTo(Screens.WEB, Constants.FOUR_PDA_THEME_URL);
                break;
            case SHIKIMORI_CLUB:
                getRouter().navigateTo(Screens.WEB, Constants.SHIKIMORI_CLUB_URL);
                break;
        }
    }

    private void onSettingsClicked() {
        analyticsInteractor.logEvent(AnalyticsEvent.SETTINGS_OPENED);
        getRouter().navigateTo(Screens.SETTINGS);
    }

    private void onFriendsClicked() {
        showNextUpdateMessage();
    }

    private void showNextUpdateMessage() {
        getRouter().showSystemMessage("В разработке. Будет добавлено в следующем обновлении ;)");
    }

    private void onMessagesClicked() {
        showNextUpdateMessage();
    }

    private void onNotificationsClicked() {
        showNextUpdateMessage();
    }

    private void onNewsClicked() {
        showNextUpdateMessage();
    }

    private void onProfileClicked() {
        switch (settings.getStatus()) {
            case AUTHORIZED:
                getRouter().navigateTo(Screens.PROFILE, settings.getUserBrief().getId());
                break;
            case GUEST:
                getViewState().showAuthTypeDialog();
                break;
        }
    }

    private void setUserSettings(UserSettings settings) {
        this.settings = settings;

        if (settings.getStatus() == UserStatus.AUTHORIZED) {
            loadUserProfile();
        } else {
            getViewState().updateUser(new MenuProfileViewModel(UserStatus.GUEST, null, null));
        }
    }

    public void onSignIn() {
        getRouter().navigateTo(Screens.AUTHORIZATION, AuthType.OAUTH);
    }

    public void onSignUp() {
        getRouter().navigateTo(Screens.AUTHORIZATION, AuthType.SIGN_UP);
    }
}
