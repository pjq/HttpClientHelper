
package com.arcsoft.IShare.account;

/**
 * @author pjq0274@arcsoft.com
 * @date 2011-4-22
 */

public class SignInTask extends BaseTask {
    public static final String TAG = SignInTask.class.getSimpleName();

    public SignInTask() {
        // TODO Auto-generated constructor stub
        mTaskId = TASK_ACCOUNT_SIGN_IN;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        // Do SignIn

        // After done,send notify.
        sendNotify();
    }

}
