package com.example.yy.db_flow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MyDatabase.class)
public class Student extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String name;

    @Column
    public int age;

    @Column
    public long mom_id;


}
