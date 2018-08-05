package com.gnoemes.shikimoriapp.presentation.view.person;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.presenter.person.PersonPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentView;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.person.adapter.PersonDetailsAdapter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class PersonFragment extends BaseFragment<PersonPresenter, PersonView> implements PersonView {

    @BindView(R.id.coordinator)
    CoordinatorLayout layout;

    @BindView(R.id.progress_loading)
    ProgressBar progressBar;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @BindView(R.id.view_empty)
    EmptyContentView emptyContentView;

    @InjectPresenter
    PersonPresenter presenter;
    @Inject
    ImageLoader imageLoader;

    private PersonDetailsAdapter adapter;

    public static PersonFragment newInstance(long personId) {
        Bundle args = new Bundle();
        PersonFragment fragment = new PersonFragment();
        args.putLong(AppExtras.ARGUMENT_PERSON_ID, personId);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    PersonPresenter providePersonPresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }
        if (getArguments() != null) {
            presenter.setPersonId(getArguments().getLong(AppExtras.ARGUMENT_PERSON_ID));
        }
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        adapter = new PersonDetailsAdapter(imageLoader, getPresenter()::onRelatedItemClicked);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());

        Drawable overflowIcon = DrawableHelper.withContext(getContext())
                .withDrawable(toolbar.getOverflowIcon())
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setOverflowIcon(overflowIcon);
        toolbar.inflateMenu(R.menu.menu_person);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_open:
                    getPresenter().onOpenInBrowserClicked();
                    break;
            }

            return false;
        });

        emptyContentView.setText(R.string.error_unprocessable);
        networkErrorView.setText(R.string.common_error_message_without_pull);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected PersonPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_person_details;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setData(List<BaseItem> baseItems) {
        adapter.bindItems(baseItems);
    }

    @Override
    public void onShowLoading() {
        TransitionManager.beginDelayedTransition(layout, new Fade());
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onHideLoading() {
        TransitionManager.beginDelayedTransition(layout, new Fade());
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        emptyContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        emptyContentView.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError() {
        networkErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkError() {
        networkErrorView.setVisibility(View.GONE);
    }
}