package com.gnoemes.shikimoriapp.presentation.presenter.anime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {

    private List<AnimeGenre> genres = new ArrayList<>();

    public AnimeGenre getItemByPosition(int position) {
        return genres.get(position);
    }

    public void bindItems(List<AnimeGenre> genres) {
        if (this.genres.size() != genres.size()) {
            this.genres = genres;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public GenresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_chip, parent, false);
        return new GenresAdapter.ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresAdapter.ViewHolder holder, int position) {
        AnimeGenre item = genres.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_genre)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(AnimeGenre item) {
            textView.setText(item.getRussianName().toLowerCase());
        }
    }
}
