package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.roles.data.SeyuResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Seyu;

import java.util.List;

import io.reactivex.functions.Function;

public interface SeyuResponseConverter extends Function<List<SeyuResponse>, List<Seyu>> {
    AnimeImage convertImage(AnimeImageResponse imageResponse);
}
