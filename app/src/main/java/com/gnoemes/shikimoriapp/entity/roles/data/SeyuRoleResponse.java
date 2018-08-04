package com.gnoemes.shikimoriapp.entity.roles.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeyuRoleResponse {

    @SerializedName("characters")
    private List<CharacterResponse> characterResponses;

    public List<CharacterResponse> getCharacterResponses() {
        return characterResponses;
    }
}
