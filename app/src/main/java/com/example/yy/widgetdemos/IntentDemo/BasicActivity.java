package com.example.yy.widgetdemos.IntentDemo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yy.widgetdemos.R;

public class BasicActivity extends AppCompatActivity {

    private Button finishBtn;//结束当前Activity
    private Button gotoBtn;//跳转到子Activity
    private Button visitBaiduBtn;//访问百度主页

    private TextView msgFromSb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        finishBtn = findViewById(R.id.finish_btn);
        gotoBtn = findViewById(R.id.goto_btn);
        msgFromSb = findViewById(R.id.data_from_sub);
        visitBaiduBtn = findViewById(R.id.visit_baidu_btn);
        registerListener();
    }

    /*
    * 从子Activity返回消息时需要重写的方法
    * 该重载方法用来接收来自子Activity利用intent回调的返回值
    * requestCode: BasicActivity启动SubActivity时传入的请求码
    * resultCode: SubActivity返回数据时传入的处理结果
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String msgFromSub = data.getStringExtra("data_return_tag");
                    Log.d("Basic","msg echoed from sub: " + msgFromSub);
                    msgFromSb.setText("msg echoed from sub: " + msgFromSub);
                }
                break;
            default:
        }
    }


    /*
    *  注册监听器
    * */
    private void registerListener(){
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 使用显式intent启动子Activity
                * */
                /*Intent intent = new Intent(BasicActivity.this,SubActivity.class);
                startActivity(intent);*/

                /*
                 * 使用隐式intent启动子Activity  方法一
                 * */
                /*Intent intent1 = new Intent("com.example.yy.ACTION_START_SUB");
                intent1.addCategory("com.example.yy.MY_CATEGORY");
                startActivity(intent1);*/

                /*
                 * 使用隐式intent启动子Activity  方法二(显示网页信息)
                 * */
                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                intent.putExtra("extra","subActivity");
                startActivity(intent);*/

                /*
                * 带返回值的显式intent
                * */
                /*Intent intent = new Intent(BasicActivity.this,SubActivity.class);
                intent.putExtra("data_tag","hello sub activity");
                startActivityForResult(intent,1);*/


                /*
                 * 带返回值的隐式intent
                 * */
                Intent intent = new Intent("com.example.yy.ACTION_START_SUB");
                intent.addCategory("com.example.yy.MY_CATEGORY");//这句话可有可无，因为AndroidManifest.xml中的SubActivity定义了两种Category
                intent.putExtra("data_tag","hello sub activity,this is yin shi intent");
                startActivityForResult(intent,1);

            }
        });

        visitBaiduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  访问百度的两种方式
                 * */
                //1、通过系统浏览器或者webView去访问，这取决于WebPageActivity的实现，
                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);*/

                //2、通过自定义的intent action去访问，不过这时展示的是WebPageActivity
                /*Intent intent = new Intent("com.example.yy.openURL",Uri.parse("http://www.baidu.com"));
                startActivity(intent);*/

            }
        });
    }

    /*
    *  添加 menu
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    /*
    *  menu 中的选项被按下的回应
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(BasicActivity.this,"add item",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(BasicActivity.this,"remove item",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
