package com.example.sowmya.musictranscription;

import java.io.File;

/**
 * Created by sowmya on 5/5/17.
 */
public class FileManager {
    public boolean create(String path){
        File file = new File(path);
        if(file.exists()) {
            return file.delete() && file.mkdir();
        }
        else{
            return file.mkdir();
        }
    }
    public boolean delete(String path){
        File file = new File(path);
        return file.delete();

    }
    public boolean isExists(String path){
        File file = new File(path);
        return  file.exists();
    }
}
