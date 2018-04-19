package com.gnoemes.shikimoriapp.utils.rx;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.app.domain.BaseException;
import com.gnoemes.shikimoriapp.entity.app.domain.NetworkException;
import com.gnoemes.shikimoriapp.entity.app.domain.TitleException;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ErrorProcessing<T> {

    private ErrorResourceProvider resourceProvider;

    public ErrorProcessing(ErrorResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    Observable<T> geObservableErrors(Throwable throwable) {
        return Observable.error(() -> throwProcessException(throwable));
    }

    Single<T> getSingleErrors(Throwable throwable) {
        return Single.error(() -> throwProcessException(throwable));
    }

    Completable getCompletableErrors(Throwable throwable) {
        return Completable.error(() -> throwProcessException(throwable));
    }

    @NonNull
    private Throwable throwProcessException(Throwable throwable) throws TitleException, NetworkException {

        if (throwable instanceof UnknownHostException) {
            throw new NetworkException(resourceProvider.getUnknownHostException());
        }

        if (throwable instanceof ConnectException) {
            throw new NetworkException(resourceProvider.getConnectionErrorException());
        }

        if (throwable instanceof SocketTimeoutException) {
            throw new NetworkException(resourceProvider.getSocketTimeoutException());
        }

        if (throwable instanceof JsonSyntaxException) {
            throw new TitleException("", resourceProvider.getJsonSyntaxException());
        }

        if (throwable instanceof BaseException) {
            return throwable;
        } else {
            return new TitleException("", throwable.getMessage());
        }
    }

}
