package com.coawesome.hosea.dr_r.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Intent previousIntent, intent;
    private TextView correctedAge, joiningDate;
    private AQuery aq = new AQuery(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previousIntent = getIntent();

        View button1 = findViewById(R.id.btn_main_1);
        button1.setOnClickListener(mClick);
        correctedAge = (TextView)findViewById(R.id.tv_main_corrected_age);
        joiningDate = (TextView)findViewById(R.id.tv_main_joining_date);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", previousIntent.getIntExtra("u_id", 0));
        aq.ajax("http://52.41.218.18:8080/getUserDate", params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                if (html != null) {
                    try {
                        try {
                            setDate(html);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.btn_main_2).setOnClickListener(mClick);

        findViewById(R.id.btn_main_3).setOnClickListener(mClick);

        findViewById(R.id.btn_main_4).setOnClickListener(mClick);

        //로그아웃 버튼
        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent logoutIntent = new Intent(getApplicationContext(),LoginActivity.class);
                logoutIntent.putExtra("u_device", previousIntent.getStringExtra("u_device"));
                startActivity(logoutIntent);
                MainActivity.this.finish();
            }
        });
        //help 버튼
        Button help = (Button)findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent helpIntent = new Intent(getApplicationContext(),SlideActivity.class);
                startActivity(helpIntent);
            }
        });
    }


    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_main_1:
                    intent = new Intent(getApplicationContext(), TimeActivity.class);
                    intent.putExtra("u_id", previousIntent.getIntExtra("u_id", 0))
                            .putExtra("u_name", previousIntent.getStringExtra("u_name"));
                    startActivity(intent);
                    break;
                case R.id.btn_main_2:
                    intent = new Intent(getApplicationContext(), WriteDiaryActivity.class);
                    intent.putExtra("u_id", previousIntent.getIntExtra("u_id", 0))
                            .putExtra("u_name", previousIntent.getStringExtra("u_name"));
                    startActivity(intent);
                    break;
                case R.id.btn_main_3:
                    intent = new Intent(getApplicationContext(), GraphActivity.class);
                    intent.putExtra("u_id", previousIntent.getIntExtra("u_id", 0))
                            .putExtra("u_name", previousIntent.getStringExtra("u_name"));
                    startActivity(intent);
                    break;
                case R.id.btn_main_4:
                    intent = new Intent(getApplicationContext(), ReadDiaryActivity.class);
                    intent.putExtra("u_id", previousIntent.getIntExtra("u_id", 0))
                            .putExtra("u_name", previousIntent.getStringExtra("u_name"));
                    startActivity(intent);
                    break;
                //TODO shere버튼 생성 예정
//                    case R.id.share:
//                        Intent msg = new Intent(Intent.ACTION_SEND);
//                        msg.addCategory(Intent.CATEGORY_DEFAULT);
//                        msg.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
//                        msg.putExtra(Intent.EXTRA_TEXT, "애플리케이션 공유하세요");
//                        msg.putExtra(Intent.EXTRA_TITLE, "제목");
//                        msg.setType("text/plain");
//                        startActivity(Intent.createChooser(msg, "공유하기"));
//                        break;
                default:
                    break;
            }
        }
    };

    public void setDate(JSONObject jsonObject) throws JSONException, ParseException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA);
        Long join_time = Long.parseLong(jsonObject.getString("u_join_date"));

        calAge(jsonObject);
        Date joinDate = new Date(join_time);
        joiningDate.setText("가입일 : " + dateFormat.format(joinDate));
    }

    public void calAge(JSONObject jsonObject) throws JSONException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowString = simpleDateFormat.format(new Date());

        Date nowDate = simpleDateFormat.parse(nowString);


        Long expectedTime = Long.parseLong(jsonObject.getString("u_expected"));
        Date expectedDate = new Date(expectedTime);

        int compare = 0;
        compare = expectedDate.compareTo(nowDate);

        if (compare > 0) {            //예정일이 더 클경우
            Calendar c1 = Calendar.getInstance();    //예정일
            Calendar c2 = Calendar.getInstance();    //오늘

            c1.setTime(expectedDate);
            c2.setTime(nowDate);

            long d1, d2;
            d1 = c1.getTime().getTime();        //예정일 -> ms
            d2 = c2.getTime().getTime();        //오늘 ->ms

            int days = (int) ((d1 - d2) / (1000 * 60 * 60 * 24));
            measureDateLess(days);
        } else if (compare < 0) {           //계산날짜가 예정일보다 큰 경우
            correctedAge.setText(getDateDifferenceInDDMMYYYY(expectedDate, nowDate));
        } else {                           //예정일과 계산날짜가 같은 경우
            correctedAge.setText("교정연령 :  " + 0 + "(오늘 태어났습니다.)");
        }
    }

    public static String getDateDifferenceInDDMMYYYY(Date from, Date to) {
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        fromDate.setTime(from);
        toDate.setTime(to);
        int increment = 0;
        int year, month, day;
        System.out.println(fromDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
            increment = fromDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        System.out.println("increment" + increment);
        //일 계산
        if (increment != 0) {
            day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }

        //월 계산
        if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH)) {
            month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

        //년 계산
        year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
        return "교정연령 : "+ year + "년 " + month + "월 " + day + "일 ";

    }

    public void measureDateLess(int days) {
        int Base = 280;

        int week = 0;
        int day = 0;
        int total = Base - days;
        if (total <= 6) {
            correctedAge.setText("교정연령 : " + "0 주" + total + " 일");
        } else {
            while (total > 6) {
                day = total % 7;
                total = total - 7;
                week += 1;

            }
            correctedAge.setText("교정연령 : " + week + " 주" + day + " 일");
        }
    }

}