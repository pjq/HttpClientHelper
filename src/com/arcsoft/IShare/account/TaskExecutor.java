
package com.arcsoft.IShare.account;

import android.util.Log;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The task executor,if you don't need it anymore,MUST call
 * {@link #stopExecutorThread()} to stop it.
 * 
 * @author pjq0274@arcsoft.com
 * @date 2011-4-22
 */

public class TaskExecutor {
    public static final String TAG = TaskExecutor.class.getSimpleName();

    private ExecutorService mExecutorService;

    private PriorityQueue<BaseTask> mPriorityQueue;

    private Object mLock = new Object();

    private boolean mStopExecutorThread = false;

    private Thread mExecutorThread = null;

    // private Thread mExecutorThread = new Thread(new Runnable() {
    // @Override
    // public void run() {
    // // TODO Auto-generated method stub
    // while (!mStopExecutorThread) {
    // // Run the task of the Queue.
    // synchronized (mLock) {
    // BaseTask task = null;
    // while (null != (task = mPriorityQueue.poll())) {
    // }
    // mExecutorService.execute(task);
    // }
    //
    // try {
    // Thread.sleep(500);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // }
    // });

    class ExecutorThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (!mStopExecutorThread) {
                // Run the task of the Queue.
                synchronized (mLock) {
                    BaseTask task = null;
                    while (null != (task = mPriorityQueue.poll())) {
                        mExecutorService.execute(task);
                    }
                }

                try {
                    Log.i(TAG, "Sleep for 500ms");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public TaskExecutor() {
        // TODO Auto-generated constructor stub
        mExecutorThread = new ExecutorThread();
        mExecutorService = Executors.newFixedThreadPool(5);
        mPriorityQueue = new PriorityQueue<BaseTask>();
        mExecutorThread.start();
    }

    /**
     * Add an new {@link BaseTask} to the queue.
     * 
     * @param task
     * @return true if add success,or false if it already exist the same task.
     */
    public boolean addTask(BaseTask task) {
        boolean result = true;
        synchronized (mLock) {
            if (!exist(task)) {
                mPriorityQueue.add(task);
                result = true;
            } else {
                result = false;
            }
        }

        return result;
    }

    /**
     * Check the task already exist in the queue.
     * 
     * @param task
     * @return true if the Quese already exist the same task.
     */
    protected boolean exist(BaseTask task) {
        // TODO Auto-generated method stub
        boolean isExist = false;

        synchronized (mLock) {
            Iterator<BaseTask> iterator = mPriorityQueue.iterator();
            while (iterator.hasNext()) {
                BaseTask baseTask = iterator.next();
                if (baseTask.equals(task)) {
                    isExist = true;
                    break;
                }
            }
        }

        return isExist;
    }

    /**
     * Cancel the task.
     * 
     * @param task
     * @return true if cancel it success,or false if it doesn't exist the task.
     */
    public boolean cancel(BaseTask task) {
        boolean result = false;
        synchronized (mLock) {
            Iterator<BaseTask> iterator = mPriorityQueue.iterator();
            while (iterator.hasNext()) {
                BaseTask baseTask = iterator.next();
                if (baseTask.equals(task)) {
                    baseTask.cancel(true);
                    mPriorityQueue.remove(baseTask);
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * Stop the executor thread.
     */
    public void stopExecutorThread() {
        mStopExecutorThread = true;
        // Shutdown
        mExecutorService.shutdownNow();
    }

}
