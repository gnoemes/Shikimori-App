package com.gnoemes.shikimoriapp.presentation.presenter.common

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType
import com.gnoemes.shikimoriapp.entity.app.domain.Type
import com.gnoemes.shikimoriapp.entity.app.presentation.Screens
import com.gnoemes.shikimoriapp.entity.manga.presentation.MangaNavigationData
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseView
import ru.terrakok.cicerone.Router


abstract class BaseNavigationPresenter<View : BaseView> : BasePresenter<View>() {

    var localRouter: Router? = null

    //Todo убрать костыль/заменить класс на джаву
    // При лейтините падало в errorProcessing, т.к. роутер не был проинициализирован в некоторых компонентах
    override fun getRouter(): Router = localRouter ?: Router()

    override fun onBackPressed() = router.exit()

    open fun onAnimeClicked(id: Long) = router.navigateTo(Screens.ANIME_DETAILS, id)

    open fun onMangaClicked(id: Long) = router.navigateTo(Screens.MANGA_DETAILS, MangaNavigationData(id, Type.MANGA))
    open fun onRanobeClicked(id: Long) = router.navigateTo(Screens.MANGA_DETAILS, MangaNavigationData(id, Type.RANOBE))
    open fun onCharacterClicked(id: Long) = router.navigateTo(Screens.CHARACTER_DETAILS, id)
    open fun onUserClicked(id: Long) = router.navigateTo(Screens.PROFILE, id)
    open fun onPersonClicked(id: Long) = router.navigateTo(Screens.PERSON_DETAILS, id)
    open fun onOpenWeb(url: String) = router.navigateTo(Screens.WEB, url)

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
}