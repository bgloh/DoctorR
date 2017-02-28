package com.coawesome.hosea.dr_r.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GraphActivity extends AppCompatActivity {
    private Intent previousIntent;
    private AQuery aq = new AQuery(this);
    ImageView baby_height;
    ImageView baby_weight;
    Button btn_measure_height;
    Button btn_measure_weight;
    String sex = "";
    EditText et_baby_month;
    EditText et_baby_height;
    EditText et_baby_month2;
    EditText et_baby_weight;
    TextView tv_avg_height, tv_per_height, tv_my_height;
    TextView tv_avg_weight, tv_per_weight, tv_my_weight;
    final double[] array_boy_height_97 = {56.0, 60.0, 64.0, 67.0, 69.0, 71.5, 73.5, 75.0, 77.0, 78.0, 80.0, 81.0, 82.0, 83.0, 85.0, 86.0, 87.0, 88.0, 89.0, 90.0, 91.0, 92.0, 92.8, 93.8, 94.5, 95.2, 96.0, 97.0, 98.0, 98.5, 99.0, 100.0, 100.8, 101.2, 102.0, 102.5, 103.0};
    final double[] array_boy_height_95 = {55.0, 59.8, 63.8, 66.8, 68.8, 71.2, 73.2, 74.6, 76.7, 77.6, 79.6, 80.6, 81.5, 82.8, 84.0, 85.0, 86.0, 87.0, 88.0, 89.0, 90.0, 91.0, 92.0, 93.0, 93.6, 94.4, 95.0, 96.0, 97.0, 97.5, 98.2, 99.0, 99.5, 100.0, 101.0, 101.3, 102.0};
    final double[] array_boy_height_90 = {54.0, 59.2, 63.0, 65.7, 68.0, 70.0, 72.0, 73.5, 75.0, 76.3, 78.0, 79.0, 80.3, 81.5, 82.8, 84.0, 85.0, 86.0, 87.0, 87.9, 88.9, 89.5, 90.5, 91.3, 92.0, 93.0, 93.8, 94.5, 95.2, 96.0, 96.9, 97.3, 98.0, 98.8, 99.2, 99.9, 100.4};
    final double[] array_boy_height_75 = {52.0, 57.0, 61.0, 64.0, 66.0, 68.0, 70.5, 72.0, 73.5, 75.0, 76.0, 77.3, 78.6, 79.8, 81.0, 82.0, 82.8, 83.9, 85.0, 85.8, 86.8, 87.4, 88.2, 89.0, 89.9, 90.7, 91.3, 92.0, 92.8, 93.5, 94.0, 94.9, 95.4, 96.0, 96.8, 97.2, 98.0};
    final double[] array_boy_height_50 = {50.0, 55.0, 59.0, 62.0, 64.6, 66.5, 68.7, 70.0, 71.5, 73.0, 74.3, 75.5, 76.6, 77.5, 78.8, 80.0, 80.7, 81.5, 82.7, 83.2, 84.2, 85.0, 86.0, 86.5, 87.2, 88.0, 88.9, 89.3, 90.0, 90.8, 91.4, 92.0, 92.6, 93.1, 93.9, 94.3, 95.0};
    final double[] array_boy_height_25 = {48.1, 54.0, 57.4, 60.0, 63.0, 64.8, 66.9, 68.2, 69.9, 71.1, 72.4, 73.5, 74.7, 75.6, 76.8, 77.7, 78.4, 79.2, 80.4, 81.0, 82.0, 82.7, 83.5, 84.1, 84.9, 85.4, 86.0, 86.9, 87.4, 88.0, 88.7, 89.2, 89.9, 90.3, 91.0, 91.4, 92.1};
    final double[] array_boy_height_10 = {46.0, 53.0, 56.0, 59.0, 61.3, 63.0, 65.3, 66.9, 68.0, 69.7, 70.8, 71.7, 72.9, 73.9, 74.8, 75.6, 76.5, 77.3, 78.2, 79.0, 80.0, 80.6, 81.2, 82.0, 82.7, 83.2, 84.0, 84.7, 85.0, 85.8, 86.2, 86.9, 87.3, 88.0, 88.6, 89.2, 89.9};
    final double[] array_boy_height_5 = {45.2, 51.0, 55.0, 58.0, 60.5, 62.0, 64.3, 65.8, 67.0, 68.6, 69.8, 70.7, 71.8, 72.6, 73.6, 74.7, 75.4, 76.0, 77.0, 77.9, 78.5, 79.3, 80.0, 80.7, 81.2, 82.0, 82.6, 83.5, 84.0, 84.4, 85.0, 85.4, 86.0, 86.7, 87.0, 87.8, 88.3};
    final double[] array_boy_height_3 = {44.8, 50.8, 54.8, 57.8, 60.3, 61.7, 63.8, 65.0, 66.3, 67.5, 69.0, 70.0, 71.0, 72.0, 72.9, 73.9, 74.7, 75.3, 76.2, 77.0, 77.8, 78.4, 79.0, 79.8, 80.4, 81.0, 81.7, 82.1, 82.7, 83.5, 84.0, 84.5, 85.0, 85.7, 86.2, 86.8, 87.6};

    final double[] array_boy_weight_97 = {4.8, 6, 7.7, 8.5, 9, 9.6, 10, 10.5, 10.9, 11.2, 11.6, 12, 12.4, 12.6, 13, 13.2, 13.5, 13.8, 14, 14.3, 14.6, 14.8, 15.2, 15.4, 15.6, 15.8, 16, 16.3, 16.6, 16.7, 16.9, 17.1, 17.3, 17.5, 17.7, 17.8, 18};
    final double[] array_boy_weight_95 = {4.4, 6, 7.5, 8.2, 8.8, 9.4, 9.8, 10.2, 10.6, 11, 11.3, 11.6, 12, 12.5, 12.6, 12.8, 13.1, 13.4, 13.6, 13.9, 14.2, 14.5, 14.7, 15.2, 15.3, 15.4, 15.7, 15.8, 16, 16.3, 16.5, 16.7, 16.8, 17, 17.2, 17.3, 17.4};
    final double[] array_boy_weight_90 = {4.1, 5.7, 7.2, 7.9, 8.3, 9, 9.5, 9.8, 10.2, 10.6, 10.9, 11.2, 11.5, 11.8, 12.1, 12.4, 12.6, 13, 13.1, 13.3, 13.5, 13.8, 14.1, 14.5, 14.8, 14.8, 15, 15.1, 15.3, 15.5, 15.8, 15.9, 16, 16.2, 16.4, 16.6, 16.8};
    final double[] array_boy_weight_75 = {3.8, 5.4, 6.6, 7.4, 7.8, 8.3, 8.8, 9.2, 9.5, 10, 10.2, 10.5, 10.8, 11, 11.2, 11.5, 11.8, 12.1, 12.4, 12.5, 12.8, 13, 13.2, 13.3, 13.6, 13.8, 14, 14.1, 14.3, 14.5, 14.8, 14.9, 15, 15.2, 15.4, 15.5, 15.6};
    final double[] array_boy_weight_50 = {3.5, 5, 6, 6.8, 7.3, 7.7, 8.2, 8.5, 9, 9.2, 9.5, 9.8, 10, 10.2, 10.5, 10.8, 11, 11.2, 11.4, 11.6, 11.9, 12, 12.3, 12.5, 12.8, 12.8, 13, 13.1, 13.2, 13.5, 13.7, 13.8, 13.9, 14, 14.1, 14.3, 14.5};
    final double[] array_boy_weight_25 = {3.1, 4.7, 5.7, 6.3, 6.8, 7.2, 7.5, 8, 8.3, 8.5, 8.8, 9, 9.5, 9.7, 9.8, 10, 10.2, 10.4, 10.7, 10.8, 11, 11.2, 11.4, 11.5, 11.7, 11.9, 12, 12.2, 12.4, 12.5, 12.6, 12.8, 12.9, 13, 13.1, 13.2, 13.3};
    final double[] array_boy_weight_10 = {2.9, 4.4, 5.3, 5.9, 6.4, 6.6, 7, 7.4, 7.7, 8, 8.2, 8.5, 8.7, 8.8, 9.1, 9.4, 9.5, 9.8, 10, 10.1, 10.3, 10.4, 10.7, 10.8, 11, 11.1, 11.3, 11.4, 11.5, 11.7, 11.8, 11.9, 12, 12.2, 12.3, 12.4, 12.5};
    final double[] array_boy_weight_5 = {2.8, 4.2, 5, 5.4, 6, 6.4, 6.7, 7, 7.4, 7.6, 7.8, 8.1, 8.3, 8.5, 8.8, 9, 9.1, 9.4, 9.5, 9.7, 9.9, 10, 10.2, 10.3, 10.5, 10.7, 10.8, 11, 11.1, 11.2, 11.3, 11.4, 11.6, 11.8, 11.9, 12, 12.1};
    final double[] array_boy_weight_3 = {2.7, 4, 4.8, 5.2, 5.8, 6.2, 6.5, 6.8, 7.2, 7.4, 7.6, 7.9, 8.1, 8.3, 8.5, 8.7, 8.9, 9.1, 9.2, 9.5, 9.7, 9.9, 10, 10.1, 10.3, 10.5, 10.6, 10.8, 10.9, 11, 11.1, 11.2, 11.4, 11.5, 11.6, 11.7, 11.8};

    final double[] array_girl_height_97 = {54.5, 59.5, 63, 66.8, 68, 70, 72.2, 74, 75.5, 77, 78.5, 79.8, 81, 82.1, 83.5, 84.5, 85.6, 86.8, 87.8, 88.6, 89.6, 90.5, 91.5, 92.3, 93.1, 94, 94.9, 95.5, 96.4, 97.1, 98, 98.5, 99.1, 99.7, 100.3, 101, 101.6};
    final double[] array_girl_height_95 = {53.8, 59.4, 62.5, 65, 67.5, 69.5, 71.8, 73.4, 75, 76.2, 78, 79, 80.2, 81.5, 82.6, 83.8, 84.8, 85.9, 87, 87.9, 88.8, 89.5, 90.6, 91.5, 92.2, 93, 93.9, 94.5, 95.2, 96.1, 97, 97.5, 98.2, 98.8, 99.3, 100, 100.5};
    final double[] array_girl_height_90 = {53, 58, 61.5, 65.7, 66.5, 68.8, 70.8, 72.2, 74, 75.2, 76.8, 78, 79, 80.2, 81.5, 82.5, 83.5, 84.7, 85.8, 86.5, 87.5, 88.2, 89.1, 90, 90.9, 91.6, 92.5, 93.2, 93.9, 94.6, 95.4, 96, 96.8, 97.2, 97.9, 98.4, 99};
    final double[] array_girl_height_75 = {51, 56, 60, 64, 65, 67, 69.1, 70.8, 72.2, 73.5, 75, 76, 77.2, 78.5, 79.5, 80.4, 81.5, 82.5, 83.5, 84.3, 85.5, 86.1, 87, 87.8, 88.6, 89.3, 90.1, 90.6, 91.5, 92.1, 93, 93.4, 94, 94.8, 95.2, 96, 95.5};
    final double[] array_girl_height_50 = {49.5, 55, 58.5, 62, 63.5, 65.5, 67.2, 69, 70.5, 71.8, 73, 74.2, 75.4, 76.5, 77.2, 78.2, 79.2, 80.2, 81.4, 82, 83, 83.8, 84.8, 85.4, 86.2, 86.9, 87.8, 88.2, 89, 89.6, 90.2, 90.8, 91.4, 92, 92.5, 93.2, 93.8};
    final double[] array_girl_height_25 = {47.8, 53, 56.5, 60, 61.5, 63.5, 65.5, 67, 68.5, 70, 70.1, 72.2, 73.3, 74.5, 75.5, 76.1, 77.2, 78.1, 79.1, 79.9, 80.7, 81.5, 82.3, 83, 83.9, 84.4, 85.1, 85.8, 86.3, 87, 87.8, 88.1, 88.8, 89.2, 90, 90.5, 91.1};
    final double[] array_girl_height_10 = {46, 51.5, 55, 59, 60, 62, 64, 65.4, 66.8, 68, 69.5, 70.5, 71.5, 72.6, 73.8, 74.5, 75.5, 76.2, 77.1, 78, 78.8, 79.5, 80.2, 81, 81.8, 82.4, 83, 83.6, 84.1, 84.9, 85.5, 86, 86.6, 87, 87.8, 88.1, 88.7};
    final double[] array_girl_height_5 = {45, 50.2, 54, 58, 59, 61, 63, 64.2, 65.5, 67, 68.4, 69.5, 70.5, 71.8, 72.5, 73.4, 74.4, 75.2, 76, 76.9, 77.6, 78.4, 79.1, 79.9, 80.7, 81.2, 81.9, 82.4, 83, 83.5, 84, 84.6, 85.1, 85.8, 86.2, 86.8, 87.3};
    final double[] array_girl_height_3 = {44.8, 50, 53.8, 57.8, 58.4, 60.2, 62.2, 63.5, 65.1, 66.4, 67.8, 68.6, 69.8, 71, 72, 72.8, 73.8, 74.4, 75.5, 76.1, 76.9, 77.6, 78.4, 79.1, 79.9, 80.4, 81, 81.7, 82.1, 82.8, 83.2, 83.8, 84.2, 84.9, 85.3, 86.9, 82.3};

    final double[] array_girl_weight_97 = {4.6, 6.1, 7.2, 7.8, 8.4, 8.9, 9.4, 9.8, 10.3, 10.6, 11.0, 11.4, 11.6, 12.0, 12.3, 12.6, 12.8, 13.2, 13.4, 13.6, 13.9, 14.2, 14.5, 14.7, 15.0, 15.2, 15.5, 15.7, 15.9, 16.2, 16.4, 16.5, 16.6, 16.9, 17.1, 17.3, 17.5};
    final double[] array_girl_weight_95 = {4.5, 6.0, 7, 7.6, 8.2, 8.6, 9.2, 9.6, 10.0, 10.4, 10.6, 11.0, 11.4, 11.6, 11.9, 12.3, 12.5, 12.7, 13.0, 13.3, 13.5, 13.7, 14.1, 14.4, 14.6, 14.8, 15.0, 15.2, 15.4, 15.6, 15.8, 16.0, 16.2, 16.4, 16.6, 16.8, 17.0};
    final double[] array_girl_weight_90 = {3.8, 5.6, 6.6, 7.3, 7.8, 8.4, 8.8, 9.3, 9.6, 10.0, 10.4, 10.6, 11.0, 11.3, 11.5, 11.8, 12.0, 12.3, 12.5, 12.8, 13.0, 13.2, 13.5, 13.7, 13.9, 14.2, 14.4, 14.5, 14.7, 15.0, 15.2, 15.4, 15.5, 15.7, 15.8, 16.0, 16.2};
    final double[] array_girl_weight_75 = {3.7, 5.2, 6.2, 6.8, 7.4, 7.8, 8.3, 8.6, 9.0, 9.4, 9.6, 10.0, 10.3, 10.5, 10.7, 11.0, 11.3, 11.5, 11.7, 11.9, 12.2, 12.4, 12.6, 12.7, 12.9, 13.2, 13.4, 13.5, 13.7, 13.9, 14.1, 14.3, 14.4, 14.5, 14.6, 14.8, 15.0};
    final double[] array_girl_weight_50 = {3.2, 5.0, 5.6, 6.4, 6.9, 7.4, 7.6, 8.0, 8.4, 8.6, 9.0, 9.2, 9.5, 9.7, 10.0, 10.2, 10.5, 10.6, 10.8, 11.0, 11.3, 11.5, 11.6, 11.8, 12.0, 12.2, 12.4, 12.5, 12.6, 12.8, 13.0, 13.1, 13.2, 13.4, 13.7, 13.8, 13.9};
    final double[] array_girl_weight_25 = {3.0, 4.5, 5.2, 5.8, 6.4, 6.7, 7.2, 7.5, 7.7, 8.1, 8.4, 8.6, 8.7, 9.0, 9.3, 9.5, 9.7, 9.8, 10.1, 10.3, 10.4, 10.6, 10.7, 10.9, 11.2, 11.4, 11.5, 11.6, 11.7, 11.9, 12.1, 12.2, 12.4, 12.5, 12.6, 12.7, 12.8};
    final double[] array_girl_weight_10 = {2.7, 4.2, 4.8, 5.5, 5.8, 6.4, 6.6, 6.9, 7.3, 7.5, 7.7, 7.9, 8.2, 8.4, 8.5, 8.7, 9.0, 9.2, 9.4, 9.5, 9.7, 9.9, 10.2, 10.4, 10.5, 10.7, 10.8, 11.0, 11.2, 11.4, 11.5, 11.6, 11.7, 11.8, 11.9, 12.0, 12.1};
    final double[] array_girl_weight_5 = {2.6, 3.9, 4.6, 5.2, 5.6, 6.0, 6.3, 6.6, 6.9, 7.2, 7.4, 7.6, 7.7, 8.0, 8.3, 8.5, 8.6, 8.8, 9.0, 9.2, 9.4, 9.5, 9.6, 9.8, 10.0, 10.2, 10.4, 10.5, 10.6, 10.7, 10.8, 11.0, 11.1, 11.2, 11.3, 11.5, 11.6};
    final double[] array_girl_weight_3 = {2.5, 3.7, 4.5, 5.0, 5.4, 5.7, 6.1, 6.4, 6.6, 7.0, 7.2, 7.4, 7.6, 7.7, 8.0, 8.3, 8.4, 8.6, 8.7, 8.9, 9.1, 9.3, 9.4, 9.6, 9.7, 9.8, 10.0, 10.2, 10.4, 10.5, 10.6, 10.7, 10.8, 10.9, 11.1, 11.3, 11.4};
//    LineGraphSeries<DataPoint> sleeps;
//    LineGraphSeries<DataPoint> feeds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_graph);
        previousIntent = getIntent();

        baby_height = (ImageView) findViewById(R.id.iv_baby_height);
        baby_weight = (ImageView) findViewById(R.id.iv_baby_weight);
        btn_measure_height = (Button) findViewById(R.id.measureHeight);
        btn_measure_weight = (Button) findViewById(R.id.measureWeight);
        et_baby_height = (EditText) findViewById(R.id.et_baby_height);
        et_baby_month = (EditText) findViewById(R.id.et_baby_month);
        et_baby_weight = (EditText) findViewById(R.id.et_baby_weight);
        et_baby_month2 = (EditText) findViewById(R.id.et_baby_month2);
        tv_avg_height = (TextView) findViewById(R.id.tv_avg_height);
        tv_per_height = (TextView) findViewById(R.id.tv_per_height);
        tv_my_height = (TextView) findViewById(R.id.tv_my_height);
        tv_avg_weight = (TextView) findViewById(R.id.tv_avg_weight);
        tv_per_weight = (TextView) findViewById(R.id.tv_per_weight);
        tv_my_weight = (TextView) findViewById(R.id.tv_my_weight);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", previousIntent.getIntExtra("u_id", 0));
        aq.ajax("http://52.41.218.18:8080/getSex", params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                if (html != null) {
                    try {
                        sex = html.getString("u_sex");
                        drawImageBySex(sex);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("u_id", previousIntent.getIntExtra("u_id", 0));
//        aq.ajax("http://52.41.218.18:8080/getDateSleepTime", params, JSONArray.class, new AjaxCallback<JSONArray>() {
//            @Override
//            public void callback(String url, JSONArray html, AjaxStatus status) {
//                if (html != null) {
//                    try {
//                        getDateSleepTime(html);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "해당하는 데이터가 없습니다", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        aq.ajax("http://52.41.218.18:8080/getDateFeedTime", params, JSONArray.class, new AjaxCallback<JSONArray>() {
//            @Override
//            public void callback(String url, JSONArray html, AjaxStatus status) {
//                if (html != null) {
//                    try {
//                        getDateFeedTime(html);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "해당하는 데이터가 없습니다", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        btn_measure_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_baby_month.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "아기의 개월 수를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (et_baby_height.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "아기의 신장을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (sex.equals("남자")) {
                        measure_height_boy();
                    } else {
                        measure_height_girl();
                    }
                }

            }
        });

        btn_measure_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_baby_month2.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "아기의 개월 수를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (et_baby_weight.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "아기의 체중을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (sex.equals("남자")) {
                        measure_weight_boy();
                    } else {
                        measure_weight_girl();
                    }
                }

            }
        });
    }

    public void drawImageBySex(String sex) {
        if (sex.equals("남자")) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.boy_height);
            Drawable drawable2 = getResources().getDrawable(R.drawable.boy_weight);
            baby_height.setImageDrawable(drawable1);
            baby_weight.setImageDrawable(drawable2);
        } else {
            Drawable drawable3 = getResources().getDrawable(R.drawable.girl_height);
            Drawable drawable4 = getResources().getDrawable(R.drawable.girl_weight);
            baby_height.setImageDrawable(drawable3);
            baby_weight.setImageDrawable(drawable4);
        }

    }

    public void measure_height_boy() {
        int month = Integer.parseInt(et_baby_month.getText().toString());
        double height = Double.parseDouble(et_baby_height.getText().toString());
        double result[] = new double[9];
        String per_height = "";
        result[0] = array_boy_height_97[month];
        result[1] = array_boy_height_95[month];
        result[2] = array_boy_height_90[month];
        result[3] = array_boy_height_75[month];
        result[4] = array_boy_height_50[month];
        result[5] = array_boy_height_25[month];
        result[6] = array_boy_height_10[month];
        result[7] = array_boy_height_5[month];
        result[8] = array_boy_height_3[month];

        tv_avg_height.setText("평균 키 : " + result[4] + "cm ");
        tv_my_height.setText("나의 키 : " + height + "cm ");

        for (int i = 8; i >= 0; i--) {
            if (result[i] <= height) {
                if (i == 0) per_height = "97";
                else if (i == 1) per_height = "95";
                else if (i == 2) per_height = "90";
                else if (i == 3) per_height = "75";
                else if (i == 4) per_height = "50";
                else if (i == 5) per_height = "25";
                else if (i == 6) per_height = "10";
                else if (i == 7) per_height = "5";
                else if (i == 8) per_height = "3";
            }
        }
        tv_per_height.setText("백분율 : " + per_height + "%");
        if(result[8] > height) {
            per_height = "0~3";
            tv_per_height.setText("백분율 : " + per_height + "%");
        }
    }

    public void measure_weight_boy() {
        int month = Integer.parseInt(et_baby_month2.getText().toString());
        double weight = Double.parseDouble(et_baby_weight.getText().toString());
        double result[] = new double[9];
        String per_height = "";
        result[0] = array_boy_weight_97[month];
        result[1] = array_boy_weight_95[month];
        result[2] = array_boy_weight_90[month];
        result[3] = array_boy_weight_75[month];
        result[4] = array_boy_weight_50[month];
        result[5] = array_boy_weight_25[month];
        result[6] = array_boy_weight_10[month];
        result[7] = array_boy_weight_5[month];
        result[8] = array_boy_weight_3[month];

        tv_avg_weight.setText("평균 몸무게 : " + result[4] + "kg ");
        tv_my_weight.setText("나의 몸무게 : " + weight + "kg ");

        for (int i = 8; i >= 0; i--) {
            if (result[i] <= weight) {
                if (i == 0) per_height = "97";
                else if (i == 1) per_height = "95";
                else if (i == 2) per_height = "90";
                else if (i == 3) per_height = "75";
                else if (i == 4) per_height = "50";
                else if (i == 5) per_height = "25";
                else if (i == 6) per_height = "10";
                else if (i == 7) per_height = "5";
                else if (i == 8) per_height = "3";
            }
        }
        tv_per_weight.setText("백분율 : " + per_height + "%");
        if(result[8] > weight) {
            per_height = "0~3";
            tv_per_weight.setText("백분율 : " + per_height + "%");
        }
    }

    public void measure_height_girl() {
        int month = Integer.parseInt(et_baby_month.getText().toString());
        double height = Double.parseDouble(et_baby_height.getText().toString());
        double result[] = new double[9];
        String per_height = "";
        result[0] = array_girl_height_97[month];
        result[1] = array_girl_height_95[month];
        result[2] = array_girl_height_90[month];
        result[3] = array_girl_height_75[month];
        result[4] = array_girl_height_50[month];
        result[5] = array_girl_height_25[month];
        result[6] = array_girl_height_10[month];
        result[7] = array_girl_height_5[month];
        result[8] = array_girl_height_3[month];

        tv_avg_height.setText("평균 키 : " + result[4] + "cm ");
        tv_my_height.setText("나의 키 : " + height + "cm ");

        for (int i = 8; i >= 0; i--) {
            if (result[i] <= height) {
                if (i == 0) per_height = "97";
                else if (i == 1) per_height = "95";
                else if (i == 2) per_height = "90";
                else if (i == 3) per_height = "75";
                else if (i == 4) per_height = "50";
                else if (i == 5) per_height = "25";
                else if (i == 6) per_height = "10";
                else if (i == 7) per_height = "5";
                else if (i == 8) per_height = "3";
            }
        }
        tv_per_height.setText("백분율 : " + per_height + "%");
        if(result[8] > height) {
            per_height = "0~3";
            tv_per_height.setText("백분율 : " + per_height + "%");
        }
    }

    public void measure_weight_girl() {
        int month = Integer.parseInt(et_baby_month.getText().toString());
        double weight = Double.parseDouble(et_baby_weight.getText().toString());
        double result[] = new double[9];
        String per_height = "";
        result[0] = array_girl_weight_97[month];
        result[1] = array_girl_weight_95[month];
        result[2] = array_girl_weight_90[month];
        result[3] = array_girl_weight_75[month];
        result[4] = array_girl_weight_50[month];
        result[5] = array_girl_weight_25[month];
        result[6] = array_girl_weight_10[month];
        result[7] = array_girl_weight_5[month];
        result[8] = array_girl_weight_3[month];

        tv_avg_weight.setText("평균 몸무게 : " + result[4] + "kg ");
        tv_my_weight.setText("나의 몸무게 : " + weight + "kg ");

        for (int i = 8; i >= 0; i--) {
            if (result[i] <= weight) {
                if (i == 0) per_height = "97";
                else if (i == 1) per_height = "95";
                else if (i == 2) per_height = "90";
                else if (i == 3) per_height = "75";
                else if (i == 4) per_height = "50";
                else if (i == 5) per_height = "25";
                else if (i == 6) per_height = "10";
                else if (i == 7) per_height = "5";
                else if (i == 8) per_height = "3";
            }
        }
        tv_per_weight.setText("백분율 : " + per_height + "%");
        if(result[8] > weight) {
            per_height = "0~3";
            tv_per_weight.setText("백분율 : " + per_height + "%");
        }
    }

//    public void getDateSleepTime(JSONArray jsonArray) throws JSONException {
//
//        double y,x=0;
//
//        GraphView sleep_graph = (GraphView) findViewById(R.id.sleep_graph);
//
//        sleeps = new LineGraphSeries<DataPoint>();
//        for(int i=0; i< jsonArray.length(); i++){
//            SleepVO sleepVO = new SleepVO(jsonArray.getJSONObject(i));
//            x = x+0.1;
//            y = sleepVO.getS_total();
//            sleeps.appendData(new DataPoint(x, y), true, 500);
//        }
//        sleep_graph.addSeries(sleeps);
//    }
//    public void getDateFeedTime(JSONArray jsonArray) throws JSONException {
//
//        double y,x=0;
//
//        GraphView feed_graph = (GraphView) findViewById(R.id.feed_graph);
//
//        feeds = new LineGraphSeries<DataPoint>();
//        for(int i=0; i< jsonArray.length(); i++){
//            FeedVO feedVO= new FeedVO(jsonArray.getJSONObject(i));
//            x = x+0.1;
//            y = feedVO.getF_total();
//            feeds.appendData(new DataPoint(x, y), true, 500);
//        }
//        feed_graph.addSeries(feeds);
//    }
}


