package com.gnoemes.shikimoriapp.presentation.view.bottom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationNavigationData;
import com.gnoemes.shikimoriapp.entity.app.domain.AuthType;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.entity.main.presentation.LocalCiceroneHolder;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeFragment;
import com.gnoemes.shikimoriapp.presentation.view.auth.AuthActivity;
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.favorite.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.SearchFragment;
import com.gnoemes.shikimoriapp.presentation.view.social.SocialFragment;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsFragment;
import com.gnoemes.shikimoriapp.utils.view.BackButtonListener;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

/**
 * Tab container for fragments with local routers
 */
public class BottomTabContainer extends Fragment implements RouterProvider, BackButtonListener {

    /**
     * Contains ciceron instances
     */
    @Inject
    LocalCiceroneHolder ciceroneHolder;

    /**
     * Navigator for fragments
     */
    private Navigator localNavigator;

    /**
     * Router instance for tabs
     */

    public static Fragment newInstance(String name) {
        BottomTabContainer container = new BottomTabContainer();
        Bundle args = new Bundle();
        args.putString(AppExtras.ARGUMENT_SCREEN, name);
        container.setArguments(args);
        return container;
    }

    ///////////////////////////////////////////////////////////////////////////
    // UI METHODS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getChildFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            getCicerone().getRouter().replaceScreen(getContainerName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        getCicerone().getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns current screen name
     *
     * @return String local screen name
     */
    private String getContainerName() {
        if (getArguments() != null) {
            return getArguments().getString(AppExtras.ARGUMENT_SCREEN);
        }
        return null;
    }

    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(getContainerName());
    }

    /**
     * Returns current router instance
     *
     * @return Router
     */
    @Override
    public Router getLocalRouter() {
        return getCicerone().getRouter();
    }

    /**
     * Returns navigator
     *
     * @return Navigator
     */
    protected Navigator getNavigator() {
        if (localNavigator == null) {
            localNavigator = new SupportAppNavigator(getActivity(), getChildFragmentManager(), R.id.fragment_container) {
                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    switch (screenKey) {
                        case BottomScreens.FAVORITE:
                            return FavoriteFragment.newInstance();
                        case BottomScreens.CALENDAR:
                            return CalendarFragment.newInstance();
                        case BottomScreens.SEARCH:
                            return SearchFragment.newInstance((AnimeGenre) data);
                        case BottomScreens.SOCIAL:
                            return SocialFragment.newInstance();
                        case BottomScreens.MENU:
                            return MenuFragment.newInstance();
                        case Screens.ANIME_DETAILS:
                            return AnimeFragment.newInstance((Long) data);
                        case Screens.TRANSLATIONS:
                            return TranslationsFragment.newInstance((TranslationNavigationData) data);

                    }
                    return null;
                }

                @Override
                protected Intent createActivityIntent(Context context, String screenKey, Object data) {

                    switch (screenKey) {
                        case Screens.AUTHORIZATION:
                            return AuthActivity.newIntent(context, (AuthType) data);
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
                    //TODO transactions
                    super.setupFragmentTransactionAnimation(command, currentFragment, nextFragment, fragmentTransaction);
                }
            };
        }

        return localNavigator;
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null
                && fragment instanceof BaseFragmentView) {
            ((BaseFragmentView) fragment).onBackPressed();
            return true;
        } else {
            return false;
        }
    }
}
