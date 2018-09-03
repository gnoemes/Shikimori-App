package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.FranchiseResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode;

import java.util.List;

import io.reactivex.functions.Function;

public interface FranchiseResponseConverter extends Function<FranchiseResponse, List<FranchiseNode>> {
}
