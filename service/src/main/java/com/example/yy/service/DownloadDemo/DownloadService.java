package com.example.yy.service.DownloadDemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.yy.service.R;
import java.io.File;

public class DownloadService extends Service {

    //服务里面使用AsyncTask
    private DownloadTask mDownloadTask;

    private String downloadUrl;

    /*
    *  DownloadListener，该类用于反馈下载的进程或者状态
    *  下载中、下载成功、下载失败、下载暂停、下载取消 等状态
    * */
    private DownloadListener listener = new DownloadListener() {

        /*
        * 调用listener的onProgress()方法显示进度
        * */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("Downloading...",
                    progress));
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onSuccess() {
            mDownloadTask = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Success",
                    -1));
            Toast.makeText(DownloadService.this,"Download Success",
                    Toast.LENGTH_SHORT).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onFailed() {
            mDownloadTask = null;
            //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Failed",
                    -1));
            Toast.makeText(DownloadService.this,"Download Failed",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            mDownloadTask = null;
            Toast.makeText(DownloadService.this,"Paused",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_SHORT)
                    .show();
        }
    };

    //创建一个Binder对象
    private DownloadBinder mBinder = new DownloadBinder();

    //回调binder对象给Activity
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    /*
    *  自定义binder
    *  里面放一些方法，好让Activity获得binder对象之后，可以调用这些方法来操作Service
    * */
    class DownloadBinder extends Binder {
        //开始下载
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void startDownload(String url){
            if (mDownloadTask == null){
                downloadUrl = url;

                /*
                *  创建AsyncTask
                * */
                mDownloadTask = new DownloadTask(listener);
                //以String url为输入参数
                mDownloadTask.execute(downloadUrl);

                //让服务前台执行
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this,"Downloading...",Toast.LENGTH_SHORT)
                        .show();
            }
        }

        //暂停下载
        public void pauseDownload(){
            if (mDownloadTask != null){
                mDownloadTask.pauseDownload();
            }
        }

        //取消下载
        public void cancelDownload(){
            if (mDownloadTask != null){
                mDownloadTask.cancelDownload();
            }else {
                if (downloadUrl != null){
                    //取消下载时需将文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"Canceled",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //创建NotificationManager
    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    //创建带进度条的Notification
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification(String title, int progress){
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if (progress > 0){
            //当progress大于或等于0时才需显示下载进度
            builder.setContentText(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
}
