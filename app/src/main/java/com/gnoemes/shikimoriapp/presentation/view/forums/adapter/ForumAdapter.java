package com.gnoemes.shikimoriapp.presentation.view.forums.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {

    private List<Forum> forums = new ArrayList<>();

    private ForumCallback forumCallback;

    public ForumAdapter(ForumCallback forumCallback) {
        this.forumCallback = forumCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forum, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(forums.get(position));
    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    public void bindItems(List<Forum> forumItems) {
        forums.clear();
        forums.addAll(forumItems);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_title)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Drawable chevron = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_chevron_right)
                    .withAttributeColor(R.attr.colorAccent)
                    .tint()
                    .get();

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            textView.setBackground(card);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, chevron, null);
        }

        public void bind(Forum forum) {
            textView.setOnClickListener(null);
            textView.setText(forum.getName());

            textView.setOnClickListener(v -> forumCallback.onForumClicked(forum.getForumType()));
        }
    }
}
