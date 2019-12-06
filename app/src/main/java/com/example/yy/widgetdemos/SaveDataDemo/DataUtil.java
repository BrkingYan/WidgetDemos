package com.example.yy.widgetdemos.SaveDataDemo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataUtil {

    /*
    * 该方法可以利用指定context，将数据保存到指定文件中
    * */
    public static void saveDataToFile(Context context,String fileName, String data){
        FileOutputStream outputStream = null;
        BufferedWriter writer = null;

        try {
            outputStream = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 该方法可以利用指定context，从指定文件中读取数据
     * */
    public static String readDataFromFile(Context context,String fileName){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
