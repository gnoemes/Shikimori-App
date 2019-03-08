package com.gnoemes.shikimoriapp.presentation.view.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode;

import java.util.List;

public interface FranchiseNodeToStringConverter {

    List<String> convertList(List<FranchiseNode> nodes);
}
