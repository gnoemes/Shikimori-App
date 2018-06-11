package com.gnoemes.shikimoriapp.presentation.view.main.provider;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType;

public abstract class BaseMangaResourceProvider implements MangaResourceProvider {
    @Override
    public int getColorByMangaType(MangaType type) {
        return 0;
    }

    @Override
    public String getLocalizedType(MangaType type) {
        switch (type) {
            case MANGA:
                return getContext().getString(R.string.type_manga_translatable);
            case NOVEL:
                return getContext().getString(R.string.type_novel_translatable);
            case ONE_SHOT:
                return getContext().getString(R.string.type_one_shot_translatable);
            case DOUJIN:
                return getContext().getString(R.string.type_doujin_translatable);
            case MANHUA:
                return getContext().getString(R.string.type_manhua_translatable);
            case MANHWA:
                return getContext().getString(R.string.type_manhwa_translatable);

        }
        return null;
    }

    protected abstract Context getContext();
}
