package com.gnoemes.shikimoriapp.entity.app.domain;

public class ServiceCodeException extends BaseException {
    public static final String TAG = "ServiceCodeException";

    private int serviceCode;

    public ServiceCodeException(int serviceCode) {
        super(TAG);
        this.serviceCode = serviceCode;
    }

    public ServiceCodeException(String tag, int serviceCode) {
        super(tag);
        this.serviceCode = serviceCode;
    }

    public int getServiceCode() {
        return serviceCode;
    }
}
