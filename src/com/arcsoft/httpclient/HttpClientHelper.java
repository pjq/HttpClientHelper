
package com.arcsoft.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * The {@link HttpClient} helper,it support trust the self-signed
 * {@link KeyStore} You can use {@link #getInstance()} to get it.And use the
 * static function {@link #createNewDefaultHttpClient()} to create the special
 * http/https client.
 * 
 * @author pjq0274@arcsoft.com
 * @date 2011-4-21
 */

public class HttpClientHelper {
    private static HttpClientHelper mInstance;
    private MySSLSocketFactory mMySSLSocketFactory;

    public HttpClientHelper() {
        // TODO Auto-generated constructor stub
        KeyStore truststore;
        try {
            truststore = KeyStore.getInstance(KeyStore.getDefaultType());
            mMySSLSocketFactory = new MySSLSocketFactory(truststore);
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Get the singleton instance.
     * 
     * @return
     */
    public static HttpClientHelper getInstance() {
        if (null == mInstance) {
            mInstance = new HttpClientHelper();
        }

        return mInstance;
    }

    /**
     * Create the new {@link DefaultHttpClient} with the special registed
     * http/https schemes,and it support trust the self-signed KeyStore.
     * 
     * @return {@link DefaultHttpClient} or null if something error occurs.
     */
    public HttpClient createNewDefaultHttpClient() {
        if (null != mMySSLSocketFactory) {
            return mMySSLSocketFactory.createNewDefaultHttpClient();
        } else {
            return null;
        }
    }

    /**
     * Set Proxy here,including http proxy and https proxy
     */
    private static void setProxy() {
        // Set Http Proxy
        System.getProperties().setProperty("http.proxyHost", "10.85.40.153");
        System.getProperties().setProperty("http.proxyPort", "8000");

        // Set Https Proxy
        // FIXME Should import the certificates to avoid the trust problem.
        // To avoid the certification problems,just trust all the certs.
        // SSLUtilities.trustAllHttpsCertificates();
        System.getProperties().setProperty("https.proxyHost", "10.85.40.153");
        System.getProperties().setProperty("https.proxyPort", "8000");
    }

    /**
     * Register the https scheme to the httpClient
     * 
     * @param httpClient the HttpClient need to support the self-signed
     *            KeyStore.
     */
    public void registerHttpsScheme(HttpClient httpClient) {
        SSLSocketFactory factory;
        try {
            factory = new SSLSocketFactory(null);
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            factory = new MySSLSocketFactory(trustStore);
            factory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme https = new Scheme("https", factory, 443);
            httpClient.getConnectionManager().getSchemeRegistry().register(https);
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
