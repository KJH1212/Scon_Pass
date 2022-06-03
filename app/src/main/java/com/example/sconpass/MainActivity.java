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
    String getName;
    String requestQuery;

//    public static final String DATABASE_NAME = "TEST";
//    public static final String TABLE_NAME = "user";
//    public static final String url = "jdbc:mysql://3.38.126.220:3306/" + DATABASE_NAME;
//    public static final String username = "root", password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDB = findViewById(R.id.textViewDB);

        Intent i = getIntent();
        getName = i.getExtras().getString("get_name");
        Log.i("MainActivity", "intent로 넘어온 값 : "+getName);

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
//*** utilFunc() -> AdduserActivity.java에서 넘어온 값으로 안녕하세요 ~ 변경
    //DB에서 값 가져와 화면에 출력
//    public void utilFun() throws SQLException {
//        new Thread(() -> {
//            //do your work
//            Log.i("MainActivity", "utilFun() 1");
//            StringBuilder records = new StringBuilder();
//            try {
//                Log.i("ShowActivity", "utilFun() 2");
//                Class.forName("com.mysql.jdbc.Driver");
//                Connection connection = DriverManager.getConnection(url, username, password);
//                Log.i("MainActivity", connection+"");
//                Statement statement = connection.createStatement();
//                Log.i("MainActivity", "Connecting");
//                requestQuery = "SELECT * FROM " + TABLE_NAME+" WHERE name = '"+getName+"'";
//                Log.i("MainActivity", "DB에 보낼 SQL : "+ requestQuery);
//                ResultSet rs = statement.executeQuery(requestQuery);
//
//                while (rs.next()) {
//                    records.append("안녕하세요 ").append(rs.getString(1)).append("님\n동: ").append(rs.getString(3)).append("동\n");
//                }
//
//                connection.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    // after the job is finished:
//                    tvDB.setText(records.toString());
//                }
//            });
//        }).start();
//
//
//    }


    //DB에 값을 삽입
//    public static void addTemp(String name_str, String phone_str, String stay_str) {
//        new Thread(() -> {
//            try {
//                Log.i("MainActivity", name_str+", "+phone_str+", "+stay_str );
//                //insert / delete / update 쿼리를 보낼 때는 executeUpdate()를
//                //select 쿼리를 보낼 때는 executeQuery()를 사용하면 되겠습니다.
//                String query = String.format("INSERT INTO " + TABLE_NAME + " VALUES ('%s', '%s', '%s')", name_str, phone_str, stay_str);
//                Log.i("MainActivity", ""+query);
//                Class.forName("com.mysql.jdbc.Driver");
//                Connection connection = DriverManager.getConnection(url, username, password);
//                Log.i("ShowActivity", "addTemp DB connect 완료");
//                //Statement 객체 생성은 Connection 객체가 제공하는 createStatement() 메소드를 사용
//                Statement statement = connection.createStatement();
//                //(sql문 대기시킴)PreparedStatement 객체 생성은 Connection 객체가 제공하는 prepareStatement() 메소드를 사용
//                //PreparedSatement 객체 sql문 실행은 인자값 필요 x, Statement 객체는 인자값 필요
//                PreparedStatement preparedStatement = connection.prepareStatement(query);
//                //(sql문 실행)ResultSet executeQuery(query) -> excuteQuery()메소드가 반환하는 ResultSet은 select한 결과값을 가지고 있다.
//                //-----ResultSet rs = preparedStatement.executeQuery();
//                //(sql문 업데이트) int executeUpdate(query) -> excuteUpdate()메소드가 반환하는 숫자값은 sql문 실행 후 영향을 받은 레코드 갯수
//                int result = preparedStatement.executeUpdate();
//                Log.i("ShowActivity", "excuteUpdate값 : "+result+"");
//                connection.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

}