package com.gnoemes.shikimoriapp.presentation.view.alternative.translations;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationNavigationData;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.presentation.presenter.alternative.AlternativeTranslationsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.adapter.TranslationAdapter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentViewWithButton;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.VerticalSpaceItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class AlternativeTranslationsFragment extends BaseFragment<AlternativeTranslationsPresenter, AlternativeTranslationsView>
        implements AlternativeTranslationsView {

    @BindView(R.id.view_empty)
    EmptyContentViewWithButton emptyContentView;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @InjectPresenter
    AlternativeTranslationsPresenter presenter;
    private TranslationAdapter adapter;

    public static AlternativeTranslationsFragment newInstance(AlternativeTranslationNavigationData data) {
        AlternativeTranslationsFragment fragment = new AlternativeTranslationsFragment();
        Bundle args = new Bundle();
        args.putLong(AppExtras.ARGUMENT_EPISODE_ID, data.getEpisodeId());
        args.putSerializable(AppExtras.ARGUMENT_TRANSLATION_TYPE, data.getType());
        args.putLong(AppExtras.ARGUMENT_ANIME_ID, data.getAnimeId());
        args.putLong(AppExtras.ARGUMENT_ANIME_RATE_ID, data.getRateId());
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    AlternativeTranslationsPresenter providePresenter() {
        presenter = presenterProvider.get();
        if (getArguments() != null) {
            if (getParentFragment() != null) {
                presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
            }
            presenter.setEpisodeId(getArguments().getLong(AppExtras.ARGUMENT_EPISODE_ID));
            presenter.setCurrentTranslationType((AlternativeTranslationType) getArguments().getSerializable(AppExtras.ARGUMENT_TRANSLATION_TYPE));
            presenter.setAnimeId(getArguments().getLong(AppExtras.ARGUMENT_ANIME_ID));
            presenter.setRateId(getArguments().getLong(AppExtras.ARGUMENT_ANIME_RATE_ID));
        }

        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        emptyContentView.setText(R.string.translations_empty);
        emptyContentView.setCallback(v -> getPresenter().onFindAll());
        emptyContentView.setVisibility(View.GONE);
        networkErrorView.setVisibility(View.GONE);

        refreshLayout.setOnRefreshListener(() -> getPresenter().loadTranslations());

        adapter = new TranslationAdapter(getPresenter()::onTranslationClicked);

        int margin = (int) getResources().getDimension(R.dimen.margin_small);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(margin));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Drawable navigationIcon = DrawableHelper
                .withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
        toolbar.inflateMenu(R.menu.menu_translations);

        Drawable icon = DrawableHelper
                .withContext(getContext())
                .withDrawable(R.drawable.ic_settings)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.getMenu()
                .getItem(0)
                .setIcon(icon);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_settings:
                    getPresenter().onSettingsClicked();
                    break;
            }
            return false;
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected AlternativeTranslationsPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_translations;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showTranslations(List<AlternativeTranslationViewModel> models) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.bindItems(models);
    }

    @Override
    public void hideErrorView() {
        networkErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        networkErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        emptyContentView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyView() {
        emptyContentView.setVisibility(View.GONE);
    }

    @Override
    public void onShowLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showSettingsDialog() {
        List<AlternativeTranslationType> types = Arrays.asList(AlternativeTranslationType.values());
        new MaterialDialog.Builder(getContext())
                .items(R.array.translations_type_alternative)
                .itemsCallback((dialog, itemView, position, text) -> {
                    dialog.dismiss();
                    getPresenter().onTypeClicked(types.get(position));
                })
                .autoDismiss(true)
                .titleColorAttr(R.attr.colorText)
                .contentColorAttr(R.attr.colorText)
                .alwaysCallSingleChoiceCallback()
                .backgroundColorAttr(R.attr.colorBackgroundWindow)
                .autoDismiss(false)
                .negativeColorAttr(R.attr.colorAction)
                .negativeText(R.string.common_cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
                .canceledOnTouchOutside(true)
                .build()
                .show();
    }
}
