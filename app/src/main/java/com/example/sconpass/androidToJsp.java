package com.example.sconpass;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class androidToJsp  extends AsyncTask<String, String, String> {
    //에뮬레이터에서는 localhost 말고 다른 주소로 넣어줘야 한다.  localhost가 아니라 10.0.2.2
    public static String IP ="10.0.2.2:8080"; //자신의 IP번호
    String SERVER_JSP_IP = "http://"+IP+"/sconpass00/jsp/androidToJsp.jsp"; // 연결할 jsp주소
    String requestProc, receiveData;
    String resultData="";

    androidToJsp(String requestProc){
        this.requestProc = requestProc;
        //sendMsg 값이 무엇인지 확인
        Log.d("요청 버튼 : ", requestProc);
    }

    @Override
    protected String doInBackground(String... strings){
        try {
            //(1) URL 정의
            URL url = new URL(SERVER_JSP_IP);
            //(2) URL 연결
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //(3) Request Header값 세팅하여 자원을 명시
            //content Type은 api 연동시에 보내는 자원을 명시하기 위해 보통 사용
            // application/json은 {key: value}의 형태로 전송되지만 application/x-www-form-urlencoded는 key=value&key=value의 형태로 전달된다
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
            //(4) 요청 방식 선택 (GET, POST) GET방식은 양이 많아지면 크기 제약이 있음
            conn.setRequestMethod("POST");
            //(5) 서버로부터 입력/출력 가능하도록 설정
            //이 두 부분이 활성화되면 클라이언트와 서버 사이에 연결된 스트림을 열어 버리기 때문에 POST 방식으로 자동 전환됨.
            conn.setDoInput(true); //GET 방식일 경우 지우기
            conn.setDoOutput(true); //GET 방식일 경우 지우기

            //-------------------1. JSP에 데이터 전달 --------------------//
            //(6)  key=value&key=value 형태로 만들기
            receiveData = "PROC="+requestProc+ "&NAME="+strings[0]+"&PHONE="+ strings[1] + "&STAY="+strings[2];
            Log.d("입력된 데이터 : ", receiveData);

            //(7) 문자에서 바이트로 이어주는 다리역할을 하는 OutputStreamWriter사용하여 Request Body에 data를 담기 위한 객체 생성.
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
            //(8) Request Body에 data를 담음
            outputStreamWriter.write(receiveData);
            //(9) Request Body에 data를 보냄
            outputStreamWriter.flush();//
            //(10) 출력 스트림을 닫고 모든 시스템 자원을 해제.
            //outputStreamWriter.close();
            Log.d("hello", "나 여기 1");
            //-------------------2. requestProc에 따른 결과값을 JSP로부터 받아오기 --------------------//
            //Http연결 성공 후 Jsp로부터 Response 가 온다면
            if(conn.getResponseCode() == conn.HTTP_OK){
                Log.d("HTTP 연결 결과 : ", "HTTP 연결 성공");
                //(10) Jsp로부터 받은 결과값을 stringBuffer에 담기
                //InputStream은 데이터가 들어오는 통로의 역할에 관해 규정 https://lannstark.tistory.com/34
                InputStreamReader inputStreamReader;
                inputStreamReader = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer stringBuffer = new StringBuffer();
                //버퍼의 웹문서 소스를 줄 단위로 읽는다(line)
                String line = bufferedReader.readLine(); //{"jsondata":[{"name":"test","id":"test"}]}
                Log.d("-1",line);
                //-------- account_read_response.jsp로부터 json데이터 가져오기 완료!---------//

                //-------- json 데이터 가공 시작! --------//
                JSONObject jsonObject = new JSONObject(line); //{"jsondata":[{"name":"test","id":"test"}]}
                JSONArray jsonArray = jsonObject.getJSONArray("jsondata"); //[{"name":"test","id":"test"}]
                JSONObject jsonDataObj = jsonArray.getJSONObject(0); //{"name":"test","id":"test"}

                if(requestProc == "login"){
                    resultData =
                            jsonDataObj.getString("name") + ","
                            +jsonDataObj.getString("phone") + ","
                            +jsonDataObj.getString("stay") + ","
                    ;
                    Log.d("check Login Data : ", resultData);
                    //https://hianna.tistory.com/510
                }
                else if(requestProc == "join"){
                    resultData =
                            jsonDataObj.getString("name") + ","
                            +jsonDataObj.getString("phone") + ","
                            +jsonDataObj.getString("stay") + ","
                            +jsonDataObj.getString("uuid") + ","
                    ;
                    Log.d("check Login Data : ", resultData);
                    //https://hianna.tistory.com/510
                }

                //--------------------- AndroidToJsp -> MainActivity -----------------------

                Log.d("jsp>AndroidToJsp", resultData+"");

            }else{
                Log.d("HTTP 연결 결과 : ", "HTTP 연결 실패");
                Log.d("e", "status code="+conn.getResponseCode());
                Log.d("e", "content type="+conn.getContentType());
                Log.d("e", "content length="+conn.getContentLength());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //(12) MainActivity에 결과값을 반환
        return resultData;
    }
}
