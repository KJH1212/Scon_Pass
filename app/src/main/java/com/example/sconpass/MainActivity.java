package com.example.sconpass;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    TextView tvDB;
    String proc, name, phone, stay;
    String requestQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDB = findViewById(R.id.textViewDB);

        Intent i = getIntent();
        proc = i.getExtras().getString("get_proc");
        name = i.getExtras().getString("get_name");
        phone = i.getExtras().getString("get_phone");
        stay = i.getExtras().getString("get_stay");
        Log.i("[Main] intent로 넘어온 값 : ", proc+" / "+name+" / "+phone+" / "+stay);

        tvDB.setText(name+"님 안녕하세요 =)\n" +
                "번호 : "+phone+"\n"+
                "주소 : "+stay
        );

        if(proc == "/join"){
            try {
                //파일에 저장한 uuid 읽어오기
                FileInputStream fis = openFileInput("userUuid.txt");
                DataInputStream dis = new DataInputStream(fis);
                String uuid = dis.readUTF();
                dis.close();
                //utilFun();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}