package com.gnoemes.shikimoriapp.presentation.view.search.adapter

import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader

class SearchAdapter(
        settings: UserSettingsSource,
        resourceProvider: SearchAnimeResourceProvider,
        imageLoader: ImageLoader,
        callback: ((Type, Long) -> Unit)
) : BaseListAdapter(null) {

    init {
        delegatesManager.addDelegate(SearchAnimeAdapterDelegate(settings, resourceProvider, imageLoader, callback))
    }
}