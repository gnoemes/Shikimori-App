package com.gnoemes.shikimoriapp.presentation.view.manga.adapter

import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.DetailsActionAdapterDelegate
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.DetailsCharacterAdapter
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.DetailsCharacterAdapterDelegate
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.DetailsContentAdapterDelegate
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BaseListAdapter

class MangaAdapter(
        resourceProvider: RateResourceProvider,
        characterAdapter: DetailsCharacterAdapter,
        callback: ((DetailsAction, Any?) -> Unit)
) : BaseListAdapter(null) {

    init {
        with(delegatesManager) {
            addDelegate(MangaHeadAdapter(resourceProvider, callback))
            addDelegate(DetailsContentAdapterDelegate())
            addDelegate(DetailsActionAdapterDelegate(callback))
            addDelegate(DetailsCharacterAdapterDelegate(characterAdapter))
        }
    }
}