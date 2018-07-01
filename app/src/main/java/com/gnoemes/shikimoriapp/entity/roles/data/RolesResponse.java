package com.gnoemes.shikimoriapp.entity.roles.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RolesResponse {

    @SerializedName("roles")
    private List<String> roles;
    @SerializedName("roles_russian")
    private List<String> rolesRussian;
    @Nullable
    @SerializedName("character")
    private CharacterResponse characterResponse;
    @Nullable
    @SerializedName("person")
    private PersonResponse personResponse;

    public List<String> getRoles() {
        return roles;
    }

    public List<String> getRolesRussian() {
        return rolesRussian;
    }

    @Nullable
    public CharacterResponse getCharacterResponse() {
        return characterResponse;
    }

    @Nullable
    public PersonResponse getPersonResponse() {
        return personResponse;
    }
}
