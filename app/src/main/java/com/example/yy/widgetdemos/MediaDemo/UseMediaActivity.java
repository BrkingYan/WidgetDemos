package com.example.yy.widgetdemos.MediaDemo;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yy.widgetdemos.IntentDemo.BasicActivity;
import com.example.yy.widgetdemos.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UseMediaActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "media";

    private ImageView photoView;
    private Uri imageUri;

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_media);

        //显示通知
        Button noticeBtn = findViewById(R.id.show_notice_btn);
        noticeBtn.setOnClickListener(this);

        //启动摄像头
        Button shootBtn = findViewById(R.id.take_photo_btn);
        shootBtn.setOnClickListener(this);

        //访问相册
        Button choseAlbumBtn = findViewById(R.id.choose_photo);
        choseAlbumBtn.setOnClickListener(this);

        //显示图片
        photoView = findViewById(R.id.photo_view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_notice_btn:
                //利用PendingIntent使得点击通知会响应
                Intent intent = new Intent(this,BasicActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);

                //创建并设置通知
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notice = new Notification.Builder(this)
                                      .setContentTitle("this is a notice")
                                      .setContentText("go to basic activity")
                                      .setWhen(System.currentTimeMillis())
                                      .setSmallIcon(R.mipmap.ic_launcher)
                                      .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                                      .setLights(Color.BLUE,1000,1000)//亮灯颜色
                                      .setContentIntent(pi)//点击通知后跳转
                                      .build();
                //该通知的id 为1
                manager.notify(1,notice);
                break;
            case R.id.take_photo_btn:
                //指定拍照生成的图片名
                File outImage = new File(getExternalCacheDir(),"out_image.jpg");///sdcard/Android/data/<package name>/cache
                try{
                    if (outImage.exists()){
                        outImage.delete();
                    }
                    outImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //指定照片Uri，使其可以用FileProvider获取
                imageUri = FileProvider.getUriForFile(UseMediaActivity.this,"com.example.yy.photoprovider",outImage);

                Log.d(TAG,"**************** imageUri's scheme: " + imageUri.getScheme());
                Log.d(TAG,"**************** imageUri's authority: " + imageUri.getAuthority());
                Log.d(TAG,"**************** imageUri's fragment: " + imageUri.getFragment());

                Uri testUri = Uri.parse("www.baidu.com");
                Log.d(TAG,"**************** testUri's scheme: " + testUri.getScheme());
                Log.d(TAG,"**************** testUri's authority: " + testUri.getAuthority());
                Log.d(TAG,"**************** testUri's fragment: " + testUri.getFragment());

                //启动相机
                Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent1,TAKE_PHOTO);
                break;
            case R.id.choose_photo:
                //运行时权限处理
                //没权限就申请权限
                if (ContextCompat.checkSelfPermission(UseMediaActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(UseMediaActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    //有权限就直接打开相册
                    openAlbum();
                }
                break;
        }
    }

    /*
     *  用于对intent携带信息的回调
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        //用ContentResolver将uri读取成流，并通过BitmapFactory解码为Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        photoView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统用该方法
                        handleImageOnKitKat(data);
                    }else {
                        //4.4以下
                        handleImageBeforeKitKat(data);
                    }
                }
        }
    }

    /*
    *  打开相册
    * */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    /*
    *  用于对运行时权限的回调
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /*
    *  处理照片路径
    * */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri，就通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);

            Log.d(TAG,"******************uri ID is: " + docId);
            Log.d(TAG,"******************photo uri's authority is: " + uri.getAuthority());
            Log.d(TAG,"******************uri scheme is: " + uri.getScheme());//content

            /*
            * android中的scheme是一种页面内跳转协议
            * */

            //authority is com.android.providers.media.documents
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;

                Log.d(TAG,"selection is: " + selection);

                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

                Log.d(TAG,"imagePath is: " + imagePath);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }else if ("content".equalsIgnoreCase(uri.getScheme())){
                //如果是content类型的uri，则使用普通方式处理
                imagePath = getImagePath(uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())){
                //如果是file类型的uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
        }
        displayImage(imagePath);
    }

    /*
    * 处理图片路径
    * */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    /*
    * 显示图片
    * */
    private void displayImage(String imagePath) {
        if (imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            photoView.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * 根据Uri获取图片路径
    * */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection获取真实图片路径
        Log.d(TAG,"************************* uri: " + uri.getAuthority());//这里用到的authority是media

        //这里用到了ContentResolver去调用底层的ContentProvider去执行query()方法，返回一个Cursor对象
        //然后利用Cursor对象去读取data/data/com.android.media/databases中数据库表images里面的_data字段
        //_data字段保存的是照片的path/fileName
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
