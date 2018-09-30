package com.gnoemes.shikimoriapp.presentation.presenter.common

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens
import com.gnoemes.shikimoriapp.entity.manga.presentation.MangaNavigationData
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseView
import ru.terrakok.cicerone.Router


abstract class BaseNavigationPresenter<View : BaseView> : BasePresenter<View>() {

    lateinit var localRouter: Router

    override fun getRouter(): Router = localRouter

    override fun onBackPressed() = route { router.exit() }

    open fun onAnimeClicked(id: Long) = route { router.navigateTo(Screens.ANIME_DETAILS, id) }

    open fun onMangaClicked(id: Long) = route { router.navigateTo(Screens.MANGA_DETAILS, MangaNavigationData(id, Type.MANGA)) }
    open fun onRanobeClicked(id: Long) = route { router.navigateTo(Screens.MANGA_DETAILS, MangaNavigationData(id, Type.RANOBE)) }
    open fun onCharacterClicked(id: Long) = route { router.navigateTo(Screens.CHARACTER_DETAILS, id) }
    open fun onUserClicked(id: Long) = route { router.navigateTo(Screens.PROFILE, id) }
    open fun onPersonClicked(id: Long) = route { router.navigateTo(Screens.PERSON_DETAILS, id) }
    open fun onOpenWeb(url: String) = route { router.navigateTo(Screens.WEB, url) }

    fun onContentClicked(type: Type, id: Long) {
        when (type) {
            Type.ANIME -> onAnimeClicked(id)
            Type.MANGA -> onMangaClicked(id)
            Type.RANOBE -> onRanobeClicked(id)
            Type.CHARACTER -> onCharacterClicked(id)
            Type.USER -> onUserClicked(id)
            Type.PERSON -> onPersonClicked(id)
        }
    }

    fun onLinkedContentClicked(id: Long, type: LinkedType) {
        when (type) {
            LinkedType.ANIME -> onAnimeClicked(id)
            LinkedType.MANGA -> onMangaClicked(id)
            LinkedType.RANOBE -> onRanobeClicked(id)
            LinkedType.CHARACTER -> onCharacterClicked(id)
            LinkedType.PERSON -> onPersonClicked(id)
            else -> router.showSystemMessage("В разработке")
        }
    }

    private fun <T : Any> T.route(f: (it: T) -> Unit) {
        if (::localRouter.isInitialized) f(this)
    }
}