package com.gnoemes.shikimoriapp.entity.anime.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeFranchiseResponse {

    @SerializedName("nodes")
    private List<AnimeFranchiseNodeResponse> nodes;

    public List<AnimeFranchiseNodeResponse> getNodes() {
        return nodes;
    }

}
