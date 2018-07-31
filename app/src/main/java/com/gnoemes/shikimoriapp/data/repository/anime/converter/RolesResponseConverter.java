package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.RolesResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;

import java.util.List;

public interface RolesResponseConverter {

    List<Character> convertCharacters(List<RolesResponse> responses);

    Character convertResponse(@Nullable CharacterResponse response);
}
