package com.kubsu.plumberapp;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

class FileHolder {

    public  static void writeLoginPasswordToFile(Context context,String login, String password){

        String stringToWrite=login+"\n"+password;

        try{
            FileOutputStream fileHolder=context.openFileOutput("LoginPasswordDetails.txt", Context.MODE_PRIVATE);
            fileHolder.write(stringToWrite.getBytes());
            fileHolder.close();
            //Toast.makeText(context, "Text saved", Toast.LENGTH_SHORT).show();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String[] getLoginPasswordFromFile(Context context){

        String result[]=new String[2];

        try{
            FileInputStream fileHolder=context.openFileInput("LoginPasswordDetails.txt");
            InputStreamReader reader=new InputStreamReader(fileHolder);
            BufferedReader bufferedReader=new BufferedReader(reader);

            String oneLine;
            int i=0;
            while ((oneLine=bufferedReader.readLine())!=null){
                result[i]=oneLine;
                i++;
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void clearFile(Context context){

        try{
            FileOutputStream fileHolder=context.openFileOutput("LoginPasswordDetails.txt", Context.MODE_PRIVATE);
            fileHolder.write("".getBytes());
            fileHolder.close();
           // Toast.makeText(context, "Text deleted", Toast.LENGTH_SHORT).show();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean emptyFile(Context context) throws IOException {
        FileInputStream fileHolder=context.openFileInput("LoginPasswordDetails.txt");
        InputStreamReader reader=new InputStreamReader(fileHolder);
        BufferedReader bufferedReader=new BufferedReader(reader);

        String emptyLine=bufferedReader.readLine();
        if (emptyLine==""){
            return true;
        }
        else{
            return false;
        }
    }
}
