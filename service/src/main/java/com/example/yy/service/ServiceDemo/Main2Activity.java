package com.example.yy.service.ServiceDemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yy.service.R;

import java.awt.font.TextAttribute;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private MyService.DownloadBinder mDownloadBinder;

    /*
    *
    * */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mDownloadBinder = (MyService.DownloadBinder)binder;
            mDownloadBinder.startDownload();
            mDownloadBinder.getProgress();
            Log.d("myservice","onServiceConnected()");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("myservice","onServiceDisConnected()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //开关服务
        Button startServiceBtn = findViewById(R.id.start_service_btn);
        Button stopServiceBtn = findViewById(R.id.stop_service_btn);
        startServiceBtn.setOnClickListener(this);
        stopServiceBtn.setOnClickListener(this);

        //绑定服务
        Button bindBtn = findViewById(R.id.bind_btn);
        Button unbindBtn = findViewById(R.id.unbind_btn);
        bindBtn.setOnClickListener(this);
        unbindBtn.setOnClickListener(this);

        //开启intent service
        Button startIntentBtn = findViewById(R.id.start_intent_btn);
        startIntentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service_btn:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);//启动服务
                break;
            case R.id.stop_service_btn:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_btn:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);//绑定服务和活动
                break;
            case R.id.unbind_btn:
                unbindService(connection);//解绑服务
                break;
            case R.id.start_intent_btn:
                Log.d("intentService","activity thread: " + Thread.currentThread().getId());
                Intent intentService = new Intent(this,MyIntentService.class);
                startService(intentService);//启动intent 服务
                break;
            default:
                break;
        }
    }
}
