package com.example.yy.widgetdemos.IntentDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yy.widgetdemos.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "http";

    private Button sendRequestBtn;
    private TextView reponseView;

    private StringBuilder response = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        reponseView = findViewById(R.id.http_reponse_view);
        sendRequestBtn = findViewById(R.id.http_request_btn);
        sendRequestBtn.setOnClickListener(this);
    }

    private void sendRequestWithHttpURLConnection() {
        //开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{

                    //只能使用https 不能使用http ?
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(800);
                    connection.setReadTimeout(800);
                    InputStream in = connection.getInputStream();
                    Log.d(TAG,"in:" + in);
                    //读取输入流
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null){
                        Log.d(TAG,"into while");
                        response.append(line);
                    }
                    Log.d(TAG,"response: " + response.toString());
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里更新UI
                reponseView.setText(s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        sendRequestWithHttpURLConnection();
    }
}
