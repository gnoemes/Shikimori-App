package com.gnoemes.shikimoriapp.presentation.view.bottom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationNavigationData;
import com.gnoemes.shikimoriapp.entity.app.domain.AuthType;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.entity.related.domain.RelatedNavigationData;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeFragment;
import com.gnoemes.shikimoriapp.presentation.view.auth.AuthActivity;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryFragment;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;
import com.gnoemes.shikimoriapp.presentation.view.player.WebPlayerActivity;
import com.gnoemes.shikimoriapp.presentation.view.profile.ProfileFragment;
import com.gnoemes.shikimoriapp.presentation.view.related.RelatedFragment;
import com.gnoemes.shikimoriapp.presentation.view.settings.SettingsActivity;
import com.gnoemes.shikimoriapp.presentation.view.similar.SimilarFragment;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsFragment;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

public class MenuFragmentContainer extends BottomTabContainer {

    /**
     * Navigator for fragments
     */
    private Navigator localNavigator;

    public static MenuFragmentContainer newInstance() {
        Bundle args = new Bundle();
        MenuFragmentContainer fragment = new MenuFragmentContainer();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Returns navigator
     *
     * @return Navigator
     */
    protected Navigator getNavigator() {
        if (localNavigator == null) {
            localNavigator = new SupportAppNavigator(getActivity(), childFragmentManager, R.id.fragment_container) {
                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    switch (screenKey) {
                        case BottomScreens.MENU:
                            return MenuFragment.newInstance();
                        case BottomScreens.FAVORITE:
                            return FavoriteFragment.newInstance((Long) data);
                        case Screens.PROFILE:
                            return ProfileFragment.newInstance((Long) data);
                        case Screens.ANIME_DETAILS:
                            return AnimeFragment.newInstance((Long) data);
                        case Screens.TRANSLATIONS:
                            return TranslationsFragment.newInstance((TranslationNavigationData) data);
                        case Screens.SIMILAR:
                            return SimilarFragment.newInstance((Long) data);
                        case Screens.HISTORY:
                            return HistoryFragment.newInstance((Long) data);
                        case Screens.RELATED:
                            return RelatedFragment.newInstance((RelatedNavigationData) data);
                    }
                    return null;
                }

                @Override
                protected Intent createActivityIntent(Context context, String screenKey, Object data) {

                    switch (screenKey) {
                        case Screens.AUTHORIZATION:
                            return AuthActivity.newIntent(context, (AuthType) data);
                        case Screens.WEB:
                            return new Intent(Intent.ACTION_VIEW, Uri.parse((String) data));
                        case Screens.SETTINGS:
                            return new Intent(context, SettingsActivity.class);
                        case Screens.WEB_PLAYER:
                            return WebPlayerActivity.newIntent(context, (String) data);
                    }
                    return null;
                }

                @Override
                protected void showSystemMessage(String message) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }

                @Override
                protected void exit() {
                    ((RouterProvider) getActivity()).getLocalRouter().exit();
                }

                @Override
                protected void setupFragmentTransactionAnimation(Command command, Fragment currentFragment, Fragment nextFragment, FragmentTransaction fragmentTransaction) {
                    //TODO shared elements

                    if (currentFragment != null && nextFragment != null) {
                        Fade fade = new Fade();
                        fade.setDuration(Constants.FADE_DURATION);
                        nextFragment.setEnterTransition(fade);
                        nextFragment.setExitTransition(fade);
                    }
                }
            };
        }

        return localNavigator;
    }
}
