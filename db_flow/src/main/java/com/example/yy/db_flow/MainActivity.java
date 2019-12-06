package com.example.yy.db_flow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Student student = new Student();
        student.name = "Mike";
        student.age = 14;
        student.save();*/

        Student student = new Student();
        student.name = "Bob";
        student.age = 22;
        student.mom_id = 2;
        student.save();


    }
}
