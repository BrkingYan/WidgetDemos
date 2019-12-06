package com.example.yy.widgetdemos.IntentDemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.yy.widgetdemos.R;

public class SubActivity extends AppCompatActivity {

    private static final String TAG = "SubActivity";
    private Button gotoBtn;

    private TextView msgFromBasic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        Log.d(TAG,"msg from parent: " + intent.getStringExtra("data_tag"));

        msgFromBasic = findViewById(R.id.data_from_basic);
        msgFromBasic.setText("msg from basic: " + intent.getStringExtra("data_tag"));
        /*
        *  back to parent activity
        * */
        gotoBtn = findViewById(R.id.echo_btn);
        gotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return_tag","hello basic activity,I give the data to you");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    /*
    * 该方法在用户点击手机的返回按钮的时候被调用
    * */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return_tag","hello basic act,user press the back, I still give the data to you");
        setResult(RESULT_OK,intent);
        finish();
    }
}
