package com.gnoemes.shikimoriapp.presentation.view.alternative.translations

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationNavigationData
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras
import com.gnoemes.shikimoriapp.presentation.presenter.alternative.AlternativeTranslationsPresenter
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.adapter.TranslationAdapter
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.adapter.TranslationItemCallback
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider
import com.gnoemes.shikimoriapp.utils.addBackButton
import com.gnoemes.shikimoriapp.utils.ifNotNull
import com.gnoemes.shikimoriapp.utils.themeDrawable
import com.gnoemes.shikimoriapp.utils.withArgs
import kotlinx.android.synthetic.main.fragment_translations.*
import kotlinx.android.synthetic.main.layout_default_list.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied

class AlternativeTranslationsFragment : BaseFragment<AlternativeTranslationsPresenter, AlternativeTranslationsView>(),
        AlternativeTranslationsView {

    @InjectPresenter
    lateinit var alternativePresenter: AlternativeTranslationsPresenter

    @ProvidePresenter
    fun providePresenter(): AlternativeTranslationsPresenter {
        alternativePresenter = presenterProvider.get()

        arguments.ifNotNull {

            parentFragment.ifNotNull { parent ->
                alternativePresenter.setLocalRouter((parent as RouterProvider).localRouter)
            }

            alternativePresenter.data = it.getSerializable(AppExtras.ARGUMENT_TRANSLATION_DATA) as AlternativeTranslationNavigationData

        }

        return alternativePresenter
    }

    val translationAdapter: TranslationAdapter by lazy {
        TranslationAdapter(object : TranslationItemCallback {
            override fun onTranslationClicked(translation: AlternativeTranslationViewModel) {
                getPresenter().onTranslationClicked(translation)
            }

            override fun onDownloadTranslation(translation: AlternativeTranslationViewModel) {
                getPresenter().onDownloadTranslation(translation)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(data: AlternativeTranslationNavigationData) = AlternativeTranslationsFragment()
                .withArgs { putSerializable(AppExtras.ARGUMENT_TRANSLATION_DATA, data) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("ResourceType")
    private fun initViews() {

        with(viewEmpty) {
            setText(R.string.translations_empty)
            setCallback { getPresenter().onFindAll() }
            visibility = View.GONE
        }
        networkErrorView.visibility = View.GONE
        refreshLayout.setOnRefreshListener { getPresenter().loadTranslations() }

        with(list) {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(context)
            adapter = translationAdapter
        }
        toolbar.addBackButton()
        toolbar.setNavigationOnClickListener { getPresenter().onBackPressed() }
        toolbar.inflateMenu(R.menu.menu_translations)

        toolbar.menu
                .getItem(0).icon = context?.themeDrawable(R.drawable.ic_settings, R.attr.colorText)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_settings -> getPresenter().onSettingsClicked()
            }
            false
        }
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onPermissionDenied() {
        getPresenter().onPermissionDenied()
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onNeverAskAgain() {
        getPresenter().onNeverAskAgain()
    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onPermissionGranted() {
        getPresenter().onPermissionGranted()
    }


    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    override fun getPresenter(): AlternativeTranslationsPresenter = alternativePresenter


    override fun getFragmentLayout(): Int = R.layout.fragment_translations

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    override fun showTranslations(models: MutableList<AlternativeTranslationViewModel>?) {
        list.visibility = View.VISIBLE
        translationAdapter.bindItems(models)
    }

    override fun hideErrorView() {
        networkErrorView.visibility = View.GONE
    }

    override fun showErrorView() {
        networkErrorView.visibility = View.VISIBLE
    }

    override fun showEmptyView() {
        viewEmpty.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun hideEmptyView() {
        viewEmpty.visibility = View.GONE
    }

    override fun showSettingsDialog() {
        context.ifNotNull {
            MaterialDialog(it).show {
                listItemsSingleChoice(R.array.translations_type_alternative) { _, index, _ -> getPresenter().onTypeClicked(AlternativeTranslationType.values()[index]) }
                negativeButton(R.string.common_cancel)
            }
        }
    }

    override fun checkPermissions() {
//        AlternativeTranslationsFragmentPermissionsDispatcher.onPermissionGrantedWithPermissionCheck(this)
    }
}