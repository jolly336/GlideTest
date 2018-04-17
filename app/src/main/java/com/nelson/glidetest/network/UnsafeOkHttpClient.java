package com.nelson.glidetest.network;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * 不安全的OkHttpClient（创建OkHttpClient禁用掉所有的SSL证书检查）
 *
 * 自己实现网络栈，接受自签名证书
 *
 * Created by Nelson on 16/12/19.
 */
public class UnsafeOkHttpClient {

    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {

                        }

                        /**
                         * Return a array of certificate authority certificates which are trusted for authenticating peers.
                         * Returns:
                         * a non-null (possibly empty) array of acceptable CA issuer certificates.
                         * @see https://github.com/square/okhttp/issues/2364
                         */
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            //创建OkHttpClient禁用掉所有的SSL证书检查
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory)
                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
            return builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
