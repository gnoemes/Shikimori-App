package com.gnoemes.shikimoriapp.presentation.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.entity.main.presentation.LocalCiceroneHolder;
import com.gnoemes.shikimoriapp.presentation.presenter.main.MainPresenter;
import com.gnoemes.shikimoriapp.presentation.view.bottom.BottomTabContainer;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MainResourceProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.bottom_nav)
    BottomNavigationBar bottomNav;

    @InjectPresenter
    MainPresenter presenter;
    @Inject
    NavigatorHolder navigatorHolder;
    @Inject
    MainResourceProvider resourceProvider;
    @Inject
    LocalCiceroneHolder localCiceroneHolder;
    private BottomTabContainer favoriteTabFragment;
    private BottomTabContainer calendarTabFragment;
    private BottomTabContainer searchTabFragment;
    private BottomTabContainer socialTabFragment;
    private BottomTabContainer menuTabFragment;

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
    protected Navigator getLocalNavigator() {

        if (localNavigator == null) {
            localNavigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.activity_container) {
                boolean canExit = false;

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
                    FragmentManager fm = getSupportFragmentManager();
                    switch (command.getScreenKey()) {
                        case BottomScreens.FAVORITE:
                            fm.beginTransaction()
                                    .detach(calendarTabFragment)
                                    .detach(searchTabFragment)
                                    .detach(socialTabFragment)
                                    .detach(menuTabFragment)
                                    .attach(favoriteTabFragment)
                                    .commitNow();
                            break;
                        case BottomScreens.CALENDAR:
                            fm.beginTransaction()
                                    .detach(favoriteTabFragment)
                                    .detach(searchTabFragment)
                                    .detach(socialTabFragment)
                                    .detach(menuTabFragment)
                                    .attach(calendarTabFragment)
                                    .commitNow();
                            break;
                        case BottomScreens.SEARCH:
                            fm.beginTransaction()
                                    .detach(calendarTabFragment)
                                    .detach(favoriteTabFragment)
                                    .detach(socialTabFragment)
                                    .detach(menuTabFragment)
                                    .attach(searchTabFragment)
                                    .commitNow();
                            break;
                        case BottomScreens.SOCIAL:
                            fm.beginTransaction()
                                    .detach(calendarTabFragment)
                                    .detach(searchTabFragment)
                                    .detach(favoriteTabFragment)
                                    .detach(menuTabFragment)
                                    .attach(socialTabFragment)
                                    .commitNow();
                            break;
                        case BottomScreens.MENU:
                            fm.beginTransaction()
                                    .detach(calendarTabFragment)
                                    .detach(searchTabFragment)
                                    .detach(socialTabFragment)
                                    .detach(favoriteTabFragment)
                                    .attach(menuTabFragment)
                                    .commitNow();
                            break;
                    }
                }

                @Override
                protected void exit() {
                    if (!canExit) {
                        showSystemMessage(resourceProvider.getExitMessage());
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
    public void onSetTitle(String title) {
        toolbar.setTitle(title);
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
        FragmentManager fm = getSupportFragmentManager();
        createFavoritePage(fm);
        createCalendarPage(fm);
        createSearchPage(fm);
        createSocialPage(fm);
        createMenuPage(fm);
    }

    private void createMenuPage(FragmentManager fm) {
        menuTabFragment = (BottomTabContainer) fm.findFragmentByTag(BottomScreens.MENU);
        if (menuTabFragment == null) {
            menuTabFragment = (BottomTabContainer) BottomTabContainer.newInstance(BottomScreens.MENU);
            fm.beginTransaction()
                    .add(R.id.activity_container, menuTabFragment, BottomScreens.MENU)
                    .detach(menuTabFragment).commitNow();
        }
    }

    private void createSocialPage(FragmentManager fm) {
        socialTabFragment = (BottomTabContainer) fm.findFragmentByTag(BottomScreens.SOCIAL);
        if (socialTabFragment == null) {
            socialTabFragment = (BottomTabContainer) BottomTabContainer.newInstance(BottomScreens.SOCIAL);
            fm.beginTransaction()
                    .add(R.id.activity_container, socialTabFragment, BottomScreens.SOCIAL)
                    .detach(socialTabFragment).commitNow();
        }
    }

    private void createSearchPage(FragmentManager fm) {
        searchTabFragment = (BottomTabContainer) fm.findFragmentByTag(BottomScreens.SEARCH);
        if (searchTabFragment == null) {
            searchTabFragment = (BottomTabContainer) BottomTabContainer.newInstance(BottomScreens.SEARCH);
            fm.beginTransaction()
                    .add(R.id.activity_container, searchTabFragment, BottomScreens.SEARCH)
                    .detach(searchTabFragment).commitNow();
        }
    }

    private void createCalendarPage(FragmentManager fm) {
        calendarTabFragment = (BottomTabContainer) fm.findFragmentByTag(BottomScreens.CALENDAR);
        if (calendarTabFragment == null) {
            calendarTabFragment = (BottomTabContainer) BottomTabContainer.newInstance(BottomScreens.CALENDAR);
            fm.beginTransaction()
                    .add(R.id.activity_container, calendarTabFragment, BottomScreens.CALENDAR)
                    .detach(calendarTabFragment).commitNow();
        }
    }

    private void createFavoritePage(FragmentManager fm) {
        favoriteTabFragment = (BottomTabContainer) fm.findFragmentByTag(BottomScreens.FAVORITE);
        if (favoriteTabFragment == null) {
            favoriteTabFragment = (BottomTabContainer) BottomTabContainer.newInstance(BottomScreens.FAVORITE);
            fm.beginTransaction()
                    .add(R.id.activity_container, favoriteTabFragment, BottomScreens.FAVORITE)
                    .detach(favoriteTabFragment).commitNow();
        }
    }

    private class TabPosition {
        final static int FAVORITE = 0;
        final static int CALENDAR = 1;
        final static int SEARCH = 2;
        final static int SOCIAL = 3;
        final static int MENU = 4;
    }
}

