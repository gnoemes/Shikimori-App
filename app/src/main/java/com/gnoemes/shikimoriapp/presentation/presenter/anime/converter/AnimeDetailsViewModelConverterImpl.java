package com.gnoemes.shikimoriapp.presentation.presenter.anime.converter;

import android.content.Context;
import android.text.TextUtils;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeStatus;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeAction;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeActionItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeCharactersItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeContentItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeHeadItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeOtherItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.DoubleDividerItem;
import com.gnoemes.shikimoriapp.entity.video.domain.Video;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeDetailsViewModelConverterImpl implements AnimeDetailsViewModelConverter {

    private static final String REGEX = "(\\[+?.+?])";
    private Context context;
    private DateTimeConverter converter;

    @Inject
    public AnimeDetailsViewModelConverterImpl(Context context, DateTimeConverter converter) {
        this.context = context;
        this.converter = converter;
    }

    @Override
    public AnimeDetailsViewModel apply(AnimeDetails animeDetails) {


        return new AnimeDetailsViewModel(animeDetails.getId(),
                convertName(animeDetails.getRussianName(), animeDetails.getName()),
                convertName(animeDetails.getName(), animeDetails.getJapaneseNames()),
                animeDetails.getUrl(),
                animeDetails.getAnimeImage().getImageOriginalUrl(),
                convertType(animeDetails.getType(), animeDetails.getEpisodes(), animeDetails.getDuration()),
                convertStatus(animeDetails.getStatus()),
                animeDetails.getAnimeGenres(),
                animeDetails.getEpisodes(),
                animeDetails.getEpisodesAired(),
                convertSeason(animeDetails.getAiredDate()),
                animeDetails.getDuration(),
                animeDetails.getScore(),
                convertDescription(animeDetails.getDescription()),
                animeDetails.getAnimeRate(),
                animeDetails.getVideos(),
                animeDetails.getCharacters()
        );
    }

    private String convertDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            return context.getString(R.string.error_no_data);
        }
        return description.replaceAll(REGEX, "");
    }

    private String convertSeason(DateTime airedDate) {
        return converter.convertAnimeSeasonToString(airedDate);
    }

    private String convertStatus(AnimeStatus status) {
        if (status == null) {
            return context.getString(R.string.error_no_data);
        }
        switch (status) {
            case ANONS:
                return context.getString(R.string.status_anons);
            case ONGOING:
                return context.getString(R.string.status_ongoing);
            case RELEASED:
                return context.getString(R.string.status_released);
            default:
                return context.getString(R.string.error_no_data);
        }
    }

    private String convertType(AnimeType type, int episodes, int duration) {
        return String.format(context.getString(R.string.type_pattern), type.toString().toUpperCase(),
                episodes == 0 ? "xxx" : String.valueOf(episodes), duration);
    }

    private String convertName(String russianName, String name) {
        return russianName == null ? name.trim() : russianName.trim();
    }

    private String convertName(String first, List<String> second) {
        return first != null ? first.trim() : second.get(0).trim();
    }

    @Override
    public List<BaseItem> convertFromViewModel(AnimeDetailsViewModel viewModel) {
        List<BaseItem> animeItems = new ArrayList<>();
        animeItems.add(new AnimeHeadItem(viewModel.getId(),
                viewModel.getName(),
                viewModel.getJpOrEngName(),
                viewModel.getUrl(),
                viewModel.getImageUrl(),
                viewModel.getAnimeType(),
                viewModel.getAnimeStatus(),
                viewModel.getSeason(),
                viewModel.getGenres(),
                viewModel.getScore(),
                viewModel.getAnimeRate()));

        if (viewModel.getCharacters() != null && !viewModel.getCharacters().isEmpty()) {
            animeItems.add(new DoubleDividerItem());
            animeItems.add(new AnimeCharactersItem(viewModel.getCharacters()));
        }

        animeItems.add(new DoubleDividerItem());
        animeItems.add(new AnimeActionItem(AnimeAction.CHRONOLOGY));
        animeItems.add(new AnimeActionItem(AnimeAction.LINKS));
        animeItems.add(new DoubleDividerItem());

        if (viewModel.getVideos() != null && !viewModel.getVideos().isEmpty()) {
            for (Video video : viewModel.getVideos()) {
                animeItems.add(new AnimeOtherItem(video.getId(), video.getUrl(), video.getName(), video.getType(), video.getPlayer()));
            }
            animeItems.add(new DoubleDividerItem());
        }

        animeItems.add(new AnimeContentItem(viewModel.getId(), viewModel.getDescription()));
        animeItems.add(new DoubleDividerItem());
        animeItems.add(new AnimeActionItem(AnimeAction.COMMENTS));
        animeItems.add(new DoubleDividerItem());
        return animeItems;
    }
}
