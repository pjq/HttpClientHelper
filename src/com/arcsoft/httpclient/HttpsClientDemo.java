
package com.arcsoft.httpclient;

import com.arcsoft.IShare.account.AccountAsyncTask;
import com.arcsoft.IShare.account.AccountAsyncTask.TaskListener;
import com.arcsoft.IShare.account.RegisterTask;
import com.arcsoft.IShare.account.TaskExecutor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HttpsClientDemo extends Activity {
    public static final String TAG = HttpsClientDemo.class.getSimpleName();
    TaskExecutor mTaskExecutor;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        RegisterTask registerTask = new RegisterTask();
        AccountAsyncTask accountAsyncTask = new AccountAsyncTask(new TaskListener() {

            @Override
            public void onTaskStart() {
                // TODO Auto-generated method stub
                Log.i(TAG, "onTaskStart");

            }

            @Override
            public void onTaskProgressUpdate(Integer... values) {
                // TODO Auto-generated method stub
                Log.i(TAG, "onTaskProgressUpdate,values=" + values[0]);

            }

            @Override
            public void OnTaskFinished(String result) {
                // TODO Auto-generated method stub
                Log.i(TAG, "OnTaskFinished,result=" + result);

            }
        });

        accountAsyncTask.execute(registerTask);
        // mTaskExecutor = new TaskExecutor();
        // mTaskExecutor.addTask(registerTask);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        mTaskExecutor.stopExecutorThread();
    }

}
