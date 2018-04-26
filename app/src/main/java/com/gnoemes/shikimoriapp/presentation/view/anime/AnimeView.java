package com.gnoemes.shikimoriapp.presentation.view.anime;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragmentView;

import java.util.List;

public interface AnimeView extends BaseFragmentView {
    void setName(String name);

    void setImage(String imageUrl);

    void setSecondName(String jpOrEngName);

    void setSeason(String season);

    void setType(String animeType);

    void setStatus(String animeStatus);

    void setGenres(List<AnimeGenre> genres);

    void setScore(float score);

    void setDescription(String description);
}
