package com.example.yy.service.ServiceDemo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * intent service可以处理耗时操作，因为是在其他线程中执行的
 *  在context中startService之后，会fork一个子线程在执行onHandleIntent中的逻辑，执行完后直接onDestroy()
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "intentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    /*
    *  相当于new Thread()中的run()方法，执行完就没了，并马上执行onDestroy()
    * */
    @Override
    protected void onHandleIntent(Intent intent) {
        //打印当前线程id
        Log.d(TAG,"onHandleIntent():intent service thread: " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy():intent service destroyed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate()");
    }
}
