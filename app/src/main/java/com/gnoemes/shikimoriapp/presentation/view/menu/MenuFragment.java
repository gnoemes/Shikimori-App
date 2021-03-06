package com.gnoemes.shikimoriapp.presentation.view.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuCategoryWithBadgeViewModel;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuProfileViewModel;
import com.gnoemes.shikimoriapp.presentation.presenter.menu.MenuPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.menu.adapter.MenuAdapter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MenuFragment extends BaseFragment<MenuPresenter, MenuView> implements MenuView {

    @BindView(R.id.list_settings)
    RecyclerView recyclerView;

    @Inject
    ImageLoader imageLoader;

    @InjectPresenter
    MenuPresenter presenter;
    private MenuAdapter menuAdapter;

    @ProvidePresenter
    MenuPresenter providePresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }
        return presenter;
    }

    public static MenuFragment newInstance() {
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().updateSettings();
    }

    private void initList() {
        menuAdapter = new MenuAdapter(imageLoader, category -> getPresenter().onAction(category));
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected MenuPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_menu;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public void showList(List<BaseItem> menuItems) {
        menuAdapter.bindItems(menuItems);
    }

    @Override
    public void updateBadge(MenuCategoryWithBadgeViewModel viewModel) {
        menuAdapter.updateBadge(viewModel);
    }

    @Override
    public void updateUser(MenuProfileViewModel viewModel) {
        menuAdapter.updateUser(viewModel);
    }

    @Override
    public void showAuthTypeDialog() {
        new MaterialDialog.Builder(getContext())
                .content(R.string.auth_dialog_conent)
                .contentColorAttr(R.attr.colorText)
                .alwaysCallSingleChoiceCallback()
                .backgroundColorAttr(R.attr.colorBackgroundWindow)
                .negativeColorAttr(R.attr.colorAction)
                .negativeText(R.string.common_cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
                .positiveColorAttr(R.attr.colorAction)
                .positiveText(R.string.common_sign_in)
                .onPositive((dialog, which) -> getPresenter().onSignIn())
                .neutralColorAttr(R.attr.colorAction)
                .neutralText(R.string.common_sign_up)
                .onNeutral((dialog, which) -> getPresenter().onSignUp())
                .autoDismiss(true)
                .canceledOnTouchOutside(true)
                .build()
                .show();
    }
}
