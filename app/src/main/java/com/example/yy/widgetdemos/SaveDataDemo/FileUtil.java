package com.example.yy.widgetdemos.SaveDataDemo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> showFiles(String dirName){
        List<String> list = new ArrayList<>();
        File curDir = new File(dirName);
        File[] files = curDir.listFiles();
        for (File f : files){
            if (f.getName().endsWith(".db")){
                list.add(f.getName());
            }
        }
        return list;
    }
}
