package com.example.yy.service.ServiceDemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.yy.service.R;

public class MyService extends Service {

    private static final String TAG = "myservice";

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d(TAG,"binder:start download");
        }

        public int getProgress(){
            Log.d(TAG,"binder:get progress");
            return 0;
        }
    }

    public MyService() {

    }

    /*
    *  context中执行bindService()之后，该方法回调
    * */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind()");
        return mBinder;//context通过该方法得到binder对象
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"service onCreate()");

        //将服务变为前台服务，前台服务不会被系统清理
        Intent intent = new Intent(this,Main2Activity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("content title")
                .setContentText("MyService is running")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
    }

    /*
    * 调用了context的startService()方法之后，该方法就会回调
    * 如果该服务之前没有创建过，就会执行onCreate()
    * 服务一开始就执行的逻辑
    * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    /*
    * 释放资源
    * stopService()或者stopSelf()可以让服务停止
    * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"service destroy");
    }
}
