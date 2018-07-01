package com.gnoemes.shikimoriapp.utils.net;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

public class NetworkHelper {

    private Context context;

    private File cacheDir;
    private long cacheSize = 5L * 1024 * 1024;

    private OkHttpClient client;

    public NetworkHelper(Context context) {
        this.context = context;
        cacheDir = new File(context.getCacheDir(), "net_cache");
        configureClient();
    }

    private void configureClient() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            client = new OkHttpClient.Builder()
                    .cache(new Cache(cacheDir, cacheSize))
                    .build();
        }


        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        TrustManager[] trustManagers = null;
        if (trustManagerFactory != null) {
            trustManagers = trustManagerFactory.getTrustManagers();
        }

        if (trustManagers != null && trustManagers.length == 1 && trustManagers[0] instanceof X509TrustManager) {
            TLSSocketFactory factory = null;

            try {
                factory = new TLSSocketFactory();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            List<CipherSuite> suites = ConnectionSpec.MODERN_TLS.cipherSuites();
            ConnectionSpec specs = null;
            if (suites != null) {
                suites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA);
                suites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA);
                specs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                        .cipherSuites(suites.toArray(new CipherSuite[suites.size()]))
                        .build();
            }


            client = new OkHttpClient.Builder()
                    .cache(new Cache(cacheDir, cacheSize))
                    .sslSocketFactory(factory, (X509TrustManager) trustManagers[0])
                    .connectionSpecs(Collections.singletonList(specs))
                    .build();
        }
    }

    public OkHttpClient getClient() {
        return client;
    }

    class TLSSocketFactory extends SSLSocketFactory {

        private SSLSocketFactory internalSSLSocketFactory;

        public TLSSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            internalSSLSocketFactory = context.getSocketFactory();
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return internalSSLSocketFactory.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return internalSSLSocketFactory.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket() throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket());
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(address, port, localAddress, localPort));
        }

        @Nullable
        private Socket enableTLSOnSocket(@Nullable Socket socket) {
            if (socket != null && socket instanceof SSLSocket) {
                ((SSLSocket) socket).setEnabledProtocols(((SSLSocket) socket).getSupportedProtocols());
            }
            return socket;
        }
    }

}
