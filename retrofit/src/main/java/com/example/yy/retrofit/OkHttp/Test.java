package com.example.yy.retrofit.OkHttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class Test {
    public static void main(String[] args){
        //System.out.println(OkHttpUtils.httpGet("http://www.baidu.com"));

        OkHttpUtils.httpGetWithCallback("http://www.baidu.com", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }
}
