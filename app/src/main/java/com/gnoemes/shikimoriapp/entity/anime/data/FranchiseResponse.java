package com.gnoemes.shikimoriapp.entity.anime.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FranchiseResponse {

    @SerializedName("nodes")
    private List<FranchiseNodeResponse> nodes;

    public List<FranchiseNodeResponse> getNodes() {
        return nodes;
    }

}
