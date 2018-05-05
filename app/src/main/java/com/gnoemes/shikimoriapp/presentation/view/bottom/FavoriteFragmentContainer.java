package com.gnoemes.shikimoriapp.presentation.view.bottom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.main.presentation.BottomScreens;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.favorite.FavoriteFragment;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

public class FavoriteFragmentContainer extends BottomTabContainer {

    /**
     * Navigator for fragments
     */
    private Navigator localNavigator;

    public static FavoriteFragmentContainer newInstance() {
        Bundle args = new Bundle();
        FavoriteFragmentContainer fragment = new FavoriteFragmentContainer();
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
                        case BottomScreens.FAVORITE:
                            return FavoriteFragment.newInstance();
                    }
                    return null;
                }

                @Override
                protected Intent createActivityIntent(Context context, String screenKey, Object data) {
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
