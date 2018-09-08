package com.gnoemes.shikimoriapp.presentation.view.search.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource
import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeViewModel
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.PreferenceAdapterDelegate
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider
import com.gnoemes.shikimoriapp.utils.color
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader
import com.gnoemes.shikimoriapp.utils.inflate
import com.gnoemes.shikimoriapp.utils.kotlin.color
import com.mpt.android.stv.Slice
import kotlinx.android.synthetic.main.item_anime_search.view.*

class SearchAnimeAdapterDelegate(
        settings: UserSettingsSource,
        private val resourceProvider: SearchAnimeResourceProvider,
        private val imageLoader: ImageLoader,
        private val callback: ((Type, Long) -> Unit)
) : PreferenceAdapterDelegate<List<BaseItem>>(settings) {

    override fun isForViewType(items: List<BaseItem>, position: Int): Boolean =
            items[position] is AnimeViewModel

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_anime_search, false))

    override fun onBindViewHolder(items: List<BaseItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as ViewHolder).bind(items[position] as AnimeViewModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: AnimeViewModel) {
            with(itemView) {
                imageLoader.setImageWithFit(animeImageView, item.imageOriginalUrl)

                nameView.reset()
                if (!TextUtils.isEmpty(item.name)) {
                    nameView.addSlice(getSliceWithName(item.name))
                } else {
                    nameView.addSlice(getSliceWithName(item.secondName))
                }

                val episodes = String.format(resourceProvider.episodeStringFormat,
                        if (item.status == Status.RELEASED) item.episodes else item.episodesAired,
                        if (item.episodes == 0) "xxx" else item.episodes)

                nameView.addSlice(Slice.Builder(episodes)
                        .textColor(context.color(R.color.colorAccentInverse))
                        .build())
                nameView.display()
                container.setOnClickListener { callback.invoke(Type.ANIME, item.id) }
            }
        }

        private fun getSliceWithName(name: String): Slice {
            return Slice.Builder(name)
                    .textColor(itemView.color(R.color.white))
                    .build()
        }
    }
}