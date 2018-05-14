package com.gnoemes.shikimoriapp.presentation.view.bottom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.domain.AuthType;
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.presentation.view.auth.AuthActivity;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;
import com.gnoemes.shikimoriapp.presentation.view.settings.SettingsActivity;

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
}
