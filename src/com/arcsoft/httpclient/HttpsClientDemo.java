
package com.arcsoft.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class HttpsClientDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                try {
                    HttpClient httpClient = null;
                    if (false) {
                        HttpClientHelper helper = HttpClientHelper.getInstance();
                        httpClient = helper.createNewDefaultHttpClient();
                    } else {
                        HttpClientHelper helper = HttpClientHelper.getInstance();
                        httpClient = new DefaultHttpClient();
                        helper.registerHttpsScheme(httpClient);
                    }

                    HttpGet httpget = new HttpGet("https://172.21.30.117/index.html");
                    HttpResponse response = httpClient.execute(httpget);
                    System.out.println(response.getStatusLine());
                    InputStream inputStream = response.getEntity().getContent();
                    String result = readFromInputStream(new InputStreamReader(inputStream));
                    Log.i("HttpClientDemo", result);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        }).start();
    }

    /**
     * Register the https scheme.
     */
    public void registerHttpsSchemeWithKeyStore() {

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream inputStream = getAssets().open("ishare.cer");
            // keyStore.load(inputStream, "password".toCharArray());
            keyStore.load(inputStream, null);

            SSLSocketFactory factory = new SSLSocketFactory(keyStore);
            Scheme https = new Scheme("https", factory, 443);
            SchemeRegistry sr = new SchemeRegistry();
            sr.register(https);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Read data from InputStreamReader
     * 
     * @param isr InputStreamReader
     * @return The Data read from The InputStreamReader
     */
    public static String readFromInputStream(InputStreamReader isr) {
        String result = null;

        BufferedReader rd = new BufferedReader(isr);
        String line;
        try {
            while ((line = rd.readLine()) != null) {
                result += line + '\n';
            }

            isr.close();
            rd.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
}
