package com.gnoemes.shikimoriapp.presentation.view.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.roles.presentation.character.CharacterViewModel
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.PreferenceAdapterDelegate
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.inflate
import com.gnoemes.shikimoriapp.utils.kotlin.color
import com.gnoemes.shikimoriapp.utils.kotlin.gone
import com.mpt.android.stv.Slice
import kotlinx.android.synthetic.main.item_search.view.*

class SearchCharacterAdapterDelegate(settings: UserSettingsSource,
                                     private val imageLoader: ImageLoader,
                                     private val callback: (Type, Long) -> Unit
) : PreferenceAdapterDelegate<List<BaseItem>>(settings) {

    override fun isForViewType(items: List<BaseItem>, position: Int): Boolean =
            items[position] is CharacterViewModel

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_search, false))

    override fun onBindViewHolder(items: List<BaseItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as ViewHolder).bind(items[position] as CharacterViewModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CharacterViewModel) {
            with(itemView) {
                imageLoader.setImageWithFit(animeImageView, item.image.original)

                typeView.gone()

                nameView.reset()
                if (isRomadziNaming()) {
                    nameView.addSlice(getSliceWithName(item.name))
                } else {
                    nameView.addSlice(getSliceWithName(item.russianName ?: item.name))
                }
                nameView.display()

                container.setOnClickListener { callback.invoke(Type.CHARACTER, item.id) }
            }
        }

        private fun getSliceWithName(name: String): Slice {
            return Slice.Builder(name)
                    .textColor(itemView.color(R.color.white))
                    .build()
        }
    }
}