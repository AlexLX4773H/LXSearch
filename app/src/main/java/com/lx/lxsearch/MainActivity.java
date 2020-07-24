package com.lx.lxsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String folders01="";
    public static String files01="";
    public static int no01 = 0;
    public static String output01 ="";
    public static int count01 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);

        Button create1 = findViewById(R.id.createbutton);
        create1.setOnClickListener(this);

        TextView textViewOut = (TextView) findViewById(R.id.textView);
        File root01 = new File(Environment.getExternalStorageDirectory() + File.separator + "LXSearch" + File.separator + "SetLocations.txt");
        if (root01.exists())
        {
            try {
                Scanner sc = new Scanner(root01);
                String the = "";
                the += sc.nextLine() + "\n";
                int x=0;
                while (sc.hasNextLine()) {
                    String data = sc.nextLine();
                    if(data.contains(":::")){
                        ++x;
                        continue;
                    }
                    else{
                        if (x == 0){
                            folders01 += data + "\n";
                        }
                        else {
                            files01 += data + "\n";
                        }
                    }
                    the += data + "\n";
                }
                textViewOut.setText(the);
                sc.close();

                TextView textView01 = (TextView) findViewById(R.id.text01f);
                TextView textView02 = (TextView) findViewById(R.id.text02f);
                textView01.setText(folders01);
                textView02.setText(files01);
            }
            catch (Exception e){
                Log.e("MYAPP", "exception", e);
            }
        }

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createbutton:
                requestForPermission();
                main01();
                break;
            case R.id.button2:
                Intent intent01 = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent01);
                break;
        }
    }

    public void main01(){
        TextView textView01 = (TextView) findViewById(R.id.text01f);
        TextView textView02 = (TextView) findViewById(R.id.text02f);

        String f01 = textView01.getText().toString();
        String f02 = textView02.getText().toString();

 //       String f01 = Environment.getExternalStorageDirectory().toString();
 //       String f02 = Environment.getExternalStorageDirectory().toString();

        String[] f01split = f01.split("\n");
        String[] f02split = f02.split("\n");

        if (f01.contains("/") && f02.contains("/")){
            no01 = f01split.length + f02split.length;
        }
        else{
            if (f01.contains("/")){
                no01 = f01split.length;
            }
            if (f02.contains("/")){
                no01 = f02split.length;
            }
        }

        File root = new File(Environment.getExternalStorageDirectory() + File.separator + "LXSearch");
        if (!root.exists())
        {
            root.mkdirs();
        }

        try {
            FileWriter fw = new FileWriter(root.getAbsolutePath() + File.separator + "SetLocations.txt");
            fw.write(no01+"\n");
            fw.append(f01);
            fw.append("\n ::: \n");
            fw.append(f02);
            fw.close();
        }
        catch (Exception e){
            Log.e("MYAPP", "exception", e);
        }

            for (String str : f01split){
                File f1 = new File(str);
                recursive(f1);
            }
      /*      for (String str : f02split){
                File f1 = new File(str);
                recursive(f1);
            }*/

        try {
            FileWriter fw = new FileWriter(root.getAbsolutePath() + File.separator + "List.txt");
            fw.write(count01+"\n");
            fw.append(output01);
            fw.close();
        }
        catch (Exception e){
            Log.e("MYAPP", "exception", e);
        }

    }

    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public final int EXTERNAL_REQUEST = 138;

    // requestForPermission();

    public boolean requestForPermission() {

        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }

        return isPermissionOn;
    }

    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));

    }

    private void recursive(File path){

        try {
            for(File file: path.listFiles()){
                if(file.isDirectory()){
                    output01 += file.getName().toLowerCase() +" ::: "+ file.getAbsolutePath() +"\n";
                    ++count01;
                    recursive(file);
                }
                else{

                }
            }
        }
        catch (Exception e){
            Log.e("MYAPP", "exception", e);
        }

    }
}