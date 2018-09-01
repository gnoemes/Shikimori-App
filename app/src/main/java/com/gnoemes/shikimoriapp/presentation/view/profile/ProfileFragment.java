package com.gnoemes.shikimoriapp.presentation.view.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.presentation.presenter.profile.ProfilePresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.profile.adapter.ProfileAdapter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ProfileFragment extends BaseFragment<ProfilePresenter, ProfileView>
        implements ProfileView {

    @BindView(R.id.list)
    RecyclerView list;

    @InjectPresenter
    ProfilePresenter presenter;
    @Inject
    ImageLoader loader;
    private ProfileAdapter adapter;

    public static ProfileFragment newInstance(long userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putLong(AppExtras.ARGUMENT_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    ProfilePresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        if (getArguments() != null) {
            presenter.setUserId(getArguments().getLong(AppExtras.ARGUMENT_USER_ID));
        }

        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews() {
        adapter = new ProfileAdapter(loader,
                (action, id) -> getPresenter().onAction(action, id));
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.inflateMenu(R.menu.menu_profile);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_exit:
                    getPresenter().onExitPressed();
                    break;
            }
            return false;
        });

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected ProfilePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_profile;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setProfile(List<BaseProfileItem> items) {
        adapter.bindItems(items);
    }

    @Override
    public void updateHead(BaseProfileItem baseProfileItem) {
        adapter.updateHead(baseProfileItem);
    }

    @Override
    public void updateSocial(BaseProfileItem baseProfileItem) {
        adapter.updateSocial(baseProfileItem);
    }

    @Override
    public void updateRates(BaseProfileItem baseProfileItem) {
        adapter.updateRateStatuses(baseProfileItem);
    }

    @Override
    public void updateOther(BaseProfileItem item) {
        adapter.updateOther(item);
    }

    @Override
    public void addExitMenu() {
        MenuItem item = toolbar.getMenu().getItem(0);
        item.setVisible(true);

        Drawable icon = DrawableHelper
                .withContext(getContext())
                .withDrawable(item.getIcon())
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        item.setIcon(icon);

    }

    @Override
    public void showLogoutDialog() {
//        new MaterialDialog.Builder(getContext())
//                .content(R.string.exit_text)
//                .autoDismiss(true)
//                .titleColorAttr(R.attr.colorText)
//                .contentColorAttr(R.attr.colorText)
//                .alwaysCallSingleChoiceCallback()
//                .backgroundColorAttr(R.attr.colorBackgroundWindow)
//                .negativeColorAttr(R.attr.colorAction)
//                .negativeText(R.string.common_cancel)
//                .onNegative((dialog, which) -> dialog.dismiss())
//                .positiveColorAttr(R.attr.colorAction)
//                .positiveText(R.string.yes)
//                .onPositive((dialog, which) -> getPresenter().onExit())
//                .canceledOnTouchOutside(true)
//                .build()
//                .show();
    }
}
