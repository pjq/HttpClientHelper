
package com.arcsoft.httpclient;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

                    // Two ways to create the HttpClient.
                    if (true) {
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

        
                    
                    HttpPost httpPost = new HttpPost("http://180.168.105.166:8012/AASService.asmx/Register");
                    // StringEntity entity = new StringEntity(
                    // "nType=string&strKey=string&strPassword=string","UTF-8");
                    
                    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                    formparams.add(new BasicNameValuePair("nType", "string"));
                    formparams.add(new BasicNameValuePair("strKey", "string"));
                    formparams.add(new BasicNameValuePair("strPassword", "string"));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);   
                   
                    httpPost.setEntity(entity);
                    response = httpClient.execute(httpPost);
                    System.out.println(response.getStatusLine());
                    inputStream = response.getEntity().getContent();
                    result = readFromInputStream(new InputStreamReader(inputStream));
                    Log.i("HttpClientDemo,post response", result);

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        }).start();
    }

    /**
     * Read data from InputStreamReader
     * 
     * @param isr InputStreamReader
     * @return The Data read from The InputStreamReader
     */
    public static String readFromInputStream(InputStreamReader isr) {
        String result="";

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
