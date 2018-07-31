package com.gnoemes.shikimoriapp.data.repository.club;

import com.gnoemes.shikimoriapp.entity.club.data.ClubResponse;
import com.gnoemes.shikimoriapp.entity.club.domain.Club;

import java.util.List;

import io.reactivex.functions.Function;

public interface ClubResponseConverter extends Function<List<ClubResponse>, List<Club>> {
    Club convertResponse(ClubResponse response);
}
