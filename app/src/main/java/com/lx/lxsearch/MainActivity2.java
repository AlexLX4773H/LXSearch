package com.lx.lxsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    public static String out00 = "";
    public static int counter00 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);

        TextView textViewOut1 = (TextView) findViewById(R.id.textView4);
        File root02 = new File(Environment.getExternalStorageDirectory() + File.separator + "LXSearch" + File.separator + "List.txt");
        if (root02.exists())
        {
            try {
                Scanner sc = new Scanner(root02);
                textViewOut1.setText(sc.nextLine());
                sc.close();
            }
            catch (Exception e){
                Log.e("MYAPP", "exception", e);
            }
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                out00 = "";
                counter00 = 0;
                TextView textin = (TextView) findViewById(R.id.tosearch01);
               searchFolder(textin.getText().toString().toLowerCase());

                break;
        }
    }

    public void searchFolder(String sss){

        try {
            File root03 = new File(Environment.getExternalStorageDirectory() + File.separator + "LXSearch" + File.separator + "List.txt");
            Scanner sc = new Scanner(root03);
            String temp = sc.nextLine();
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] data00 = data.split(" ::: ");
                if (data00[0].contains(sss)){
                    out00 += data00[1] +"\n";
                    ++counter00;
                }
            }
            sc.close();

            TextView textss = (TextView) findViewById(R.id.textView4);
            textss.setText(out00);

        } catch (FileNotFoundException e) {
            Log.e("MYAPP", "exception", e);
        }
        TextView textss = (TextView) findViewById(R.id.textViewss01);
        textss.setText(out00);

        TextView textss02 = (TextView) findViewById(R.id.textViewss02);
        textss02.setText(" "+counter00);
    }

}