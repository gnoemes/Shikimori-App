package com.gnoemes.shikimoriapp.presentation.view.bottom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.di.base.modules.BaseFragmentModule;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationNavigationData;
import com.gnoemes.shikimoriapp.entity.app.domain.AuthType;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.entity.main.presentation.LocalCiceroneHolder;
import com.gnoemes.shikimoriapp.entity.related.domain.RelatedNavigationData;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.ScreenshotNavigationData;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeFragment;
import com.gnoemes.shikimoriapp.presentation.view.auth.AuthActivity;
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryFragment;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;
import com.gnoemes.shikimoriapp.presentation.view.player.WebPlayerActivity;
import com.gnoemes.shikimoriapp.presentation.view.player.embedded.EmbeddedPlayerActivity;
import com.gnoemes.shikimoriapp.presentation.view.profile.ProfileFragment;
import com.gnoemes.shikimoriapp.presentation.view.related.RelatedFragment;
import com.gnoemes.shikimoriapp.presentation.view.screenshots.ScreenshotsActivity;
import com.gnoemes.shikimoriapp.presentation.view.search.SearchFragment;
import com.gnoemes.shikimoriapp.presentation.view.settings.SettingsActivity;
import com.gnoemes.shikimoriapp.presentation.view.similar.SimilarFragment;
import com.gnoemes.shikimoriapp.presentation.view.social.SocialFragment;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsFragment;
import com.gnoemes.shikimoriapp.utils.view.BackButtonListener;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;

/**
 * Tab container for fragments with local routers
 */
public class BottomTabContainer extends MvpAppCompatFragment implements RouterProvider,
        BackButtonListener,
        HasSupportFragmentInjector {

    /**
     * Contains ciceron instances
     */
    @Inject
    protected LocalCiceroneHolder ciceroneHolder;

    /**
     * Child fragment manager
     */
    @Inject
    @Named(BaseFragmentModule.CHILD_FRAGMENT_MANAGER)
    protected FragmentManager childFragmentManager;

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    private Navigator localNavigator;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    public static BottomTabContainer newInstance() {
        Bundle args = new Bundle();
        BottomTabContainer fragment = new BottomTabContainer();
        fragment.setArguments(args);
        return fragment;
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

        if (childFragmentManager.findFragmentById(R.id.fragment_container) == null) {
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

    protected Navigator getNavigator() {
        if (localNavigator == null) {
            localNavigator = new SupportAppNavigator(getActivity(), childFragmentManager, R.id.fragment_container) {
                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    switch (screenKey) {
                        case BottomScreens.FAVORITE:
                            return FavoriteFragment.newInstance((Long) data);
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
                        case Screens.SIMILAR:
                            return SimilarFragment.newInstance((Long) data);
                        case Screens.PROFILE:
                            return ProfileFragment.newInstance((Long) data);
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
                        case Screens.WEB_PLAYER:
                            return WebPlayerActivity.newIntent(context, (String) data);
                        case Screens.WEB:
                            return new Intent(Intent.ACTION_VIEW, Uri.parse((String) data));
                        case Screens.EMBEDDED_PLAYER:
                            return EmbeddedPlayerActivity.newIntent(context, (Long) data);
                        case Screens.SETTINGS:
                            return new Intent(context, SettingsActivity.class);
                        case Screens.SCREENSHOTS:
                            return ScreenshotsActivity.newIntent(context, (ScreenshotNavigationData) data);
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

                    if (command instanceof Forward) {
                        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
                    }

//                    if (currentFragment != null && nextFragment != null) {
//                        AutoTransition fade = new AutoTransition();
//                        fade.setDuration(Constants.FADE_DURATION);
//                        nextFragment.setEnterTransition(fade);
//                        nextFragment.setExitTransition(fade);
//                    }
                }

                @Override
                protected void unknownScreen(Command command) {
                    if (getActivity() != null) {
                        ((RouterProvider) getActivity()).getLocalNavigator().applyCommands(new Command[]{command});
                    }
                }


            };
        }

        return localNavigator;
    }

    @Override
    public Navigator getLocalNavigator() {
        return getNavigator();
    }

    protected String getContainerName() {
        return getTag();
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

    @Override
    public boolean onBackPressed() {
        Fragment fragment = childFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment != null
                && fragment instanceof BaseFragmentView) {
            ((BaseFragmentView) fragment).onBackPressed();
            return true;
        } else {
            return false;
        }
    }
}
