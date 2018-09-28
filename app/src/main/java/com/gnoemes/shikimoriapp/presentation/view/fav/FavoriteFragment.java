package com.gnoemes.shikimoriapp.presentation.view.fav;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.list.DialogListExtKt;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.data.AppConfig;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.FavoritePresenter;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentViewWithButton;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.fav.adapter.AnimeRateAdapter;
import com.gnoemes.shikimoriapp.presentation.view.fav.provider.UserRatesAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.santalu.respinner.ReSpinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FavoriteFragment extends BaseFragment<FavoritePresenter, FavoriteView>
        implements FavoriteView, DefaultItemCallback {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @BindView(R.id.needAuthView)
    EmptyContentViewWithButton needAuthView;

    @InjectPresenter
    FavoritePresenter presenter;

    private ReSpinner spinner;

    public static FavoriteFragment newInstance(Long id) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        if (id != null) {
            args.putLong(AppExtras.ARGUMENT_USER_ID, id);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    ImageLoader imageLoader;

    @Inject
    UserRatesAnimeResourceProvider resourceProvider;

    @Inject
    RateResourceProvider rateResourceProvider;

    private AnimeRateAdapter adapter;

    @ProvidePresenter
    FavoritePresenter providePresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        if (getArguments() != null && getArguments().containsKey(AppExtras.ARGUMENT_USER_ID)) {
            presenter.setUserId(getArguments().getLong(AppExtras.ARGUMENT_USER_ID));
        }
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initList();
    }

    @Override
    public void initToolbar() {
        toolbar.setTitle(null);
        spinner = new ReSpinner(getContext());
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner_normal, resourceProvider.getRateStasuses()));
        spinner.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    getPresenter().onStatusChanged(RateStatus.WATCHING);
                    break;
                case 1:
                    getPresenter().onStatusChanged(RateStatus.PLANNED);
                    break;
                case 2:
                    getPresenter().onStatusChanged(RateStatus.REWATCHING);
                    break;
                case 3:
                    getPresenter().onStatusChanged(RateStatus.COMPLETED);
                    break;
                case 4:
                    getPresenter().onStatusChanged(RateStatus.ON_HOLD);
                    break;
                case 5:
                    getPresenter().onStatusChanged(RateStatus.DROPPED);
                    break;
            }
        });

        Drawable rateBackground = DrawableHelper
                .withContext(getContext())
                .withDrawable(spinner.getBackground())
                .withAttributeColor(R.attr.colorAccent)
                .tint()
                .get();
        spinner.setBackground(rateBackground);

        toolbar.addView(spinner);

        toolbar.inflateMenu(R.menu.menu_favorite);

        MenuItem shuffleItem = toolbar.getMenu().getItem(0);
        Drawable shuffleIcon = DrawableHelper
                .withContext(getContext())
                .withDrawable(shuffleItem.getIcon())
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        shuffleItem.setIcon(shuffleIcon);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_shuffle_play:
                    getPresenter().onRandomOpen();
                    //Prevent double click
                    shuffleItem.setEnabled(false);
                    toolbar.postDelayed(() -> shuffleItem.setEnabled(true), 300);
                    break;
            }
            return true;
        });
    }

    private void initList() {
        adapter = new AnimeRateAdapter(imageLoader,
                resourceProvider,
                getPresenter()::onItemClicked,
                getPresenter()::onItemChangeStatus);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        list.setLayoutManager(manager);
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemPosition = manager.findLastCompletelyVisibleItemPosition() + AppConfig.BIG_LIMIT / 2;
                int itemCount = manager.getItemCount() - 1;

                if (visibleItemPosition >= itemCount) {
                    getPresenter().loadNextPage();
                }
            }
        });

        networkErrorView.setVisibility(View.GONE);

        needAuthView.setCallback(view -> getPresenter().onAuthClicked());
        needAuthView.setVisibility(View.GONE);
        needAuthView.setText(R.string.favorite_need_auth);
        needAuthView.setButtonText(R.string.common_enter);

        getPresenter().onRefresh();
    }

    @Override
    public void onItemClick(long id) {
        getPresenter().onItemClicked(id);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected FavoritePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_favorite;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showList(List<BaseItem> items) {
        adapter.bindItems(items);
    }

    @Override
    public void insertMore(List<BaseItem> items) {
        adapter.insertMore(items);
    }

    @Override
    public void clearList() {
        adapter.clearList();
    }

    @Override
    public void onShowPageLoading() {
        adapter.showPageLoading();
    }

    @Override
    public void onHidePageLoading() {
        adapter.hidePageLoading();
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
    public void showNetworkErrorView() {
        networkErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkErrorView() {
        networkErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showNeedAuthView() {
        needAuthView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNeedAuthView() {
        needAuthView.setVisibility(View.GONE);
    }


    @Override
    public void addBackArrow() {
        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
    }

    @Override
    public void setSpinnerPosition(RateStatus status) {
        if (spinner != null) {
            int position = 0;
            switch (status) {
                case WATCHING:
                    position = 0;
                    break;
                case PLANNED:
                    position = 1;
                    break;
                case REWATCHING:
                    position = 2;
                    break;
                case COMPLETED:
                    position = 3;
                    break;
                case ON_HOLD:
                    position = 4;
                    break;
                case DROPPED:
                    position = 5;
                    break;
            }
            spinner.setSelection(position, false);
        }
    }

    @Override
    public void updateRateItems(List<String> rates) {
        if (getContext() != null) {
            spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner_normal, rates));
        }
    }

    @Override
    public void showAuthDialog() {
        new MaterialDialog(new ContextThemeWrapper(getContext(), R.style.DialogStyle))
                .message(R.string.auth_dialog_conent, null)
                .positiveButton(R.string.common_sign_in, null, materialDialog -> {
                    getPresenter().onSignIn();
                    return null;
                })
                .negativeButton(R.string.common_cancel, null, null)
                .neutralButton(R.string.common_sign_up, null, materialDialog -> {
                    getPresenter().onSignUp();
                    return null;
                })
                .show();
    }

    @SuppressLint("CheckResult")
    @Override
    public void showChangeRateDialog(long id, List<RateStatus> statuses) {
        List<String> items = new ArrayList<>();

        for (RateStatus status : statuses) {
            items.add(rateResourceProvider.getLocalizedStatus(Type.ANIME, status));
        }
        items.add(resourceProvider.getDeleteString());

        if (getContext() != null) {
            //TODO kotlin
//            new MaterialDialog.Builder(getContext())
//                    .title(R.string.rate_change)
//                    .items(items.toArray(new CharSequence[items.size()]))
//                    .itemsCallback(new MaterialDialog.ListCallback() {
//                        @Override
//                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
//                            if (position == items.size() - 1) {
//                                getPresenter().onItemStatusRemove(id);
//                            } else {
//                                FavoriteFragment.this.getPresenter().onItemStatusChanged(id, statuses.get(position));
//                            }
//                        }
//                    })
//                    .autoDismiss(true)
//                    .titleColorAttr(R.attr.colorText)
//                    .contentColorAttr(R.attr.colorText)
//                    .alwaysCallSingleChoiceCallback()
//                    .backgroundColorAttr(R.attr.colorBackgroundWindow)
//                    .negativeText(R.string.close)
//                    .negativeColorAttr(R.attr.colorAction)
//                    .canceledOnTouchOutside(true)
//                    .build()
//                    .show();
            MaterialDialog dialog = new MaterialDialog(new ContextThemeWrapper(getContext(), R.style.DialogStyle))
                    .title(R.string.rate_change, null);
            DialogListExtKt.listItems(dialog, 0, items, new int[]{}, false, (materialDialog, integer, s) -> {
                if (integer == items.size() - 1) {
                    getPresenter().onItemStatusRemove(integer);
                } else {
                    getPresenter().onItemStatusChanged(id, statuses.get(integer));
                }
                return null;
            });
            dialog.show();
        }
    }
}
