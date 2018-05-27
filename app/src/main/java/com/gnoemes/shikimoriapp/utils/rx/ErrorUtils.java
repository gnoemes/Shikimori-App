package com.gnoemes.shikimoriapp.utils.rx;

import android.util.Log;

import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.ServiceCodeException;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseView;

import ru.terrakok.cicerone.Router;

public class ErrorUtils {

    private static final String TAG = "ErrorUtils";

    public void processErrors(Throwable throwable,
                              Router router,
                              BaseView baseView) {
        if (router != null && throwable instanceof BaseException) {

//            BaseException baseException = (BaseException) throwable;

            Log.e(TAG, "processErrors: ", throwable);
            if (throwable instanceof ServiceCodeException) {
                ServiceCodeException exception = (ServiceCodeException) throwable;
                if (exception.getServiceCode() >= 500) {
                    router.showSystemMessage("Произошла ошибка в работе сервера shikimori.org");
                }
            }
        }
    }
}
