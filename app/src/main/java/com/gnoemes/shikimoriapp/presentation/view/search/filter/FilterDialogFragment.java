package com.gnoemes.shikimoriapp.presentation.view.search.filter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants;
import com.gnoemes.shikimoriapp.utils.date.DateTimeUtils;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;


//TODO refactor this monster sometime
public class FilterDialogFragment extends AAH_FabulousFragment {

    private static final String UNSELECTED = "unselected";
    private static final String SELECTED = "selected";

    List<TextView> textViews = new ArrayList<>();

    HashMap<String, List<FilterItem>> appliedFilters = new HashMap<>();

    @BindView(R.id.tab_layout)
    TabLayout tabTypes;
    @BindView(R.id.rl_content)
    RelativeLayout relativeLayout;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    @Inject
    FilterResourceProvider resourceProvider;
    @Inject
    DateTimeUtils dateTimeUtils;

    public static FilterDialogFragment newInstance() {
        return new FilterDialogFragment();
    }

    @OnClick(R.id.btn_clear)
    public void onRefreshClick() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.colorDivider, typedValue, true);

        Drawable unselected = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.chip_unselected)
                .withAttributeColor(R.attr.colorDivider)
                .stroke(2)
                .get();

        for (TextView tv : textViews) {
            tv.setTag(UNSELECTED);
            tv.setBackground(unselected);
            tv.setTextColor(typedValue.data);
        }
        appliedFilters.clear();
    }

    @OnClick(R.id.btn_accept)
    public void onApplyClick() {
        closeFilter(appliedFilters);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getParentFragment() != null) {
            appliedFilters = ((FilterProvider) getParentFragment()).getAppliedFilters();
            if (appliedFilters == null) {
                appliedFilters = new HashMap<>();
            }
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_filter, null);
        ButterKnife.bind(this, contentView);

        FilterSectionPagerAdapter pagerAdapter = new FilterSectionPagerAdapter();
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
        tabTypes.setupWithViewPager(viewPager);

        setAnimationDuration(230);
        setPeekHeight(300);
        setCallbacks((Callbacks) getParentFragment());
        setViewgroupStatic(linearLayout);
        setViewPager(viewPager);
        setViewMain(relativeLayout);
        setMainContentView(contentView);
        super.setupDialog(dialog, style);
    }

    private void inflateLayoutWithFilters(String category, RecyclerView recyclerView) {
        final FiltersListChipsAdapter listAdapter = getAdapterFromPage(category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    private FiltersListChipsAdapter getAdapterFromPage(String category) {
        List<FilterItem> keywords;
        switch (category) {
            case SearchConstants.GENRE:
                keywords = resourceProvider.getGenres();
                return new FiltersListChipsAdapter(SearchConstants.GENRE, Collections.singletonList(Pair.create(null, keywords)));
            case SearchConstants.TYPE:
                keywords = resourceProvider.getTypes();
                return new FiltersListChipsAdapter(SearchConstants.TYPE, Collections.singletonList(Pair.create(null, keywords)));
            case SearchConstants.STATUS:
                keywords = resourceProvider.getStatuses();
                return new FiltersListChipsAdapter(SearchConstants.STATUS, Collections.singletonList(Pair.create(null, keywords)));
            case SearchConstants.ADVANCED:
                return new FiltersListChipsAdapter(SearchConstants.ADVANCED, getAdvancedPage());
            default:
                return null;
        }
    }

    public List<Pair<String, List<FilterItem>>> getAdvancedPage() {
        List<Pair<String, List<FilterItem>>> filters = new ArrayList<>();

        filters.add(Pair.create(resourceProvider.getSeasonString(), resourceProvider.getSeasons()));
        filters.add(Pair.create(resourceProvider.getYearString(), resourceProvider.getYears()));
        filters.add(Pair.create(resourceProvider.getOrderByString(), resourceProvider.getOrder()));
        filters.add(Pair.create(resourceProvider.getDurationString(), resourceProvider.getDurations()));

        return filters;
    }

    private void addToSelected(String key, FilterItem value) {
        if (appliedFilters.get(key) != null && !appliedFilters.get(key).contains(value)) {
            appliedFilters.get(key).add(value);
        } else {
            List<FilterItem> temp = new ArrayList<>();
            temp.add(value);
            appliedFilters.put(key, temp);
        }
    }

    private void removeFromSelected(String page, FilterItem value) {
        if (appliedFilters.get(page).size() == 1) {
            appliedFilters.remove(page);
        } else {
            appliedFilters.get(page).remove(value);
        }
    }

    public interface FilterProvider {
        HashMap<String, List<FilterItem>> getAppliedFilters();
    }

    private class FilterSectionPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_filters_sorters, container, false);
            RecyclerView recyclerView = layout.findViewById(R.id.list_filter);
            switch (position) {
                case 0:
                    inflateLayoutWithFilters(SearchConstants.GENRE, recyclerView);
                    break;
                case 1:
                    inflateLayoutWithFilters(SearchConstants.TYPE, recyclerView);
                    break;
                case 2:
                    inflateLayoutWithFilters(SearchConstants.STATUS, recyclerView);
                    break;
                case 3:
                    inflateLayoutWithFilters(SearchConstants.ADVANCED, recyclerView);
                    break;
            }

            container.addView(layout);
            return layout;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return resourceProvider.getGenreString();
                case 1:
                    return resourceProvider.getTypeString();
                case 2:
                    return resourceProvider.getStatusString();
                case 3:
                    return resourceProvider.getAdvanceSearchString();
            }
            return "";
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public class FiltersListChipsAdapter extends RecyclerView.Adapter<FiltersListChipsAdapter.ViewHolder> {

        private String page;
        private List<Pair<String, List<FilterItem>>> filters;

        public FiltersListChipsAdapter(String page,
                                       List<Pair<String, List<FilterItem>>> filters) {
            this.page = page;
            this.filters = filters;
        }

        @NonNull
        @Override
        public FiltersListChipsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_filter_container, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FiltersListChipsAdapter.ViewHolder holder, int position) {
            holder.bind(filters.get(position).first, filters.get(position).second);
        }

        @Override
        public int getItemCount() {
            return filters.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.text_category)
            TextView tvCategory;
            @BindView(R.id.fbl)
            FlexboxLayout flexboxLayout;
            @BindView(R.id.divider)
            View divider;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            private void bind(String category, List<FilterItem> values) {

                if (category == null) {
                    tvCategory.setVisibility(View.GONE);
                    divider.setVisibility(View.GONE);
                } else {
                    tvCategory.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.VISIBLE);
                    tvCategory.setText(category);
                }


                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = getContext().getTheme();
                theme.resolveAttribute(R.attr.colorDivider, typedValue, true);
                int accent = typedValue.data;
                theme.resolveAttribute(R.attr.colorText, typedValue, true);
                int textColor = typedValue.data;

                Drawable uncelected = DrawableHelper.withContext(getContext())
                        .withDrawable(R.drawable.chip_unselected)
                        .withAttributeColor(R.attr.colorDivider)
                        .stroke(2)
                        .get();
                uncelected.mutate();

                Drawable selected = DrawableHelper.withContext(getContext())
                        .withDrawable(R.drawable.chip_selected)
                        .withAttributeColor(R.attr.colorPrimary)
                        .tint()
                        .get();

                selected.mutate();

                for (int i = 0; i < values.size(); i++) {
                    View subChild = getLayoutInflater().inflate(R.layout.item_filter_chip, null);
                    final TextView tv = subChild.findViewById(R.id.txt_title);
                    tv.setText(values.get(i).getLocalizedText());
                    final int finalIndex = i;

                    tv.setOnClickListener(v -> {
                        if (tv.getTag() != null && tv.getTag().equals(SELECTED)) {
                            tv.setTag(UNSELECTED);
                            tv.setBackground(uncelected);
                            tv.setTextColor(accent);
                            removeFromSelected(page, values.get(finalIndex));
                        } else {
                            tv.setTag(SELECTED);
                            tv.setBackground(selected);
                            tv.setTextColor(textColor);
                            addToSelected(page, values.get(finalIndex));
                        }

                        tv.invalidate();
                    });

                    if (appliedFilters != null
                            && appliedFilters.get(page) != null
                            && appliedFilters.get(page).contains(values.get(i))) {
                        tv.setTag(SELECTED);
                        tv.setBackground(selected);
                        tv.setTextColor(textColor);
                    } else {
                        tv.setBackground(uncelected);
                        tv.setTextColor(accent);
                    }
                    textViews.add(tv);

                    flexboxLayout.addView(subChild);
                    subChild.invalidate();
                }

            }
        }
    }
}
