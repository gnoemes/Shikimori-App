package com.gnoemes.shikimoriapp.presentation.view.main;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
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
import com.gnoemes.shikimoriapp.presentation.view.bottom.BottomTabContainer;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MainResourceProvider;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.joda.time.DateTime;

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

        mockMessage();
    }

    private void mockMessage() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String s = sp.getString("lastLaunch", "");
        if (TextUtils.isEmpty(s) || new DateTime(s).plusHours(3).isBeforeNow()) {
            String message = "Наблюдаются проблемы с получением видео из-за новой политики сайта. Встроенный плеер временно отключен. Подробнее в теме на 4PDA.";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://4pda.ru/forum/index.php?s=&showtopic=903970&view=findpost&p=74831685"));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "mock_channel")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Внимание")
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (mNotificationManager != null) {
                mNotificationManager.notify(0, builder.build());
            }
            sp.edit().putString("lastLaunch", DateTime.now().toString()).apply();
        }
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
                    testAnalytics(command.getScreenKey());
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

    private void testAnalytics(String screenKey) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screenKey);
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
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
        menuTabFragment = (BottomTabContainer) fragmentManager.findFragmentByTag(BottomScreens.MENU);
        if (menuTabFragment == null) {
            menuTabFragment = BottomTabContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, menuTabFragment, BottomScreens.MENU)
                    .detach(menuTabFragment).commitNow();
        }
    }

    private void createSocialPage() {
        socialTabFragment = (BottomTabContainer) fragmentManager.findFragmentByTag(BottomScreens.SOCIAL);
        if (socialTabFragment == null) {
            socialTabFragment = BottomTabContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, socialTabFragment, BottomScreens.SOCIAL)
                    .detach(socialTabFragment).commitNow();
        }
    }

    private void createSearchPage() {
        searchTabFragment = (BottomTabContainer) fragmentManager.findFragmentByTag(BottomScreens.SEARCH);
        if (searchTabFragment == null) {
            searchTabFragment = BottomTabContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, searchTabFragment, BottomScreens.SEARCH)
                    .detach(searchTabFragment).commitNow();
        }
    }

    private void createCalendarPage() {
        calendarTabFragment = (BottomTabContainer) fragmentManager.findFragmentByTag(BottomScreens.CALENDAR);
        if (calendarTabFragment == null) {
            calendarTabFragment = BottomTabContainer.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_container, calendarTabFragment, BottomScreens.CALENDAR)
                    .detach(calendarTabFragment).commitNow();
        }
    }

    private void createFavoritePage() {
        favoriteTabFragment = (BottomTabContainer) fragmentManager.findFragmentByTag(BottomScreens.FAVORITE);
        if (favoriteTabFragment == null) {
            favoriteTabFragment = BottomTabContainer.newInstance();
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

