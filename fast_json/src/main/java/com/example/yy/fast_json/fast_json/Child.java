package com.example.yy.fast_json.fast_json;

import android.support.annotation.NonNull;

public class Child{
    private String name;
    private int age;
    private int grade;

    Child(String name,int age,int grade){
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    Child(){}

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @NonNull
    @Override
    public String toString() {
        return "Child:[name:" + name + " age:" + age + " grade:" + grade + "]";
    }
}
