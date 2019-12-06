package com.example.yy.widgetdemos.BroadcastDemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String castMsg = intent.getStringExtra("broadcast_tag");
        Toast.makeText(context,"received broadcast: " + castMsg,Toast.LENGTH_SHORT).show();
    }
}
