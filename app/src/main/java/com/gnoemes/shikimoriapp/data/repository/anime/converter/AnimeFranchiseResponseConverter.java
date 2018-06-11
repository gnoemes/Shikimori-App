package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeFranchiseResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeFranchiseNode;

import java.util.List;

import io.reactivex.functions.Function;

public interface AnimeFranchiseResponseConverter extends Function<AnimeFranchiseResponse, List<AnimeFranchiseNode>> {
}
