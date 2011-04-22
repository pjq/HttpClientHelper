
package com.arcsoft.IShare.account;

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

        // After done,send notify.
        sendNotify();
    }
}
