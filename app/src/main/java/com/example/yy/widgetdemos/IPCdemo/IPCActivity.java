package com.example.yy.widgetdemos.IPCdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yy.widgetdemos.R;

public class IPCActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "IPC";

    private Button add_data_provider;
    private Button query_data_provider;
    private Button update_data_provider;
    private Button delete_data_provider;

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);

        Log.d(TAG,"WidgetDemos's IPCActivity onCreate()");

        add_data_provider = findViewById(R.id.add_data_provider);
        query_data_provider = findViewById(R.id.query_data_provider);
        update_data_provider = findViewById(R.id.update_data_provider);
        delete_data_provider = findViewById(R.id.delete_data_provider);

        add_data_provider.setOnClickListener(this);
        query_data_provider.setOnClickListener(this);
        update_data_provider.setOnClickListener(this);
        delete_data_provider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_data_provider:
                addDataWithProvider();
                break;
            case R.id.query_data_provider:
                Log.d(TAG,"query pushed");
                queryDataWithProvider();
                break;
            case R.id.update_data_provider:
                updateDataWithProvider();
                break;
            case R.id.delete_data_provider:
                deleteDataWithProvider();
                break;
        }
    }
    //插入数据
    private void addDataWithProvider(){
        Log.d(TAG,"IPCActivity's add()");

        //这里就用到了provider
        Uri uri = Uri.parse("content://com.example.yy.dbprovider/book");
        ContentValues values = new ContentValues();
        values.put("name","A Clash of Kings");
        values.put("author","George Martin");
        values.put("pages",1040);
        values.put("price",22.56);
        Uri newUri = getContentResolver().insert(uri,values);
        newId = newUri.getPathSegments().get(1);
    }

    //查询数据
    private void queryDataWithProvider(){
        Log.d(TAG,"IPCActivity's query(),current pid is:" + Process.myPid() + " application is:" + getApplication().getPackageName());
        Uri uri = Uri.parse("content://com.example.yy.dbprovider/book");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.d(TAG,name);
                Log.d(TAG,author);
                Log.d(TAG,pages + "");
                Log.d(TAG,price + "");
            }
        }
    }

    //更新数据
    private void updateDataWithProvider(){
        Log.d(TAG,"IPCActivity's update()");
        Uri uri = Uri.parse("content://com.example.yy.dbprovider/book/" + newId);
        ContentValues values = new ContentValues();
        values.put("name","A Storm of Swords");
        values.put("author","George Martin");
        values.put("pages",556);
        values.put("price",11.34);
        getContentResolver().update(uri,values,null,null);
    }

    //删除数据
    private void deleteDataWithProvider(){
        Log.d(TAG,"IPCActivity's delete()");
        Uri uri = Uri.parse("content://com.example.yy.dbprovider/book/" + newId);
        getContentResolver().delete(uri,null,null);
    }
}
