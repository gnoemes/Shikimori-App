package com.gnoemes.shikimoriapp.presentation.view.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.manga.presentation.MangaViewModel
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.PreferenceAdapterDelegate
import com.gnoemes.shikimoriapp.utils.color
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.inflate
import com.gnoemes.shikimoriapp.utils.kotlin.color
import com.mpt.android.stv.Slice
import kotlinx.android.synthetic.main.item_search.view.*

class SearchMangaAdapterDelegate(settings: UserSettingsSource,
                                 private val imageLoader: ImageLoader,
                                 private val callback: (Type, Long) -> Unit
) : PreferenceAdapterDelegate<List<BaseItem>>(settings) {

    override fun isForViewType(items: List<BaseItem>, position: Int): Boolean =
            items[position] is MangaViewModel

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_search, false))

    override fun onBindViewHolder(items: List<BaseItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as ViewHolder).bind(items[position] as MangaViewModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MangaViewModel) {
            with(itemView) {
                imageLoader.setImageWithFit(animeImageView, item.image.original)

                nameView.reset()
                if (isRomadziNaming()) {
                    nameView.addSlice(getSliceWithName(item.name))
                } else {
                    nameView.addSlice(getSliceWithName(item.nameRu ?: item.name))
                }
                typeView.text = item.type.name

                val episodes = String.format(context.getString(R.string.calendar_volumes_format),
                        if (item.volumes == 0) "xxx" else item.volumes,
                        if (item.chapters == 0) "xxx" else item.chapters)

                nameView.addSlice(Slice.Builder(episodes)
                        .textColor(context.color(R.color.colorAccentInverse))
                        .build())
                nameView.display()
                container.setOnClickListener { callback.invoke(Type.MANGA, item.id) }
            }
        }

        private fun getSliceWithName(name: String?): Slice {
            return Slice.Builder(name)
                    .textColor(itemView.color(R.color.white))
                    .build()
        }
    }
}