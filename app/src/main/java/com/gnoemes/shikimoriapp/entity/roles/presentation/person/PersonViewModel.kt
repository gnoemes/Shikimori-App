package com.gnoemes.shikimoriapp.entity.roles.presentation.person

import com.gnoemes.shikimoriapp.entity.common.domain.Image
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem

data class PersonViewModel(
        val id: Long,
        val name: String,
        val nameRu: String?,
        val image: Image,
        val url: String
) : BaseSearchItem()