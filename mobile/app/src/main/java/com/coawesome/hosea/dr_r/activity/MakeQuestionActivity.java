package com.coawesome.hosea.dr_r.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MakeQuestionActivity extends AppCompatActivity {
    private AQuery aq = new AQuery(this);
    private Intent previousIntent;
    EditText title, content;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makequestion);
        previousIntent = getIntent();

        title = (EditText) findViewById(R.id.et_makeQuestion_title);
        content = (EditText) findViewById(R.id.et_makeQuestion_content);
        submit = (Button) findViewById(R.id.btn_qna_Question_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!title.getText().toString().equals("") && !content.getText().toString().equals("")) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("u_id", previousIntent.getIntExtra("u_id", 0));
                    params.put("u_name", previousIntent.getStringExtra("u_name"));
                    params.put("qna_title", title.getText());
                    params.put("qna_content", content.getText());
                    aq.ajax("http://52.41.218.18:8080/makeQuestion", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject html, AjaxStatus status) {
                            if (html != null) {
                                Toast.makeText(getApplicationContext(), "작성 완료", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "연결 상태가 좋지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    if (title.getText().toString().equals("")) {
                        title.requestFocus();
                    } else {
                        content.requestFocus();
                    }
                }
            }
        });

    }

}