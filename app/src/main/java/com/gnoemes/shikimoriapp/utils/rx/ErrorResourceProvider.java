package com.gnoemes.shikimoriapp.utils.rx;

public interface ErrorResourceProvider {

    String getUnknownHostException();

    String getSocketTimeoutException();

    String getJsonSyntaxException();

    String getConnectionErrorException();

    String getUnknownException();
}
