package com.example.sconpass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdduserActivity extends AppCompatActivity {

    EditText e1, e2, e3;
    CardView joinButton, loginButton;
    View.OnClickListener cl;
    String name, phone, stay;
    String result;
    String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        e1 = findViewById(R.id.editTextTextPersonName);
        e2 = findViewById(R.id.editTextTextPersonID);
        e3 = findViewById(R.id.editTextTextPersonStay);
        joinButton = findViewById(R.id.cardView_join);
        loginButton = findViewById(R.id.cardView_login);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId())
                {
                    case R.id.cardView_join:
                        joinFunc(view);
                        break;
                    case R.id.cardView_login:
                        loginFunc(view);
                        break;
                }
            }
        };
        joinButton.setOnClickListener(cl);
        loginButton.setOnClickListener(cl);
    }

    //회원가입 버튼 클릭 시,
    public void joinFunc(View view) {
        name = e1.getText().toString();
        phone = e2.getText().toString();
        stay = e3.getText().toString();

        if (name.isEmpty()) {
            e1.setError("Field cannot be empty");
        } else if (phone.isEmpty()) {
            e2.setError("Field cannot be empty");
        } else if (stay.isEmpty()) {
            e3.setError("Field cannot be empty");
        } else {
            // ** add to DB:
            try {
                result = new androidToJsp("join").execute(name, phone, stay).get();
                array = result.split(",");
                //name, phone, stay, uuid를 받아옴
                Log.d("result array",array.toString());
                //uuid값이 넘어왔다면 파일을 저장해야된다.
                Log.d("",array[3]);
                if(array[3]!=null) {
                    FileOutputStream fos = openFileOutput("userUuid.txt",MODE_PRIVATE);
                    DataOutputStream dos = new DataOutputStream(fos);
                    dos.writeUTF(array[3]);
                    dos.flush();
                    dos.close();
                }else{
                    Log.d("file write", "uuid 저장 실패");
                }
            } catch (ExecutionException | InterruptedException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("AdduserActivity", result);
        }
        //회원가입 로직
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        //보낼 데이터 변경
        i.putExtra("get_name", name);
        startActivity(i);
    }

    //로그인 버튼 클릭 시,
    public void loginFunc(View view) {
        name = e1.getText().toString();
        phone = e2.getText().toString();
        stay = e3.getText().toString();

        if (name.isEmpty()) {
            e1.setError("Field cannot be empty");
        } else if (phone.isEmpty()) {
            e2.setError("Field cannot be empty");
        } else if (stay.isEmpty()) {
            e3.setError("Field cannot be empty");
        } else {
            // ** add to DB:
            try {
                //Asynctask 를 실행할 때 넣어줄 인자(String(name), String(phone), String(stay))
                result = new androidToJsp("login").execute(name, phone, stay).get();
                array = result.split(",");
                Log.d("result array",array.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("AdduserActivity", result);
        }
        //로그인 로직
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        //보낼 데이터 변경
        i.putExtra("get_name", name);
        startActivity(i);
    }


}