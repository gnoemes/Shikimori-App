package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.list.DialogListExtKt;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.presenter.alternative.AlternativeEpisodesPresenter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.adapter.EpisodeAdapter;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.provider.AlternativeEpisodeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.LinearStickyHead;
import com.gnoemes.shikimoriapp.utils.view.VerticalSpaceItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AlternativeEpisodesFragment extends BaseFragment<AlternativeEpisodesPresenter, AlternativeEpisodesView>
        implements AlternativeEpisodesView {
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    RecyclerView seriesList;
    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;
    @InjectPresenter
    AlternativeEpisodesPresenter presenter;
    @Inject
    AlternativeEpisodeResourceProvider resourceProvider;
    private EpisodeAdapter episodeAdapter;
    private MaterialDialog translationTypeDialog;

    public static AlternativeEpisodesFragment newInstance(long animeId) {
        Bundle args = new Bundle();
        AlternativeEpisodesFragment fragment = new AlternativeEpisodesFragment();
        args.putLong(AppExtras.ARGUMENT_ANIME_ID, animeId);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    AlternativeEpisodesPresenter provideAnimePresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }
        if (getArguments() != null) {
            presenter.setAnimeId(getArguments().getLong(AppExtras.ARGUMENT_ANIME_ID));
        }
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        episodeAdapter = new EpisodeAdapter(resourceProvider,
                getPresenter()::onEpisodeClicked,
                getPresenter()::onEpisodeOptionAction);

        refreshLayout.setOnRefreshListener(() -> getPresenter().onEpisodesRefresh());
        seriesList.setLayoutManager(new LinearStickyHead<EpisodeAdapter>(getContext()));
        int margin = (int) getResources().getDimension(R.dimen.margin_small);
        seriesList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
        seriesList.setAdapter(episodeAdapter);

        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());

        networkErrorView.setText(R.string.common_error_message_without_pull);
        networkErrorView.setVisibility(View.GONE);

        getPresenter().onEpisodesRefresh();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected AlternativeEpisodesPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_series;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showList(List<BaseItem> episodes) {
        episodeAdapter.bindItems(episodes);
    }

    @SuppressLint("CheckResult")
    @Override
    public void showTranslationDialog(List<AlternativeTranslationType> types) {

        if (translationTypeDialog == null || !translationTypeDialog.isShowing()) {
//            translationTypeDialog = new MaterialDialog.Builder(getContext())
//                    .dividerColor(R.attr.colorAccent)
//                    .items(R.array.translations_type_alternative)
//                    .itemsColorAttr(R.attr.colorText)
//                    .backgroundColorAttr(R.attr.colorBackgroundWindow)
//                    .buttonRippleColorAttr(R.attr.colorAccentTransparent)
//                    .autoDismiss(true)
//                    .canceledOnTouchOutside(true)
//                    .itemsCallback((dialog, itemView, position, text) -> getPresenter().onTranslationChoosed(types.get(position)))
//                    .build();
//
//            translationTypeDialog.show();
            //TODO kotlin
            translationTypeDialog = new MaterialDialog(new ContextThemeWrapper(getContext(), R.style.DialogStyle));
            DialogListExtKt.listItems(translationTypeDialog, R.array.translations_type_alternative, null, new int[]{}, false, (materialDialog, integer, s) -> {
                getPresenter().onTranslationChoosed(types.get(integer));
                return null;
            });
            translationTypeDialog.show();
        }
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
    public void onShowLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        refreshLayout.setRefreshing(false);
    }
}
