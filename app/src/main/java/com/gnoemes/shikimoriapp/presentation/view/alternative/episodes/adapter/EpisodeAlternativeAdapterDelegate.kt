package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeItem
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.utils.inflate
import com.gnoemes.shikimoriapp.utils.onClick
import com.gnoemes.shikimoriapp.utils.themeDrawable
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.item_episode.view.*

class EpisodeAlternativeAdapterDelegate(val callback: (EpisodeItem) -> Unit) : AdapterDelegate<List<BaseItem>>() {

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder =
            ViewHolder(parent!!.inflate(R.layout.item_episode, false))

    override fun isForViewType(items: List<BaseItem>, position: Int): Boolean = items[position] is EpisodeItem

    override fun onBindViewHolder(items: List<BaseItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as ViewHolder).bind(items[position] as EpisodeItem)
    }

    @SuppressLint("ResourceType")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.container.background = itemView.context.themeDrawable(R.drawable.card, R.attr.colorBackgroundContent)
        }

        fun bind(episode: EpisodeItem) {
            with(itemView) {
                watchedBadge.visibility = View.GONE
                hostingsTextView.text = null
                episodeTextView.text = null
                episodeTextView.text = episode.episodeFull
                hostingsTextView.text = "smotret-anime.ru"

                if (episode.isWatched) {
                    watchedBadge.visibility = View.VISIBLE
                }
                container.onClick { callback.invoke(episode) }
            }
        }
    }
}