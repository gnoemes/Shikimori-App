package com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeItem
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionsItem
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.utils.inflate
import com.gnoemes.shikimoriapp.utils.themeDrawable
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.item_episode.view.*
import kotlinx.android.synthetic.main.item_episode_options.view.*

class EpisodeAlternativeOptionAdapterDelegate(val callback: ((EpisodeOptionAction, EpisodeItem) -> Unit)
) : AdapterDelegate<List<BaseItem>>() {

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder =
            ViewHolder(parent!!.inflate(R.layout.item_episode_options, false))

    override fun isForViewType(items: List<BaseItem>, position: Int): Boolean =
            items[position] is EpisodeOptionsItem

    override fun onBindViewHolder(items: List<BaseItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as ViewHolder).bind(items[position] as EpisodeOptionsItem)
    }

    @SuppressLint("ResourceType")
    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.constraint.background = itemView.context.themeDrawable(R.drawable.card, R.attr.colorBackgroundWindow)
            val icon = itemView.context.themeDrawable(R.drawable.ic_play_circle_filled, R.attr.colorText)
            itemView.continueButton.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        }

        fun bind(optionsItem: EpisodeOptionsItem) {
            with(itemView) {
                val buttonText: String = if (optionsItem.isFirst) {
                    itemView.resources.getString(R.string.watch_now)
                } else {
                    itemView.resources.getString(R.string.watch_continue)
                }
                episodeTextView.text = optionsItem.episodeItem.episodeFull
                continueButton.text = buttonText
                continueButton.setOnClickListener { callback.invoke(EpisodeOptionAction.ALTERNATIVE_SOURCE, optionsItem.episodeItem) }
                alternativeImageView.visibility = View.GONE
            }
        }
    }
}