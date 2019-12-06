package com.example.yy.fast_json.fast_json;

import android.support.annotation.NonNull;

public class Father {
    private String name;
    private int age;
    private Child child;

    Father(String name,int age,Child child){
        this.name = name;
        this.age = age;
        this.child = child;
    }

    Father(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }


    @NonNull
    @Override
    public String toString() {
        return "Father:[name:" + name + " age:" + age + " child:" + child + "]";
    }
}
