package com.example.yy.db_flow;

import android.app.Application;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class MyApplication extends Application {

    private static final String TAG = "dbflow";

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);// 初始化DBFlow


        /*
        * insert
        * */
        /*Person person1 = new Person();
        person1.name = "Bob";
        person1.age = 25;
        person1.save();*/

        /*
        * query
        * */
        //单个查找
        /*Person person2 = SQLite.select()
                .from(Person.class)
                .where(Person_Table.id.eq(1L))
                .querySingle();
        if(person2 != null){
            Log.d(TAG,person2.toString());
        }*/


        //范围查找
        /*List<Person> persons= SQLite.select()
                .from(Person.class)
                .where(Person_Table.name.isNotNull(), Person_Table.age.greaterThanOrEq(18))// 这里的条件也可以多个
                .orderBy(Person_Table.id, true)// 按照 id 升序
                .limit(3)// 限制 3 条
                .queryList();// 返回的 list 不为 null，但是可能为 empty
        Log.d(TAG,persons.toString());*/

        /*
        * update
        * */
        // 第一种 先查后改
        /*Person person3 = SQLite.select()
                .from(Person.class)
                .where(Person_Table.id.eq(1L))
                .querySingle();// 区别与 queryList()
        if (person3 != null) {
            person3.name = "P0000";
            person3.update();
        }*/

        // 第二种 直接sql
        /*SQLite.update(Person.class)
                .set(Person_Table.name.eq("Mike"))
                .where(Person_Table.id.eq(1L))
                .execute();*/

        /*
        * delete
        * */
        // 第一种 先查后删
        /*Person person4 = SQLite.select()
                .from(Person.class)
                .where(Person_Table.id.eq(3L))
                .querySingle();
        if (person4 != null) {
            person4.delete();
        }*/
        // 第二种 直接sql
        /*SQLite.delete(Person.class)
                .where(Person_Table.id.eq(2L))
                .execute();*/
    }
}
