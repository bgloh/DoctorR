package com.coawesome.hosea.dr_r.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CallDrActivity extends AppCompatActivity {
    private Intent previousIntent;
    private AQuery aq = new AQuery(this);
    String uri, phoneNum;
    JSONObject jsonObject;
    TextView tv;
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calldoctor);
        previousIntent = getIntent();

        tv = (TextView) findViewById(R.id.tv_doctor_phone_num);
        /*iv = (ImageView) findViewById(R.id.doctor_img);
*/


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", previousIntent.getIntExtra("u_id", 0));
        aq.ajax("http://52.41.218.18:8080/getDocPhone", params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                if (html != null) {
                    try {
                        phoneNum = html.getString("a_phone");
                        uri = "tel:" + phoneNum;
                        tv.setText(phoneNum);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(Intent.ACTION_DIAL,
                                        Uri.parse(uri)));
                            }
                        });
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    tv.setText("연결상태가 좋지 않습니다.");
                }
            }
        });
    }
}