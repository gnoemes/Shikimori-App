package com.gnoemes.shikimoriapp.data.local.preferences;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationDubberSettings;
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus;

public interface UserSettingsConverter {

    UserStatus convertStatus(String status);

    TranslationType convertTranslationType(String type);

    TranslationDubberSettings convertTranslationDubberSettings(String type);

    PlayerType convertPlayerType(String type);
}
