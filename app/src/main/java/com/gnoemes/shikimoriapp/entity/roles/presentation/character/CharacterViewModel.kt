package com.gnoemes.shikimoriapp.entity.roles.presentation.character

import com.gnoemes.shikimoriapp.entity.common.domain.Image
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem

data class CharacterViewModel(
        val id: Long,
        val name: String,
        val russianName: String?,
        val image: Image,
        val url: String
) : BaseSearchItem()