package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.Target;

public interface TargetResponseConverter {

    Target convertFrom(AnimeResponse animeResponse);
}
