package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.LinkResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Link;

import java.util.List;

import io.reactivex.functions.Function;

public interface LinkResponseConverter extends Function<List<LinkResponse>, List<Link>> {
}
