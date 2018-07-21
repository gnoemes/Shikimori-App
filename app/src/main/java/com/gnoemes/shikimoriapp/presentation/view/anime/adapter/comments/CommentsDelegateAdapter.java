package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.comments;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.comments.presentation.CommentContentViewModel;
import com.gnoemes.shikimoriapp.entity.comments.presentation.CommentViewModel;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsDelegateAdapter extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;
    private DefaultItemCallback callback;

    public CommentsDelegateAdapter(ImageLoader imageLoader, DefaultItemCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof CommentViewModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        CommentViewModel viewModel = (CommentViewModel) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(viewModel);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.image_avatar)
        CircleImageView avatar;

        @BindView(R.id.user_name)
        TextView userName;

        @BindView(R.id.summary_badge)
        TextView summaryBadge;

        @BindView(R.id.text_date)
        TextView date;

        @BindView(R.id.content)
        RecyclerView content;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            Drawable badge = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.language_background)
                    .withColor(R.color.light_teal)
                    .tint()
                    .get();

            layout.setBackground(card);
            summaryBadge.setBackground(badge);

            content.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            content.setItemAnimator(new DefaultItemAnimator());
        }


        public void bind(CommentViewModel viewModel) {
            summaryBadge.setVisibility(View.GONE);
            content.setAdapter(null);


            imageLoader.setImageWithPlaceHolder(avatar, viewModel.getAvatarUrl(), R.attr.colorPrimary);
            avatar.setOnClickListener(v -> callback.onItemClick(viewModel.getUserId()));

            if (viewModel.isSummary()) {
                summaryBadge.setVisibility(View.VISIBLE);
            }

            date.setText(viewModel.getCreatedDate());

            userName.setText(viewModel.getUserName());

            List<String> lines = viewModel.getBody();
            List<CommentContentViewModel> models = new ArrayList<>();

            if (lines.size() == 1) {
                models.add(new CommentContentViewModel(lines.get(0), false));
            } else {
                boolean first = false;
                for (String s : lines) {
                    if (first) {
                        models.add(new CommentContentViewModel(s, true));
                        first = false;
                    } else {
                        models.add(new CommentContentViewModel(s, false));
                        first = true;
                    }
                }
            }

            CommentContentAdapter adapter = new CommentContentAdapter(imageLoader);
            content.setAdapter(adapter);
            adapter.bindItems(models);
        }
    }
}
