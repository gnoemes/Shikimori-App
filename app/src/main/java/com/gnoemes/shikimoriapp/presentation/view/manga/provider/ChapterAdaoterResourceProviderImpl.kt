package com.gnoemes.shikimoriapp.presentation.view.manga.provider

import android.content.Context
import com.gnoemes.shikimoriapp.R
import javax.inject.Inject

class ChapterAdaoterResourceProviderImpl @Inject constructor(
        private val context: Context
) : ChapterAdapterResourceProvider {

    override fun getPlaceholderMessage(): String = context.getString(R.string.chapters_in_dev)
}