
package com.arcsoft.IShare.account;

import java.util.LinkedList;
import java.util.Queue;

import android.os.AsyncTask;
import android.util.Log;

/**
 * The AccountAsyncTask,if you need to listener the task status,pass
 * {@link TaskListener} to the constructor. <br>
 * Example
 * 
 * <pre>
 * RegisterTask registerTask = new RegisterTask();
 * AccountAsyncTask accountAsyncTask = new AccountAsyncTask(new TaskListener() {
 * 
 *     &#064;Override
 *     public void onTaskStart() {
 *         // TODO Auto-generated method stub
 *         Log.i(TAG, &quot;onTaskStart&quot;);
 * 
 *     }
 * 
 *     &#064;Override
 *     public void onTaskProgressUpdate(Integer... values) {
 *         // TODO Auto-generated method stub
 *         Log.i(TAG, &quot;onTaskProgressUpdate,values=&quot; + values[0]);
 * 
 *     }
 * 
 *     &#064;Override
 *     public void OnTaskFinished(String result) {
 *         // TODO Auto-generated method stub
 *         Log.i(TAG, &quot;OnTaskFinished,result=&quot; + result);
 * 
 *     }
 * });
 * 
 * accountAsyncTask.execute(registerTask);
 * </pre>
 * 
 * @author pjq0274@arcsoft.com
 * @date 2011-3-22
 */

public class AccountAsyncTask extends AsyncTask<BaseTask, Integer, String> {
    private TaskListener mListener;

    public static final int ASYNCTASK_REFRESH_BUDDY_LIST = 0;

    public static final int ASYNCTASK_REFRESH_PHONE_BOOK_LIST = 1;

    Queue<BaseTask> mQueue = new LinkedList<BaseTask>();

    private int mAsyncTaskStatus;

    public static final int ASYNC_TASK_STATUS_ON_PREEXECUTE = 0;

    public static final int ASYNC_TASK_STATUS_DO_IN_BACKGROUND = 1;

    public static final int ASYNC_TASK_STATUS_ON_POSTEXECUTE = 2;

    /**
     * Add task,if it already exist,just skip it.
     * 
     * @param object
     */
    public void add(BaseTask object) {
        if (!mQueue.contains(object)) {
            mQueue.add(object);
        }
    }

    public void setListener(TaskListener listener) {
        mListener = listener;
    }

    public int getAsyncTaskSatus() {
        return mAsyncTaskStatus;
    }

    public AccountAsyncTask(TaskListener taskListener) {
        // TODO Auto-generated constructor stub
        mListener = taskListener;
    }

    public Queue<BaseTask> getTaskQueue() {
        return mQueue;
    }

    @Override
    protected String doInBackground(BaseTask... params) {
        mAsyncTaskStatus = ASYNC_TASK_STATUS_DO_IN_BACKGROUND;
        params[0].run();

        return null;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        mAsyncTaskStatus = ASYNC_TASK_STATUS_ON_PREEXECUTE;
        if (null != mListener) {
            mListener.onTaskStart();
        }

        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        if (null != mListener) {
            mListener.onTaskProgressUpdate(values);
        }

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        mAsyncTaskStatus = ASYNC_TASK_STATUS_ON_POSTEXECUTE;
        if (null != mListener) {
            mListener.OnTaskFinished(result);
        }

        super.onPostExecute(result);
    }

    /**
     * The task listener
     * 
     * @author pjq0274
     */
    public interface TaskListener {
        void onTaskStart();

        void onTaskProgressUpdate(Integer... values);

        void OnTaskFinished(String result);
    }
}
