package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mpt.android.stv.SpannableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsCharacterAdapter extends RecyclerView.Adapter<DetailsCharacterAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private AnimeItemCallback callback;

    private List<Character> items = new ArrayList<>();

    public DetailsCharacterAdapter(ImageLoader imageLoader,
                                   AnimeItemCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    public void bindItems(List<Character> charactersItems) {
        items.clear();
        items.addAll(charactersItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content_medium, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        ConstraintLayout layout;
        @BindView(R.id.imageView)
        RoundedImageView characterImage;
        @BindView(R.id.typeView)
        TextView typeView;
        @BindView(R.id.nameView)
        SpannableTextView characterName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Character item) {
            layout.setOnClickListener(null);
            characterName.setText(null);

            imageLoader.setImageWithFit(characterImage, item.getImage().getOriginal());
            typeView.setVisibility(View.GONE);


            String name = TextUtils.isEmpty(item.getRussianName()) ? item.getName() : item.getRussianName();
            characterName.setText(name);

            layout.setOnClickListener(v -> callback.onAction(DetailsAction.CHARACTER, item.getId()));
        }
    }

}
