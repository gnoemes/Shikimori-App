package com.gnoemes.shikimoriapp.presentation.view.manga.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.anime.domain.Status
import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType
import com.gnoemes.shikimoriapp.entity.manga.presentation.MangaHeadItem
import com.gnoemes.shikimoriapp.presentation.presenter.anime.GenresAdapter
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider
import com.gnoemes.shikimoriapp.utils.*
import com.gnoemes.shikimoriapp.utils.kotlin.onClick
import com.gnoemes.shikimoriapp.utils.view.RecyclerItemClickListener
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.mpt.android.stv.Slice
import kotlinx.android.synthetic.main.item_details_head.view.*

class MangaHeadAdapter(private val resourceProvider: RateResourceProvider,
                       private val callback: ((DetailsAction, Any?) -> Unit)
) : AdapterDelegate<List<BaseItem>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_details_head))

    override fun isForViewType(items: List<BaseItem>, position: Int): Boolean =
            items[position] is MangaHeadItem

    override fun onBindViewHolder(items: List<BaseItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as ViewHolder).bind((items[position] as MangaHeadItem))
    }

    @SuppressLint("ResourceType")
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textColor by lazy { itemView.context.colorAttr(R.attr.colorText) }
        private val textSize by lazy { itemView.context.dimen(R.dimen.text_default).toInt() }
        private val genresAdapter by lazy { GenresAdapter() }

        init {
            val flexboxLayoutManager = FlexboxLayoutManager(itemView.context).also { it.flexWrap = FlexWrap.WRAP }
            with(itemView) {

                with(genreList) {
                    layoutManager = flexboxLayoutManager
                    adapter = genresAdapter
                    addOnItemTouchListener(RecyclerItemClickListener(itemView.context, RecyclerItemClickListener.OnItemClickListener { _, pos -> callback.invoke(DetailsAction.GENRE, genresAdapter.getItemByPosition(pos)) }))
                }

                with(onlineButton) {
                    val playDrawable = context.drawable(R.drawable.ic_play_circle_filled)
                    playDrawable?.tint(context.color(R.color.white))
                    setCompoundDrawablesWithIntrinsicBounds(playDrawable, null, null, null)
                    setText(R.string.read_online)
                    isEnabled = false
                    background = ColorDrawable(context.color(R.color.gray))
                    onClick { callback.invoke(DetailsAction.WATCH_ONLINE, null) }
                }

                with(similarCompound) {
                    val drawable = context.themeDrawable(R.drawable.ic_arrange_send_backward, R.attr.colorText)
                    setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                    onClick { callback.invoke(DetailsAction.SIMILAR, null) }
                }

                with(relatedCompound) {
                    val drawable = context.themeDrawable(R.drawable.ic_arrange_bring_to_front, R.attr.colorText)
                    setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                    onClick { callback.invoke(DetailsAction.RELATED, null) }
                }

                optionsMenu.onClick { showPopup() }
            }
        }

        fun bind(item: MangaHeadItem) {
            with(itemView) {
                with(firstDescriptionView) {
                    reset()
                    addSlice(getSliceTitle(context.getString(R.string.common_season)))
                    addSlice(getSliceContent(item.season))
                    display()
                }

                with(typeView) {
                    reset()
                    val volumes = context.resources.getQuantityString(R.plurals.volumes, item.volumes, item.volumes)
                    val chapters = context.resources.getQuantityString(R.plurals.chapters, item.chapters, item.chapters)
                    val type = String.format(context.getString(R.string.manga_type_format), getLocalizedMangaType(item.type), volumes, chapters)
                    addSlice(getSliceTitle(context.getString(R.string.common_type)))
                    addSlice(getSliceContent(type))
                    display()
                }

                with(statusView) {
                    reset()
                    addSlice(getSliceTitle(context.getString(R.string.common_status)))
                    addSlice(getSliceContent(getLocalizedStatus(item.status)))
                    display()
                }

                with(genreView) {
                    reset()
                    addSlice(getSliceTitle(context.getString(R.string.common_genre)))
                    display()
                }

                genresAdapter.bindItems(item.genres)

                ratingView.rating = (item.score / 2).toFloat()
                ratingValueView.text = "${item.score}"


                with(rateCompound) {
                    if (item.userRate != null) {
                        val drawable = context.drawable(R.drawable.ic_star_big)
                        drawable?.tint(context.color(R.color.colorAccentInverse))
                        setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                        text = resourceProvider.getLocalizedStatus(Type.MANGA, item.userRate.status)
                    } else {
                        val drawable = context.themeDrawable(R.drawable.ic_star_border, R.attr.colorText)
                        setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                        setText(R.string.no_rate)
                    }
                    onClick { callback.invoke(DetailsAction.ADD_TO_LIST, item.userRate) }
                }


            }
        }

        private fun getLocalizedStatus(status: Status): String {
            return when (status) {
                Status.ANONS -> itemView.context.getString(R.string.status_anons)
                Status.RELEASED -> itemView.context.getString(R.string.status_released)
                Status.ONGOING -> itemView.context.getString(R.string.status_ongoing)
                else -> itemView.context.getString(R.string.error_no_data)
            }
        }

        private fun getLocalizedMangaType(type: MangaType): String {
            return when (type) {
                MangaType.DOUJIN -> itemView.context.getString(R.string.type_doujin_translatable)
                MangaType.MANGA -> itemView.context.getString(R.string.type_manga_translatable)
                MangaType.ONE_SHOT -> itemView.context.getString(R.string.type_one_shot_translatable)
                MangaType.NOVEL -> itemView.context.getString(R.string.type_novel_translatable)
                MangaType.MANHWA -> itemView.context.getString(R.string.type_manhwa_translatable)
                MangaType.MANHUA -> itemView.context.getString(R.string.type_manhua_translatable)
                else -> itemView.context.getString(R.string.error_no_data)
            }
        }

        /**
         * Returns slice for title (e.g. **Genre:**)
         *
         * @param text String
         * @return Slice title
         */
        private fun getSliceTitle(text: String): Slice {
            return Slice.Builder("$text: ")
                    .textColor(textColor)
                    .textSize(textSize)
                    .style(Typeface.BOLD)
                    .build()
        }

        /**
         * Returns slice for content (base text)
         *
         * @param text String
         * @return Slice content
         */
        private fun getSliceContent(text: String): Slice {
            return Slice.Builder(text)
                    .textColor(textColor)
                    .textSize(textSize)
                    .build()
        }

        private fun showPopup() {
            val wrapper = ContextThemeWrapper(itemView.context, R.style.PopupMenuTheme)
            val popupMenu = PopupMenu(wrapper, itemView.optionsMenu)
            popupMenu.inflate(R.menu.menu_anime)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_open -> callback.invoke(DetailsAction.OPEN_IN_BROWSER, null)
                    R.id.item_clear_history -> callback.invoke(DetailsAction.CLEAR_HISTORY, null)
                }
                false
            }
            popupMenu.show()
        }

    }
}

