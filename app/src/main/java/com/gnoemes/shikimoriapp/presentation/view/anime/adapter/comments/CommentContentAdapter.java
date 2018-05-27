package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.comments;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.comments.presentation.CommentContentViewModel;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentContentAdapter extends RecyclerView.Adapter<CommentContentAdapter.ViewHolder> {

    private List<CommentContentViewModel> items = new ArrayList<>();
    private ImageLoader imageLoader;

    public CommentContentAdapter(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_comment_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void bindItems(List<CommentContentViewModel> viewModels) {
        items.clear();
        items.addAll(viewModels);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content)
        SpannableTextView content;

        @BindView(R.id.indicator)
        TextView indicator;

        @BindView(R.id.constraint)
        ConstraintLayout parent;


        private int textColor;
        private int quote;
        private int padding;

//        private final Pattern URL_REGEX = Pattern.compile("(https|http)(.*?)\\s");

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            textColor = AttributesHelper
                    .withContext(itemView.getContext())
                    .getColor(R.attr.colorText);

            quote = itemView.getContext().getResources().getColor(R.color.quote_color);

            padding = (int) itemView.getResources().getDimension(R.dimen.margin_normal);

        }

        private Slice getTextSlice(String text) {
            return new Slice.Builder(text)
                    .textColor(textColor)
                    .build();
        }

        private Slice getQuoteSlice(String text) {
            return new Slice.Builder(text)
                    .textColor(textColor)
                    .build();
        }

        public void bind(CommentContentViewModel model) {
            indicator.setVisibility(View.GONE);
            parent.setBackgroundColor(0);
            content.reset();

            if (model.isQuote()) {
                content.addSlice(getQuoteSlice(model.getText()));
                content.setPadding(padding, padding, padding, padding);
                parent.setBackgroundColor(quote);
                indicator.setVisibility(View.VISIBLE);
            } else {
                content.setPadding(0, padding, padding, padding);
                content.addSlice(getTextSlice(model.getText()));
            }

            content.display();
        }
    }
}
