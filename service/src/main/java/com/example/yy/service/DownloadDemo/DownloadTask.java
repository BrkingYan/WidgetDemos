package com.example.yy.service.DownloadDemo;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/*
*  doInBackground(String param) return -> onPostExecute(DownloadStatus status)
*                               调用publishProgress(Integer progress) -> onProgressUpdate(Integer progress) 更新UI
* */
public class DownloadTask extends AsyncTask<String,Integer,DownloadStatus> {

    // < Params , Progress , Result >
    // params是AsyncTask.execute()方法里的输入参数，此处为url，也是doInBackground()方法的输入参数
    // Progress 是阶段性的数据，此处为Integer，是doInBackground()的中间参数，也是publishProgress()方法的输入参数，onProgressUpdate()的输入参数
    // Result是运行结果，是doInBackground()方法的返回结果，也是onPostExecute()方法的输入参数

    private static final String TAG = "task";

    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    private DownloadListener listener;

    DownloadTask(DownloadListener listener){
        this.listener = listener;
    }

    /*
    * 该方法中的所有东西都是在子线程中执行，因此不能更新UI，只能用于计算
    * 如果要反馈当前计算进度，可以通过publishProgress()方法
    *
    * 该方法返回后，会触发onPostExecute()方法
    * 返回值是Result
    * */
    @Override
    protected DownloadStatus doInBackground(String... papams) {
        InputStream in = null;
        RandomAccessFile savedFile = null;
        File file = null;

        Log.d(TAG,"doInBackground()");
        for (String s : papams){
            Log.d(TAG,"params: " + s);
        }

        try {
            long downloadedLength = 0;//记录已下载的文件长度
            String downloadUrl = papams[0];
            Log.d(TAG,"download url: " + downloadUrl);
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            Log.d(TAG,"fileName: " + fileName);
            String directory = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .getPath();
            Log.d(TAG,"dir: " + directory);
            file = new File(directory + fileName);
            if (file.exists()){
                downloadedLength = file.length();//读取已下载的文件大小
                Log.d(TAG,"downloadedLength: " + downloadedLength);
            }

            long contentLength = getContentLength(downloadUrl);//待下载文件的总大小
            if (contentLength == 0){
                return DownloadStatus.FAILED;
            }else if (contentLength == downloadedLength){
                return DownloadStatus.SUCCESS;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点下载，指定从哪个字节开始下载
                    //断点续传，发送http请求时发送请求头Rnge：bytes=x-x  表示需要请求的范围。
            .addHeader("RANGE","bytes=" + downloadedLength + "-")
            .url(downloadUrl)
            .build();

            //建立Http连接
            Response response = client.newCall(request).execute();
            if (response != null){
                in = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);//跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = in.read(b)) != -1){
                    if (isCanceled){
                        return DownloadStatus.CANCELED;
                    }else if (isPaused){
                        return DownloadStatus.PAUSED;
                    }else {
                        total += len;
                        savedFile.write(b,0,len);//将数据写入文件
                        //计算已经下载的百分比
                        int progress = (int)((total + downloadedLength) * 100 / contentLength);

                        //该方法可以用来反馈计算进度，会触发onProgressUpdate()方法
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return DownloadStatus.SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null){
                    in.close();
                }
                if (savedFile != null){
                    savedFile.close();
                }
                //如果中途取消了，就删除之前的文件
                if (isCanceled && file != null){
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DownloadStatus.SUCCESS;
    }

    //获取待下载文件的大小长度
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()){
            //请求体长度
            long contentLength = response.body().contentLength();
            response.close();
            Log.d(TAG,"contentLength: " + contentLength);
            return contentLength;
        }
        return 0;
    }


    /*
    * 该方法在publishProgress()调用后执行，该方法可以更新UI，这里是利用DownloadListener回调主线程去更新UI
    * */
    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d(TAG,"onProgressUpdate()");

        //在这里更新下载进度
        int progress = values[0];//当前进度
        Log.d(TAG,"progress: " + progress);

        //增长的时候才通知
        if (progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    /*
    *  该方法在后台任务执行完后执行
    * */
    @Override
    protected void onPostExecute(DownloadStatus status) {
        Log.d(TAG,"onPostExecute()");
        Log.d(TAG,"status: " + status);

        switch (status){
            case SUCCESS:
                listener.onSuccess();
                break;
            case FAILED:
                listener.onFailed();
                break;
            case PAUSED:
                listener.onPaused();
                break;
            case CANCELED:
                listener.onCanceled();
            default:
                break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCanceled = true;
    }
}
