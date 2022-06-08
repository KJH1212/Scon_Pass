package com.example.sconpass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
//java.io.FileNotFoundException -> Manifest 에 android:requestLegacyExternalStorage="true" 추가

public class AdduserActivity extends AppCompatActivity {

    EditText e1, e2, e3;
    CardView joinButton, loginButton;
    View.OnClickListener cl;
    String request, name, phone, stay;
    String result;
    String[] array;
    ImageView profileImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        e1 = findViewById(R.id.editTextTextPersonName);
        e2 = findViewById(R.id.editTextTextPersonID);
        e3 = findViewById(R.id.editTextTextPersonStay);
        joinButton = findViewById(R.id.cardView_join);
        loginButton = findViewById(R.id.cardView_login);
        profileImg = findViewById(R.id.profileImage);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.profileImage:

                        break;

                    case R.id.cardView_login:
                        Log.d("AdduserActivity", "login button clicked");
                        loginFunc(view);
                        break;

                    case R.id.cardView_join:
                        Log.d("AdduserActivity", "join button clicked");
                        joinFunc(view);
                        break;
                }
            }
        };
        joinButton.setOnClickListener(cl);
        loginButton.setOnClickListener(cl);

    }

    //로그인 버튼 클릭 시,
    public void loginFunc(View view) {
        request = "/login";
        name = e1.getText().toString();
        phone = e2.getText().toString();
        Log.d("loginFunc",name+"/"+phone);
        if (name.isEmpty()) {
            e1.setError("Field cannot be empty");
        } else if (phone.isEmpty()) {
            e2.setError("Field cannot be empty");
        } else {
            // ** select data from DB:
            try {
                //Asynctask 를 실행할 때 넣어줄 인자(String(name), String(phone), String(stay))
                result = new androidToJsp(request).execute(name, phone, stay).get();
                Log.d("[Adduser]from andTojsp", result);
                array = new String[]{};

                switch (result){
                    case "READ FAILED":
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        array = result.split(",");
                        Log.d("result array", Arrays.toString(array));
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        //보낼 데이터 변경
                        i.putExtra("get_proc", request);
                        i.putExtra("get_name", array[0]);
                        i.putExtra("get_phone", array[1]);
                        i.putExtra("get_stay", array[2]);
                        startActivity(i);
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("[Adduser] login result", Arrays.toString(array));
        }


    }

    //회원가입 버튼 클릭 시,
    public void joinFunc(View view) {
        request = "/join";
        name = e1.getText().toString();
        phone = e2.getText().toString();
        stay = e3.getText().toString();
        Log.d("joinFunc",name+"/"+phone+"/"+stay);

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
                result = new androidToJsp(request).execute(name, phone, stay).get(); //JOIN
                Log.d("[Adduser]from andTojsp:", result);
                array = new String[]{};

                switch (result) {
                    case "CREATE FAILED":
                        Toast.makeText(this, "회원이 이미 존재합니다", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG).show();
                        loginFunc(view);
                }

                }

            catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("AdduserActivity", result);
        }

    }

}
