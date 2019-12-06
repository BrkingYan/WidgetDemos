package com.example.yy.retrofit.OkHttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yy.retrofit.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "okhttp";

    private Button request_btn;
    private Button parseXMLBtn;
    private Button parseJsonBtn;
    private TextView response_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        request_btn = findViewById(R.id.http_request_btn);
        request_btn.setOnClickListener(this);

        response_view = findViewById(R.id.http_reponse_view);

        parseXMLBtn = findViewById(R.id.parse_xml_btn);
        parseXMLBtn.setOnClickListener(this);

        parseJsonBtn = findViewById(R.id.parse_json_btn);
        parseJsonBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.http_request_btn:
                sendRequestWithOkhttp();
                break;
            case R.id.parse_xml_btn:
                parseXMLInTomcat();
                break;
            case R.id.parse_json_btn:
                requestJsonFile();
                break;

        }
    }

    /*
    *  利用okHttp发送http请求百度主页
    * */
    private void sendRequestWithOkhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://fy.iciba.com/ajax.php?a=fy&f=en&t=zh&w=hello%20world")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseText = response.body().string();
                    Log.d(TAG,responseText);
                    showResponse(responseText);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    * 在子线程中更新UI，可以用runOnUiThread()方法
    * */
    private void showResponse(final String responseText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                response_view.setText(responseText);
            }
        });
    }

    /*
    *  通过okHttp访问tomcat中的get_data.xml文件
    * */
    private void parseXMLInTomcat(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.100:8080/get_data.xml")
                            .build();
                    Response response = client.newCall(request).execute();
                    //获取响应消息
                    String responseData = response.body().string();
                    parseXMLWithPull(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    *  解析XML文件
    * */
    private void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:{
                        if ("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if ("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if ("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //某个节点完成解析
                    case XmlPullParser.END_TAG:{
                        if ("app".equals(nodeName)){
                            Log.d(TAG,"id is:" + id);
                            Log.d(TAG,"name is: " + name);
                            Log.d(TAG,"version is: " + version);
                        }
                        break;
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *  通用的利用OkHttp发送请求的方法
    * */
    public void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * @description 请求tomcat上的json文件
     * @param
     * @return void
     */
    private void requestJsonFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.100:8080/get_data.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    *  解析json字符串
    * */
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for (App app : appList){
            Log.d(TAG,"id is: " + app.getId());
            Log.d(TAG,"name is: " + app.getName());
            Log.d(TAG,"version is: " + app.getVersion());
        }
    }

}
