package com.gnoemes.shikimoriapp.presentation.view.profile.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ImageContent;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ImageContentAdapter extends RecyclerView.Adapter<ImageContentAdapter.ViewHolder> {

    @LayoutRes
    private int layoutId;
    @StringRes
    private int placeholderText;
    private List<ImageContent> items;
    private ImageLoader imageLoader;
    private DefaultItemCallback callback;

    public ImageContentAdapter(int layoutId,
                               int placeHolderText,
                               ImageLoader imageLoader,
                               DefaultItemCallback callback) {
        this.layoutId = layoutId;
        this.placeholderText = placeHolderText;
        this.imageLoader = imageLoader;
        this.callback = callback;
        items = new ArrayList<>();
    }

    public void bindItems(List<ImageContent> list) {
        items.clear();
        if (list != null) {
            if (!list.isEmpty()) {
                items.addAll(list);
                notifyDataSetChanged();
            } else {
                items.add(new ImageContent(-1, null));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageContent content = items.get(position);
        holder.bind(content);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        CircleImageView imageView;

        @BindView(R.id.placeholder)
        TextView placeholder;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ImageContent content) {
            if (!TextUtils.isEmpty(content.getUrl())) {
                imageLoader.setImageWithPlaceHolder(imageView, content.getUrl(), 0);
                placeholder.setVisibility(View.GONE);
                imageView.setOnClickListener(v -> callback.onItemClick(content.getId()));
            } else {
                placeholder.setVisibility(View.VISIBLE);
                placeholder.setText(placeholderText);
            }
        }
    }
}
