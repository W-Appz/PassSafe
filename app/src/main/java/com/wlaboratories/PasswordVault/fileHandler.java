package com.wlaboratories.PasswordVault;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class fileHandler {
    Context ct;
    public fileHandler(Context ct) {
        this.ct = ct;
    }
    public void writeToFile(String filename, String content) {
        File path = this.ct.getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, filename));
            writer.write(content.getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteCode(Context ct) {
        File path = this.ct.getFilesDir();
        try {
            File code = new File(path,"code.txt");
            File hint = new File(path,"hint.txt");
            code.delete();
            hint.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public String readFromFile(String filename) {
        File path = this.ct.getFilesDir();
        File readfrom = new File(path, filename);
        byte[] content = new byte[(int) readfrom.length()];
        try {
            FileInputStream stream = new FileInputStream(readfrom);
            stream.read(content);
            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public boolean fileExist(String fname){
        File file = this.ct.getFileStreamPath(fname);
        return file.exists();
    }
    public String readlineFromFile(String filename) {
        FileInputStream fis;
        try {
            fis = this.ct.openFileInput("hello.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != "\n" && (line = bufferedReader.readLine()) != null ) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } return "";
    }
}
