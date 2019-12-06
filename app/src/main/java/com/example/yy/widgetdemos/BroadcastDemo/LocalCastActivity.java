package com.example.yy.widgetdemos.BroadcastDemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.autofill.TextValueSanitizer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yy.widgetdemos.R;

public class LocalCastActivity extends AppCompatActivity {

    private IntentFilter mIntentFilter;
    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_cast);

        //创建LocalBroadcastManager
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        Button button = findViewById(R.id.broadcast_btn2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.yy.LOCAL_BROADCAST");
                intent.putExtra("local_cast_tag","local cast's msg");
                mLocalBroadcastManager.sendBroadcast(intent);
            }
        });

        //创建IntentFilter
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.example.yy.LOCAL_BROADCAST");
        //创建LocalReceiver
        mLocalReceiver = new LocalReceiver();

        //利用BoradcastManager注册的Receiver是本地Receiver
        mLocalBroadcastManager.registerReceiver(mLocalReceiver,mIntentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销Receiver
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("local_cast_tag");
            Toast.makeText(context,"received local broadcast: " + msg,Toast.LENGTH_SHORT).show();
        }
    }
}
