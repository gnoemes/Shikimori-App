package com.gnoemes.shikimoriapp.presentation.view.person.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonHeadItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonHeadAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;

    public PersonHeadAdapterDelegate(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof PersonHeadItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person_head, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        PersonHeadItem item = (PersonHeadItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.person_image)
        ImageView imageView;
        @BindView(R.id.text_name_eng)
        TextView engNameView;
        @BindView(R.id.text_name_jp)
        TextView jpNameView;
        @BindView(R.id.text_birthday)
        TextView birthdayView;
        @BindView(R.id.text_roles)
        TextView rolesView;
        @BindView(R.id.title_eng)
        TextView titleEngView;
        @BindView(R.id.title_jp)
        TextView titleJpView;
        @BindView(R.id.title_birthday)
        TextView titleBirthdayView;
        @BindView(R.id.divider1)
        View descriptionDividerView;
        @BindView(R.id.title_roles)
        TextView titleRolesView;
        @BindView(R.id.title_job)
        TextView jobTitleView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);
        }

        public void bind(PersonHeadItem item) {

            imageLoader.setImageWithFit(imageView, item.getAnimeImage().getImageOriginalUrl());

            if (TextUtils.isEmpty(item.getName())) {
                engNameView.setVisibility(View.GONE);
                titleEngView.setVisibility(View.GONE);
            } else {
                engNameView.setVisibility(View.VISIBLE);
                titleEngView.setVisibility(View.VISIBLE);
                engNameView.setText(item.getName());
            }

            if (TextUtils.isEmpty(item.getJapaneeseName())) {
                jpNameView.setVisibility(View.GONE);
                titleJpView.setVisibility(View.GONE);
            } else {
                jpNameView.setVisibility(View.VISIBLE);
                titleJpView.setVisibility(View.VISIBLE);
                jpNameView.setText(item.getJapaneeseName());
            }

            if (TextUtils.isEmpty(item.getBirthDay())) {
                birthdayView.setVisibility(View.GONE);
                titleBirthdayView.setVisibility(View.GONE);
            } else {
                birthdayView.setVisibility(View.VISIBLE);
                titleBirthdayView.setVisibility(View.VISIBLE);
                birthdayView.setText(item.getBirthDay());
            }

            if (TextUtils.isEmpty(item.getRoles())) {
                rolesView.setVisibility(View.GONE);
                descriptionDividerView.setVisibility(View.GONE);
                titleRolesView.setVisibility(View.GONE);
            } else {
                rolesView.setText(item.getRoles());
            }

            if (TextUtils.isEmpty(item.getJobTitle())) {
                jobTitleView.setText(R.string.error_no_data);
            } else {
                jobTitleView.setText(item.getJobTitle());
            }

        }
    }
}
