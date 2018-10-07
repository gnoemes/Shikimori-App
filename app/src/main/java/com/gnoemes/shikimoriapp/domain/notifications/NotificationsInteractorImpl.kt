package com.gnoemes.shikimoriapp.domain.notifications

import com.gnoemes.shikimoriapp.data.repository.notifications.NotificationsRepository
import com.gnoemes.shikimoriapp.data.repository.user.UserRepository
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler
import com.gnoemes.shikimoriapp.utils.rx.RxUtils
import io.reactivex.Completable
import javax.inject.Inject

class NotificationsInteractorImpl @Inject constructor(
        private val settingsInteractor: UserSettingsInteractor,
        private val userRepository: UserRepository,
        private val notificationsRepository: NotificationsRepository,
        private val completableErrorHandler: CompletableErrorHandler,
        private val rxUtils: RxUtils
) : NotificationsInteractor {

//    override fun syncAnimeNotifications(): Completable =
//            Single.fromCallable { settingsInteractor.userStatus }
//                    .filter { it == UserStatus.AUTHORIZED }
//                    .map { settingsInteractor.isNotificationsEnabled }
//                    .filter { it }
//                    .flatMapSingle { userRepository.getUserMessages(MessageType.NEWS) }
//                    .flatMap { list ->
//                        Observable.fromIterable(list)
//                                .filter { it.type == MessageType.EPISODE }
//                                .doOnNext { if (notificationsRepository.lastNewsMessageDate() == -1L) notificationsRepository.saveNewsMessageDate(it.dateCreated) }
//                                .filter { it.dateCreated.millis > notificationsRepository.lastNewsMessageDate() }
//                                .toList()
//                    }
//                    .map { notificationsRepository.saveNewsMessageDate(it.firstOrNull()?.dateCreated); it }
//                    .flatMapCompletable { list ->
//                        Observable.fromIterable(list)
//                                .flatMapCompletable { notificationsRepository.createNotification(NotificationData(NotificationType.NEW_EPISODE, it)) }
//                    }
//                    .compose(completableErrorHandler)
//                    .compose(rxUtils.applyCompleteSchedulers())

    override fun syncAnimeNotifications(): Completable = Completable.complete()
}