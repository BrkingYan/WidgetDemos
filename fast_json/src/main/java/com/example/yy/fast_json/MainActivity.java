package com.example.yy.fast_json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yy.fast_json.gson.Child;
import com.example.yy.fast_json.gson.Father;
import com.example.yy.fast_json.gson.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button jsonToObj = findViewById(R.id.json_to_obj);
        Button objToJson = findViewById(R.id.obj_to_json);
        Button jsonToList = findViewById(R.id.json_to_list);
        Button listToJson = findViewById(R.id.list_to_json);

        jsonToObj.setOnClickListener(this);
        objToJson.setOnClickListener(this);
        jsonToList.setOnClickListener(this);
        listToJson.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
