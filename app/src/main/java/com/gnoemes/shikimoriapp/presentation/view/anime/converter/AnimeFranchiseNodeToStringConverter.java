package com.gnoemes.shikimoriapp.presentation.view.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeFranchiseNode;

import java.util.List;

public interface AnimeFranchiseNodeToStringConverter {

    List<String> convertList(List<AnimeFranchiseNode> nodes);
}
