package com.gnoemes.shikimoriapp.presentation.view.translations;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationNavigationData;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoTrack;
import com.gnoemes.shikimoriapp.presentation.presenter.translations.TranslationsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentViewWithButton;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.player.WebPlayerActivity;
import com.gnoemes.shikimoriapp.presentation.view.translations.adapter.TranslationItemCallback;
import com.gnoemes.shikimoriapp.presentation.view.translations.adapter.TranslationsAdapter;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class TranslationsFragment extends BaseFragment<TranslationsPresenter, TranslationsView>
        implements TranslationsView {

    @BindView(R.id.view_empty)
    EmptyContentViewWithButton emptyContentView;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.networkErrorView)
    NetworkErrorView networkErrorView;

    @InjectPresenter
    TranslationsPresenter presenter;
    private TranslationsAdapter adapter;

    public static TranslationsFragment newInstance(TranslationNavigationData data) {
        TranslationsFragment fragment = new TranslationsFragment();
        Bundle args = new Bundle();
        args.putInt(AppExtras.ARGUMENT_EPISODE_ID, data.getEpisodeId());
        args.putSerializable(AppExtras.ARGUMENT_TRANSLATION_TYPE, data.getType());
        args.putLong(AppExtras.ARGUMENT_ANIME_ID, data.getAnimeId());
        args.putLong(AppExtras.ARGUMENT_ANIME_RATE_ID, data.getRateId());
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    TranslationsPresenter providePresenter() {
        presenter = presenterProvider.get();
        if (getArguments() != null) {
            if (getParentFragment() != null) {
                presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
            }
            presenter.setEpisodeId(getArguments().getInt(AppExtras.ARGUMENT_EPISODE_ID));
            presenter.setCurrentTranslationType((TranslationType) getArguments().getSerializable(AppExtras.ARGUMENT_TRANSLATION_TYPE));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TranslationsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void initViews() {

        emptyContentView.setText(R.string.translations_empty);
        emptyContentView.setCallback(v -> getPresenter().onFindAll());
        emptyContentView.setVisibility(View.GONE);
        networkErrorView.setVisibility(View.GONE);

        refreshLayout.setOnRefreshListener(() -> getPresenter().loadTranslations());

        adapter = new TranslationsAdapter(new TranslationItemCallback() {
            @Override
            public void onTranslationClicked(TranslationViewModel translation) {
                getPresenter().onTranslationClicked(translation);
            }

            @Override
            public void onDownloadTranslation(TranslationViewModel translation) {
                getPresenter().onDownloadTranslation(translation);
            }
        });

        int margin = (int) getResources().getDimension(R.dimen.margin_small);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(margin));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        if (getContext() != null && toolbar != null) {
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
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onPermissionDenied() {
        getPresenter().onPermissionDenied();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onNeverAskAgain() {
        getPresenter().onNeverAskAgain();
    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onPermissionGranted() {
        getPresenter().onPermissionGranted();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected TranslationsPresenter getPresenter() {
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

    public void checkPermissions() {
        TranslationsFragmentPermissionsDispatcher.onPermissionGrantedWithPermissionCheck(this);
    }


    @Override
    public void showTranslations(List<TranslationViewModel> translations) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.bindItems(translations);
    }

    @Override
    public void showQualityDialog(List<VideoTrack> tracks) {
        List<String> resolutions = new ArrayList<>();

        for (VideoTrack track : tracks) {
            if (track.getResolution() != Constants.NO_ID) {
                resolutions.add(String.valueOf(track.getResolution()));
            }
        }

        if (getContext() != null) {
//            new MaterialDialog.Builder(getContext())
//                    .title(R.string.quality_title)
//                    .items(resolutions)
//                    .itemsCallback((dialog, itemView, position, text) -> getPresenter().onQualityForExternalChoosed(tracks.get(position).getUrl()))
//                    .autoDismiss(true)
//                    .titleColorAttr(R.attr.colorText)
//                    .contentColorAttr(R.attr.colorText)
//                    .alwaysCallSingleChoiceCallback()
//                    .backgroundColorAttr(R.attr.colorBackgroundWindow)
//                    .canceledOnTouchOutside(true)
//                    .build()
//                    .show();
        }
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
    public void showErrorView() {
        recyclerView.setVisibility(View.GONE);
        networkErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlayerDialog(List<PlayerType> players) {
        if (getContext() != null) {
//            new MaterialDialog.Builder(getContext())
//                    .items(players.size() == 1 ? R.array.players_single : R.array.players)
//                    .itemsCallback((dialog, itemView, position, text) -> getPresenter().onPlay(players.get(position)))
//                    .autoDismiss(true)
//                    .titleColorAttr(R.attr.colorText)
//                    .contentColorAttr(R.attr.colorText)
//                    .alwaysCallSingleChoiceCallback()
//                    .backgroundColorAttr(R.attr.colorBackgroundWindow)
//                    .canceledOnTouchOutside(true)
//                    .build()
//                    .show();
        }
    }

    @Override
    public void hideErrorView() {
        networkErrorView.setVisibility(View.GONE);
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
        if (getContext() != null) {
//            List<TranslationType> types = Arrays.asList(TranslationType.values());
//            new MaterialDialog.Builder(getContext())
//                    .items(R.array.translations_type)
//                    .itemsCallback((dialog, itemView, position, text) -> {
//                        dialog.dismiss();
//                        getPresenter().onTypeClicked(types.get(position));
//                    })
//                    .autoDismiss(true)
//                    .titleColorAttr(R.attr.colorText)
//                    .contentColorAttr(R.attr.colorText)
//                    .alwaysCallSingleChoiceCallback()
//                    .backgroundColorAttr(R.attr.colorBackgroundWindow)
//                    .autoDismiss(false)
//                    .negativeColorAttr(R.attr.colorAction)
//                    .negativeText(R.string.common_cancel)
//                    .onNegative((dialog, which) -> dialog.dismiss())
//                    .canceledOnTouchOutside(true)
//                    .build()
//                    .show();
        }
    }

    @Override
    public void playVideoOnWeb(String url) {
        Intent intent = new Intent(getContext(), WebPlayerActivity.class);
        intent.putExtra(AppExtras.ARGUMENT_URL, url);
        startActivity(intent);
    }
}
