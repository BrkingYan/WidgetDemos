package com.example.yy.widgetdemos.SaveDataDemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yy.widgetdemos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SaveDataActivity extends AppCompatActivity {


    private static final String TAG = "save";

    private Map<String,Integer> DBMap = new HashMap<>();
    private static SQLiteDatabase currentDB;
    private static MyDatabaseHelper currentHelper;

    private Button createTableBtn;

    private EditText DBNameText;
    private Button createDBbtn;
    private Button dropDBbtn;

    private Button insertBtn;
    private Button updateBtn;


    private Button showTablesBtn;
    private Button showDbBtn;
    private TextView currentDBView;
    private TextView displayView;
    private TextView tablesView;
    private TextView dbsView;

    private EditText dropedTable;
    private Button dropTableBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);

        //输入数据库名
        DBNameText = findViewById(R.id.DB_name_text);

        //创建或者打开数据库
        createDBbtn = findViewById(R.id.create_db_btn);
        createDBbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String dbName = DBNameText.getText().toString();
                Log.d(TAG,"dbName: " + dbName);

                if (DBMap.containsKey(dbName)){
                    Toast.makeText(SaveDataActivity.this,"DB " + dbName + " already exists",Toast.LENGTH_SHORT).show();
                }
                currentHelper = new MyDatabaseHelper(SaveDataActivity.this,dbName,null,1);

                //currentDB = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.widgetdemos/databases/" + dbName,null);
                currentDBView.setText(dbName);
            }
        });

        //删除数据库
        dropDBbtn = findViewById(R.id.drop_db_btn);
        dropDBbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //建表sql
        createTableBtn = findViewById(R.id.create_table_btn);
        setSqlListener(createDBbtn,"type on the create sql");

        //插入数据sql
        insertBtn = findViewById(R.id.insert_data_btn);
        setSqlListener(insertBtn,"type on the insert sql");

        //更新数据sql
        updateBtn = findViewById(R.id.updata_data_btn);
        setSqlListener(updateBtn,"type on the update sql");

        //show all the tables
        showTablesBtn = findViewById(R.id.show_table_btn);

        showTablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDB = currentHelper.getReadableDatabase();
                Cursor cursor = currentDB.rawQuery("select name from sqlite_master where type='table'",null);
                List<String> tableList = new ArrayList<>();
                if (cursor.moveToFirst()){
                    do {
                        tableList.add(cursor.getString(0));
                    } while (cursor.moveToNext());
                }

                tablesView.setText(tableList.toString());
            }
        });

        //show all the dbs
        showDbBtn = findViewById(R.id.show_db_btn);

        showDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> dbs = FileUtil.showFiles("/data/data/com.example.yy.widgetdemos/databases");
                dbsView.setText(dbs.toString());
            }
        });

        //删除表
        dropedTable = findViewById(R.id.droped_table_text);

        dropTableBtn = findViewById(R.id.drop_table_btn);
        dropTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = dropedTable.getText().toString();
                currentDB = currentHelper.getWritableDatabase();
                currentDB.execSQL("drop table if exists " + tableName);
                Toast.makeText(SaveDataActivity.this,"drop table " + tableName,Toast.LENGTH_SHORT).show();
            }
        });

        //console view
        displayView = findViewById(R.id.display_view);
        //current db
        currentDBView = findViewById(R.id.current_db_view);
        //tables view
        tablesView = findViewById(R.id.tables_view);
        //db view
        dbsView = findViewById(R.id.dbs_view);
    }

    private void setSqlListener(View view, final String title){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消按钮
                final SQLDialog execDialog = new SQLDialog(SaveDataActivity.this);
                execDialog.setTitle(title);
                //点击屏幕外不消失
                execDialog.setCanceledOnTouchOutside(false);


                execDialog.setCancelButtonListener(new SQLDialog.ButtonOnClickListener() {
                    @Override
                    public void buttonOnClick() {
                        execDialog.dismiss();
                    }
                });
                //点击确认按钮
                currentDB = currentHelper.getWritableDatabase();
                execDialog.setConfirmButtonListener(new SQLDialog.ButtonOnClickListener() {
                    @Override
                    public void buttonOnClick() {
                        execDialog.execSQL(currentDB);
                        execDialog.dismiss();
                    }
                });
                execDialog.show();
            }
        });
    }

}
