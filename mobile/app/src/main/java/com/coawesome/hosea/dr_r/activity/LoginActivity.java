package com.coawesome.hosea.dr_r.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LeeMoonSeong on 2016-11-10.
 */
public class LoginActivity extends AppCompatActivity {
    private AQuery aq = new AQuery(this);
    private Intent previousIntent;
    private EditText id, password;
    private Button join, login;
    private String u_name;
    private int u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        previousIntent = getIntent();

        id = (EditText) findViewById(R.id.login_login_id);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.request_login);
        join = (Button) findViewById(R.id.request_join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater=getLayoutInflater();
                final View dialogView= inflater.inflate(R.layout.dialog_agreement, null);
                AlertDialog.Builder buider= new AlertDialog.Builder(LoginActivity.this); //AlertDialog.Builder 객체 생성
                buider.setTitle("개인정보 활용 동의서"); //Dialog 제목
                buider.setView(dialogView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)
                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckBox checkBox1 = (CheckBox) dialogView.findViewById(R.id.dialog_agree1);
                        CheckBox checkBox2 = (CheckBox) dialogView.findViewById(R.id.dialog_agree2);

                        if (checkBox1.isChecked() && checkBox2.isChecked()) {
                            Toast.makeText(getApplicationContext(), "약관에 동의했습니다.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), JoinActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "약관에 동의하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "약관을 동의 하셔야 가입이 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog=buider.create();
                dialog.setCanceledOnTouchOutside(false);//없어지지 않도록 설정
                dialog.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("login_id", id.getText().toString());
                params.put("u_password", password.getText().toString());
                params.put("u_device" ,previousIntent.getStringExtra("u_device"));
                aq.ajax("http://52.41.218.18:8080/login", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject jsonObject, AjaxStatus status) {
                        if (jsonObject != null) {
                            try {
                                u_id = jsonObject.getInt("num");
                                u_name = jsonObject.getString("msg");

                                if (u_id > 0) {
                                    Toast.makeText(getApplicationContext(), u_name + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    mainIntent.putExtra("u_id", u_id);
                                    mainIntent.putExtra("u_name", u_name);
                                    mainIntent.putExtra("u_device", previousIntent.getStringExtra("u_device"));
                                    startActivity(mainIntent);
                                    LoginActivity.this.finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                                    id.setText("");
                                    id.requestFocus();
                                    password.setText("");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });

            }
        });
    }


}
