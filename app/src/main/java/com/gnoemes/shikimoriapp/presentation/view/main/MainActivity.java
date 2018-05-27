package com.gnoemes.shikimoriapp.presentation.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.presentation.presenter.main.MainPresenter;
import com.gnoemes.shikimoriapp.presentation.view.bottom.CalendarFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.bottom.FavoriteFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.bottom.MenuFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.bottom.SearchFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.bottom.SocialFragmentContainer;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MainResourceProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView, RouterProvider {

    @BindView(R.id.bottom_nav)
    BottomNavigationBar bottomNav;

    @InjectPresenter
    MainPresenter presenter;
    @Inject
    NavigatorHolder navigatorHolder;
    @Inject
    MainResourceProvider resourceProvider;

    private FavoriteFragmentContainer favoriteTabFragment;
    private CalendarFragmentContainer calendarTabFragment;
    private SearchFragmentContainer searchTabFragment;
    private SocialFragmentContainer socialTabFragment;
    private MenuFragmentContainer menuTabFragment;

    @ProvidePresenter
    MainPresenter provideMainPresenter() {
        return presenterProvider.get();
    }

    ///////////////////////////////////////////////////////////////////////////
    // UI METHODS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initContainers();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(AppExtras.ARGUMENT_SELECT_TAB, bottomNav.getCurrentSelectedPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (bottomNav != null && savedInstanceState != null) {
            bottomNav.selectTab(savedInstanceState.getInt(AppExtras.ARGUMENT_SELECT_TAB));
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    @LayoutRes
    protected int getLayoutActivity() {
        return R.layout.activity_main;
    }

    @Override
    protected Navigator getNavigator() {
        if (localNavigator == null) {
            localNavigator = new SupportAppNavigator(this, fragmentManager, R.id.activity_container) {


                @Override
                protected Intent createActivityIntent(Context context, String screenKey, Object data) {
                    return null;
                }

                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    return null;
                }

                @Override
                protected void showSystemMessage(String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected void replace(Replace command) {
                    switch (command.getScreenKey()) {
                        case BottomScreens.FAVORITE:
                            fragmentManager.beginTransaction()
                                    .detach(calendarTabFragment)
                                    .detach(searchTabFragment)
                                    .detach(socialTabFragment)
                                    .detach(menuTabFragment)
                                    .attach(favoriteTabFragment)
                                    .commitNow();
                            break;
                        case BottomScreens.CALENDAR:
                            fragmentManager.beginTransaction()
                                    .detach(favoriteTabFragment)
                                    .detach(searchTabFragment)
                                    .detach(socialTabFragment)
                                    .detach(menuTabFragment)
                                    .attach(calendarTabFragment)
                                    .commitNow();
                            break;
                        case BottomScreens.SEARCH:
                            fragmentManager.beginTransaction()
                                    .detach(calendarTabFragment)
                                    .detach(favoriteTabFragment)
                                    .detach(socialTabFragment)
                                    .detach(menuTabFragment)
                                    .attach(searchTabFragment)
                                    .commitNow();
                            break;
                        case BottomScreens.SOCIAL:
                            fragmentManager.beginTransaction()
                                    .detach(calendarTabFragment)
                                    .detach(searchTabFragment)
                                    .detach(favoriteTabFragment)
                                    .detach(menuTabFragment)
                                    .attach(socialTabFragment)
                                    .commitNow();
                            break;
                        case BottomScreens.MENU:
                            menuTabFragment.clearRouter();
                            fragmentManager.beginTransaction()
                                    .detach(calendarTabFragment)
                                    .detach(searchTabFragment)
                                    .detach(socialTabFragment)
                                    .detach(favoriteTabFragment)
                                    .attach(menuTabFragment)
                                    .commitNow();
                            break;
                    }
                }

                boolean canExit = false;

                @Override
                protected void exit() {
                    if (!canExit) {
                        getPresenter().getRouter().showSystemMessage(resourceProvider.getExitMessage());
                        canExit = true;
                        new Handler().postDelayed(() -> canExit = false, Constants.EXIT_TIMEOUT);
                    } else {
                        finish();
                    }
                }
            };
        }


        return localNavigator;
    }

    @Override
    protected NavigatorHolder getNavigatorHolder() {
        return navigatorHolder;
    }

    @Override
    protected MainPresenter getPresenter() {
        return presenter;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void initBottomNavigation() {
        bottomNav.addItem(new BottomNavigationItem(R.drawable.ic_star, R.string.common_favorite))
                .addItem(new BottomNavigationItem(R.drawable.ic_calendar, R.string.common_calendar))
                .addItem(new BottomNavigationItem(R.drawable.ic_search, R.string.common_search))
                .addItem(new BottomNavigationItem(R.drawable.ic_group, R.string.common_main))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu, R.string.common_menu))
                .initialise();

        bottomNav.setTabSelectedListener(new BottomNavigationBar.SimpleOnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case TabPosition.FAVORITE:
                        getPresenter().onFavoriteSelected();
                        break;
                    case TabPosition.CALENDAR:
                        getPresenter().onCalendarSelected();
                        break;
                    case TabPosition.SEARCH:
                        getPresenter().onSearchSelected();
                        break;
                    case TabPosition.SOCIAL:
                        getPresenter().onSocialSelected();
                        break;
                    case TabPosition.MENU:
                        getPresenter().onMenuSelected();
                        break;

                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////////////////////////////////

    private void initContainers() {
        createFavoritePage();
        createCalendarPage();
        createSearchPage();
        createSocialPage();
        createMenuPage();
    }

    private void createMenuPage() {
        menuTabFragment = (MenuFragmentContainer) fragmentManager.findFragmentByTag(BottomScreens.MENU);
        if (menuTabFragment == null) {
            menuTabFragment = MenuFragmentContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, menuTabFragment, BottomScreens.MENU)
                    .detach(menuTabFragment).commitNow();
        }
    }

    private void createSocialPage() {
        socialTabFragment = (SocialFragmentContainer) fragmentManager.findFragmentByTag(BottomScreens.SOCIAL);
        if (socialTabFragment == null) {
            socialTabFragment = SocialFragmentContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, socialTabFragment, BottomScreens.SOCIAL)
                    .detach(socialTabFragment).commitNow();
        }
    }

    private void createSearchPage() {
        searchTabFragment = (SearchFragmentContainer) fragmentManager.findFragmentByTag(BottomScreens.SEARCH);
        if (searchTabFragment == null) {
            searchTabFragment = SearchFragmentContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, searchTabFragment, BottomScreens.SEARCH)
                    .detach(searchTabFragment).commitNow();
        }
    }

    private void createCalendarPage() {
        calendarTabFragment = (CalendarFragmentContainer) fragmentManager.findFragmentByTag(BottomScreens.CALENDAR);
        if (calendarTabFragment == null) {
            calendarTabFragment = CalendarFragmentContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, calendarTabFragment, BottomScreens.CALENDAR)
                    .detach(calendarTabFragment).commitNow();
        }
    }

    private void createFavoritePage() {
        favoriteTabFragment = (FavoriteFragmentContainer) fragmentManager.findFragmentByTag(BottomScreens.FAVORITE);
        if (favoriteTabFragment == null) {
            favoriteTabFragment = FavoriteFragmentContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, favoriteTabFragment, BottomScreens.FAVORITE)
                    .detach(favoriteTabFragment).commitNow();
        }
    }

    @Override
    public Router getLocalRouter() {
        return getPresenter().getRouter();
    }

    @Override
    public Navigator getLocalNavigator() {
        return getNavigator();
    }

    private class TabPosition {
        final static int FAVORITE = 0;
        final static int CALENDAR = 1;
        final static int SEARCH = 2;
        final static int SOCIAL = 3;
        final static int MENU = 4;
    }
}

