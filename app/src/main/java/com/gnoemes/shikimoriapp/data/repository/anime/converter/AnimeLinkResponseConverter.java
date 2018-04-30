package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeLinkResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink;

import java.util.List;

import io.reactivex.functions.Function;

public interface AnimeLinkResponseConverter extends Function<List<AnimeLinkResponse>, List<AnimeLink>> {
}
