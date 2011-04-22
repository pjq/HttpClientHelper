
package com.arcsoft.IShare.account;

/**
 * @author pjq0274@arcsoft.com
 * @date 2011-4-22
 */

public abstract class BaseTask implements Runnable {
    public static final int TASK_ACCOUNT_REGISTER = 0;
    public static final int TASK_ACCOUNT_SIGN_IN = 1;
    public static final int TASK_ACCOUNT_REQ_INFO = 2;

    /**
     * The task description
     */
    String mTaskDesc;

    /**
     * The task id
     */
    int mTaskId;

    /**
     * Default the task is ok.
     */
    boolean mIsCanceled = false;

    /**
     * Send notify.
     */
    public void sendNotify() {
        if (mIsCanceled) {
            // Has been canceled.
        } else {
            // Normal handle
        }
    }

    /**
     * You can cancel it.But if it is already running,you can't real cancel
     * it.You can just send the different notify according to the status.
     * 
     * @param cancel
     */
    void cancel(boolean cancel) {
        mIsCanceled = cancel;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        boolean isEqual = true;

        BaseTask task = (BaseTask) o;

        if (task.mTaskId != mTaskId || !task.mTaskDesc.equals(mTaskDesc)) {
            isEqual = false;
        }

        return isEqual;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String string = "mTaskId=" + mTaskId + ",mTaskDesc=" + mTaskDesc;
        return string;
    }
}
