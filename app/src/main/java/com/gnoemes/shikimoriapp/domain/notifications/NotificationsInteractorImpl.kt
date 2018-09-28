package com.gnoemes.shikimoriapp.domain.notifications

import com.gnoemes.shikimoriapp.data.repository.notifications.NotificationsRepository
import com.gnoemes.shikimoriapp.data.repository.user.UserRepository
import com.gnoemes.shikimoriapp.domain.app.UserSettingsInteractor
import com.gnoemes.shikimoriapp.entity.app.domain.NotificationData
import com.gnoemes.shikimoriapp.entity.app.domain.NotificationType
import com.gnoemes.shikimoriapp.entity.app.domain.UserStatus
import com.gnoemes.shikimoriapp.entity.user.domain.MessageType
import com.gnoemes.shikimoriapp.utils.rx.CompletableErrorHandler
import com.gnoemes.shikimoriapp.utils.rx.RxUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject

class NotificationsInteractorImpl @Inject constructor(
        private val settingsInteractor: UserSettingsInteractor,
        private val userRepository: UserRepository,
        private val notificationsRepository: NotificationsRepository,
        private val completableErrorHandler: CompletableErrorHandler,
        private val rxUtils: RxUtils
) : NotificationsInteractor {

    override fun syncAnimeNotifications(): Completable =
            Single.fromCallable { settingsInteractor.userStatus }
                    .filter { it == UserStatus.AUTHORIZED }
                    .map { settingsInteractor.isNotificationsEnabled }
                    .filter { it }
                    .flatMapSingle { userRepository.getUserMessages(MessageType.NEWS) }
                    .flatMap { list ->
                        Observable.fromIterable(list)
                                .filter { it.type == MessageType.EPISODE }
                                .filter { it.dateCreated.millis > notificationsRepository.lastNewsMessageDate().millis }
                                .toList()
                    }
                    .map { notificationsRepository.saveNewsMessageDate(DateTime.now()); it }
                    .flatMapCompletable { list ->
                        Observable.fromIterable(list)
                                .flatMapCompletable { notificationsRepository.createNotification(NotificationData(NotificationType.NEW_EPISODE, it.linked)) }
                    }
                    .compose(completableErrorHandler)
                    .compose(rxUtils.applyCompleteSchedulers())
}