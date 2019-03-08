package com.gnoemes.shikimoriapp.presentation.view.common.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.entity.main.domain.Constants
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProviderImpl
import com.gnoemes.shikimoriapp.utils.*
import com.mpt.android.stv.Slice
import kotlinx.android.synthetic.main.dialog_rate.view.*

class RateDialogFragment : MvpAppCompatDialogFragment() {

    private lateinit var type: Type
    private lateinit var customView: View
    private var rate: UserRate? = null
    private val resourceProvider by lazy { RateResourceProviderImpl(context) }
    var callback: RateDialogCallback? = null

    private var status: RateStatus? = null

    companion object {
        @JvmStatic
        fun newInstance(type: Type, rate: UserRate?) = RateDialogFragment().withArgs { putSerializable(TYPE_ARGUMENT, type); putParcelable(AppExtras.ARGUMENT_RATE, rate) }

        private const val TYPE_ARGUMENT = "TYPE_ARGUMENT"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments.ifNotNull {
            type = it.getSerializable(TYPE_ARGUMENT) as Type
            rate = it.getParcelable(AppExtras.ARGUMENT_RATE)
        }

        customView = View.inflate(context, R.layout.dialog_rate, null)
        initViews()

        return MaterialDialog(context!!.dialogContext())
                .show {
                    customView(view = customView, scrollable = true)
                    positiveButton(R.string.common_save) {
                        if (callback != null) {
                            var id = Constants.NO_ID
                            if (rate != null) {
                                id = rate?.id!!
                            }

                            rate = convertUserRate(id)
                            when (type) {
                                Type.ANIME -> callback?.onSaveAnimeRate(rate!!)
                                else -> callback?.onSaveMangaRate(rate!!)
                            }
                        }
                    }
                    negativeButton(R.string.common_cancel)
                    rate.ifNotNull {
                        neutralButton(R.string.common_delete) { materialDialog ->
                            when (type) {
                                Type.ANIME -> callback?.onDeleteAnimeRate(it.id!!)
                                else -> callback?.onDeleteMangaRate(it.id!!)
                            }
                        }
                    }
                }
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        with(customView) {
            with(ratesSpinnerView) {
                val stasuses = if (type == Type.ANIME) resourceProvider.animeRateStasuses else resourceProvider.mangaRateStasuses
                adapter = ArrayAdapter(context, R.layout.item_spinner_default, stasuses)
                val ratesBackground = background
                ratesBackground.tint(context?.colorAttr(R.attr.colorAccent)!!)
                background = ratesBackground
            }

            val episodesOrChapters = if (type == Type.ANIME) R.string.common_episodes else R.string.common_chapters
            episodesOrChaptersTitleView.setText(episodesOrChapters)

            with(episodesOrChaptersContentView) {
                val episodesBackground = episodesOrChaptersContentView.background
                episodesBackground.tint(context?.colorAttr(R.attr.colorAccent)!!)
                background = episodesBackground
                val text = if (type == Type.ANIME) rate?.episodes?.toString() else rate?.chapters?.toString()
                setText(text)
            }


            commentContentView.background = context?.themeDrawable(R.drawable.language_background, R.attr.colorBackgroundWindow)
            commentContentView.setText(rate?.text)

            ratingTitleView.addSlice(getRatingTitleSlice())
            ratingTitleView.addSlice(getRatingSlice(rate?.score?.toString() ?: "0"))

            ratingView.rating = rate?.score?.div(2)?.toFloat() ?: 0f
            ratingView.setOnRatingBarChangeListener { _, rating, _ ->
                with(ratingTitleView) {
                    replaceSliceAt(1, getRatingSlice(Math.round(rating * 2).toString()))
                    display()
                }
            }

            ratesSpinnerView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) = Unit

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    when (position) {
                        0 -> status = RateStatus.WATCHING
                        1 -> status = RateStatus.PLANNED
                        2 -> status = RateStatus.REWATCHING
                        3 -> status = RateStatus.COMPLETED
                        4 -> status = RateStatus.ON_HOLD
                        5 -> status = RateStatus.DROPPED
                    }
                }
            }

            ratingTitleView.display()

            rate.ifNotNull {
                ratesSpinnerView.setSelection(convertStatus(it.status))
            }
        }
    }

    private fun convertUserRate(id: Long): UserRate {
        val score = Math.round(customView.ratingView.rating * 2).toDouble()
        val text = customView.commentContentView.text.toString()
        val episodesOrChaptersString = customView.episodesOrChaptersContentView.text?.toString()
        val episodesOrChapters = if (!TextUtils.isEmpty(episodesOrChaptersString) && TextUtils.isDigitsOnly(episodesOrChaptersString)) episodesOrChaptersString?.toInt() else null

        return UserRate(
                id,
                score = score,
                status = status,
                episodes = if (type == Type.ANIME) episodesOrChapters else null,
                chapters = if (type == Type.ANIME) null else episodesOrChapters,
                text = text
        )
    }

    private fun convertStatus(status: RateStatus?): Int {
        return when (status) {
            RateStatus.WATCHING -> 0
            RateStatus.PLANNED -> 1
            RateStatus.REWATCHING -> 2
            RateStatus.COMPLETED -> 3
            RateStatus.ON_HOLD -> 4
            RateStatus.DROPPED -> 5
            else -> 0
        }
    }

    private fun getRatingTitleSlice(): Slice {
        return Slice.Builder(resources.getString(R.string.rate_rating) + " ")
                .style(Typeface.BOLD)
                .textColor(context?.colorAttr(R.attr.colorText)!!)
                .setSliceId(0)
                .build()
    }

    private fun getRatingSlice(rate: String): Slice {
        return Slice.Builder(String.format("%s/10", rate))
                .style(Typeface.BOLD)
                .textColor(context?.colorAttr(R.attr.colorText)!!)
                .setSliceId(1)
                .build()
    }

    interface RateDialogCallback {
        fun onSaveAnimeRate(rate: UserRate) = Unit
        fun onDeleteAnimeRate(id: Long) = Unit
        fun onSaveMangaRate(rate: UserRate) = Unit
        fun onDeleteMangaRate(id: Long) = Unit
    }
}