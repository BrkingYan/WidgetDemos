package com.example.yy.widgetdemos.SaveDataDemo.Litepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yy.widgetdemos.R;
import com.example.yy.widgetdemos.SaveDataDemo.SQLDialog;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LitepalActivity extends AppCompatActivity {

    private static final String TAG = "litepal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);

        //create DB
        Button create_db_btn = findViewById(R.id.create_db_litepal_btn);
        create_db_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建数据库
                Connector.getDatabase();
            }
        });

        //insert data
        Button insertDataBtn = findViewById(R.id.insert_data_litepal_btn);
        insertDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("The Da Vinci Code");
                book.setAuthor("zz");
                book.setPages(454);
                book.setPrice(16.23);
                book.save();
            }
        });

        //update data
        Button updateBtn = findViewById(R.id.update_litepal_btn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("The Lost Symbol");
                book.setAuthor("zz1");
                book.setPages(510);
                book.setPrice(19.95);
                book.save();
                book.setPrice(10.66);
                book.save();
            }
        });

        //delete data
        Button deleteBtn = findViewById(R.id.delete_litepal_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.delete(Book.class,3);
                DataSupport.deleteAll(Book.class,"price < ?","17");
            }
        });

        //query data
        Button queryBtn = findViewById(R.id.select_litepal_btn);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = DataSupport.findAll(Book.class);
                for (Book book : books){
                    Log.d(TAG,"book name: " + book.getName());
                    Log.d(TAG,"book author: " + book.getAuthor());
                    Log.d(TAG,"book pages: " + book.getPages());
                    Log.d(TAG,"book price: " + book.getPrice());
                }

                Book firstBook = DataSupport.findFirst(Book.class);
                Log.d(TAG,"first item:" + firstBook);
                Book lastBook = DataSupport.findLast(Book.class);
                Log.d(TAG,"last item:" + lastBook);

                //分页查询
                List<Book> bookList = DataSupport.select("name","author").find(Book.class);
                Log.d(TAG,"分页查询: " + bookList.toString());
                //条件查询
                List<Book> list = DataSupport.where("pages > ?","400").find(Book.class);
                Log.d(TAG,"条件查询: " + list.toString());

                //分页+条件
                List<Book> list1 = DataSupport.select("name","author").where("pages > ?","400").find(Book.class);
                Log.d(TAG,"组合查询: " + list1.toString());

            }
        });
    }
}
