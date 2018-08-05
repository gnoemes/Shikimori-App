package com.gnoemes.shikimoriapp.presentation.view.person.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonRelatedItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonRelatedAdapter extends RecyclerView.Adapter<PersonRelatedAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private PersonItemCallback callback;

    private List<PersonRelatedItem> items = new ArrayList<>();

    public PersonRelatedAdapter(ImageLoader imageLoader,
                                PersonItemCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    public void bindItems(List<PersonRelatedItem> charactersItems) {
        items.clear();
        items.addAll(charactersItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person_work, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonRelatedItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.work_image)
        ImageView imageView;
        @BindView(R.id.work_name)
        TextView nameView;

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

        public void bind(PersonRelatedItem item) {
            layout.setOnClickListener(null);
            nameView.setText(null);
            nameView.setText(item.getText());
            imageLoader.setImageWithFit(imageView, item.getImage().getImageOriginalUrl());
            layout.setOnClickListener(v -> callback.onRelatedItemClicked(item.getType(), item.getId()));
        }

    }

}
