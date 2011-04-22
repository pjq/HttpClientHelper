
package com.arcsoft.IShare.account;

import com.arcsoft.httpclient.HttpClientHelper;
import com.arcsoft.httpclient.ServerConfig;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Register task,do register in the {@link #run()}.
 * 
 * @author pjq0274@arcsoft.com
 * @date 2011-4-22
 */

public class RegisterTask extends BaseTask {
    public static final String TAG = RegisterTask.class.getSimpleName();

    public RegisterTask() {
        // TODO Auto-generated constructor stub
        mTaskDesc = TAG;
        mTaskId = TASK_ACCOUNT_REGISTER;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        // Do Register.
        try {
            HttpClientHelper helper = HttpClientHelper.getInstance();
            HttpClient httpClient = helper.createNewDefaultHttpClient();

            HttpPost httpPost = new HttpPost(ServerConfig.HTTP_SERVER_REGISTER_URL);
            httpPost.addHeader("Accept", "text/plain");
            httpPost.addHeader("Accept-Charset", "UTF-8,*;q=0.5");
            StringEntity soapEntity = new StringEntity(
                    ServerConfig.SOAP_TMP, "UTF-8");
            soapEntity.setContentType("text/xml");

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("nType", "1"));
            formparams.add(new BasicNameValuePair("strKey", "string"));
            formparams.add(new BasicNameValuePair("strPassword", "string"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);

            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine());
            InputStream inputStream = response.getEntity().getContent();
            String result = helper.readFromInputStream(new InputStreamReader(inputStream));
            Log.i("HttpClientDemo,post response", result);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        // After done,send notify.
        sendNotify();
    }
}
