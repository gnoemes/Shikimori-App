package com.gnoemes.shikimoriapp.data.repository.app.impl;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.gnoemes.shikimoriapp.data.repository.app.TokenSource;
import com.gnoemes.shikimoriapp.di.app.qualifiers.SettingsQualifier;
import com.gnoemes.shikimoriapp.entity.app.domain.Token;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;

import io.reactivex.Completable;

public class TokenSourceImpl implements TokenSource {

    private SharedPreferences preferences;
    private Gson gson;

    @Inject
    public TokenSourceImpl(@SettingsQualifier SharedPreferences preferences,
                           Gson gson) {
        this.preferences = preferences;
        this.gson = gson;
    }

    @Override
    public Completable saveToken(Token token) {
        return Completable.fromAction(() -> {
            String json = gson.toJson(token).equals("null") ? null : gson.toJson(token);
            getEditor().putString(AppExtras.ARGUMENT_TOKEN, json).commit();
        })
                ;
    }

    @Override
    public Token getToken() {
        try {
            String json = getPrefs().getString(AppExtras.ARGUMENT_TOKEN, "");
            return gson.fromJson(json, Token.class);
        } catch (JsonSyntaxException ex) {
            return null;
        }
    }

    @Override
    public boolean isTokenExists() {
        String token = getPrefs().getString(AppExtras.ARGUMENT_TOKEN, "");

        return getPrefs().contains(AppExtras.ARGUMENT_TOKEN) &&
                !TextUtils.isEmpty(token) &&
                //auto fix if version < 0.6.17;
                !token.equals("null");
    }


    private SharedPreferences getPrefs() {
        return preferences;
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

}
