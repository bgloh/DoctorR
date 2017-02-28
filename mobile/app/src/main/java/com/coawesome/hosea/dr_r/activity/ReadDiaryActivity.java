package com.coawesome.hosea.dr_r.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;
import com.coawesome.hosea.dr_r.adapter.DiaryAdapter;
import com.coawesome.hosea.dr_r.dao.DiaryVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import im.dacer.androidcharts.ClockPieHelper;
import im.dacer.androidcharts.ClockPieView;

public class ReadDiaryActivity extends AppCompatActivity {
    ArrayList<ClockPieHelper> clockPieHelperArrayListForSleep;
    ArrayList<ClockPieHelper> clockPieHelperArrayListForFeed;
    ClockPieView pieView;
    ClockPieView pieView2;
    private Intent previousIntent;
    private AQuery aq = new AQuery(this);
    long result_sleep = 0;
    long result_feed = 0;

    int start_year = 0, start_month = 0, start_day = 0;
    int year, month, day;
    String date;
    TextView tv, today, sleepTotal, feedTotal , powderTotal;
    SimpleDateFormat dateFormat;
    DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        previousIntent = getIntent();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        clockPieHelperArrayListForSleep = new ArrayList<ClockPieHelper>();
        clockPieHelperArrayListForFeed = new ArrayList<ClockPieHelper>();
        //이름 설정
//        tv = (TextView) findViewById(R.id.tv_listView_title);
//        tv.setText(previousIntent.getStringExtra("u_name"));

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        tv = (TextView) findViewById(R.id.date);
        sleepTotal = (TextView) findViewById(R.id.tv_sleepTotal);
        feedTotal = (TextView) findViewById(R.id.tv_feedTotal);
        powderTotal = (TextView)findViewById(R.id.tv_powder);
        today = (TextView) findViewById(R.id.dateForList);

        date = year + "-" + (month + 1) + "-" + day + " ";

        findViewById(R.id.dateForList).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(ReadDiaryActivity.this, dateSetListener2, year, month, day);
                datePickerDialog2.show();
            }
        });

        pieView = (ClockPieView) findViewById(R.id.clock_pie_view);
        pieView2 = (ClockPieView) findViewById(R.id.clock_pie_view2);
        ArrayList<ClockPieHelper> pieHelperArrayListForSleep = new ArrayList<ClockPieHelper>();
        ArrayList<ClockPieHelper> pieHelperArrayListForFeed = new ArrayList<ClockPieHelper>();
        pieView.setDate(pieHelperArrayListForSleep);

        pieView2.setDate(pieHelperArrayListForFeed);

        today.setText(year + "년 " + (month + 1) + "월 " + day + "일 " + getDayKor());
        set();
        readDiary();

    }

    public int getUserId(){
        return previousIntent.getIntExtra("u_id",0);
    }

    private void set() {
        readSleep();
        readFeed();
    }

    public void readSleep() {
        result_sleep = 0;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", previousIntent.getIntExtra("u_id",0));
        params.put("s_start", date + "00:00:00");
        aq.ajax("http://52.41.218.18:8080/getSleepTimeByDate", params, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray html, AjaxStatus status) {
                if (html != null) {
                    try {
                        clockPieHelperArrayListForSleep.clear();
                        jsonArrayToSleepArray(html);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    tv.setText("해당하는 데이터가 없습니다.");
                }
            }
        });
    }

    public void readFeed() {
        result_feed = 0;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", previousIntent.getIntExtra("u_id",0));
        params.put("f_start", date + "00:00:00");
        aq.ajax("http://52.41.218.18:8080/getFeedTimeByDate", params, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray html, AjaxStatus status) {
                if (html != null) {
                    try {

                        clockPieHelperArrayListForFeed.clear();
                        jsonArrayToFeedArray(html);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    tv.setText("해당하는 데이터가 없습니다.");
                }
            }
        });
    }

    public void jsonArrayToSleepArray(JSONArray jsonArr) throws JSONException {
        final SimpleDateFormat curHourFormat = new SimpleDateFormat("HH", Locale.KOREA);
        curHourFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        final SimpleDateFormat curMinuteFormat = new SimpleDateFormat("mm", Locale.KOREA);
        final SimpleDateFormat curSecFormat = new SimpleDateFormat("ss", Locale.KOREA);
        for (int i = 0; i < jsonArr.length(); i++) {

            long s_time = Long.parseLong(jsonArr.getJSONObject(i).getString("s_start"));
            long e_time = Long.parseLong(jsonArr.getJSONObject(i).getString("s_end"));
            result_sleep += e_time - s_time;
            Date start = new Date(s_time);
            Date end = new Date(e_time);
            int s_hour = Integer.parseInt(curHourFormat.format(start));
            int s_min = Integer.parseInt(curMinuteFormat.format(start));
            int s_sec = Integer.parseInt(curSecFormat.format(start));
            int e_hour = Integer.parseInt(curHourFormat.format(end));
            int e_min = Integer.parseInt(curMinuteFormat.format(end));
            int e_sec = Integer.parseInt(curSecFormat.format(end));

            clockPieHelperArrayListForSleep.add(new ClockPieHelper(s_hour, s_min, s_sec, e_hour, e_min, e_sec));
            pieView.setDate(clockPieHelperArrayListForSleep);
        }
        sleepTotal.setText(result_sleep / 1000 / 3600 + " 시간 " + (result_sleep / 1000 % 3600) / 60 + " 분 " + (result_sleep / 1000 % 3600 % 60) + " 초 ");
    }

    //수유시간 리스트 받아오기
    public void jsonArrayToFeedArray(JSONArray jsonArr) throws JSONException {
        long result_powder = 0;
        final SimpleDateFormat curHourFormat = new SimpleDateFormat("HH", Locale.KOREA);
        curHourFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        final SimpleDateFormat curMinuteFormat = new SimpleDateFormat("mm", Locale.KOREA);
        final SimpleDateFormat curSecFormat = new SimpleDateFormat("ss", Locale.KOREA);
        for (int i = 0; i < jsonArr.length(); i++) {
            if(jsonArr.getJSONObject(i).getString("feed").equals("분유")){
                result_powder += Long.parseLong(jsonArr.getJSONObject(i).getString("f_total"));
                long powder_time = Long.parseLong(jsonArr.getJSONObject(i).getString("f_start"));
                Date powder_start = new Date(powder_time);
                int s_hour = Integer.parseInt(curHourFormat.format(powder_start));
                int s_min = Integer.parseInt(curMinuteFormat.format(powder_start));
                int s_sec = Integer.parseInt(curSecFormat.format(powder_start));
                clockPieHelperArrayListForFeed.add(new ClockPieHelper(s_hour, s_min, s_sec, s_hour, s_min+1, s_sec));
                pieView2.setDate(clockPieHelperArrayListForFeed);

            }
            else {
                long s_time = Long.parseLong(jsonArr.getJSONObject(i).getString("f_start"));
                long e_time = Long.parseLong(jsonArr.getJSONObject(i).getString("f_end"));
                result_feed += e_time - s_time;
                Date start = new Date(s_time);
                Date end = new Date(e_time);
                int s_hour = Integer.parseInt(curHourFormat.format(start));
                int s_min = Integer.parseInt(curMinuteFormat.format(start));
                int s_sec = Integer.parseInt(curSecFormat.format(start));
                int e_hour = Integer.parseInt(curHourFormat.format(end));
                int e_min = Integer.parseInt(curMinuteFormat.format(end));
                int e_sec = Integer.parseInt(curSecFormat.format(end));

                clockPieHelperArrayListForFeed.add(new ClockPieHelper(s_hour, s_min, s_sec, e_hour, e_min, e_sec));
                pieView2.setDate(clockPieHelperArrayListForFeed);
            }
        }
        feedTotal.setText(result_feed / 1000 / 3600 + " 시간 " + (result_feed / 1000 % 3600) / 60 + " 분 " + (result_feed / 1000 % 3600 % 60) + " 초 ");
        powderTotal.setText(result_powder+"ml");
    }

    public void readDiary() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", previousIntent.getIntExtra("u_id", 0));
        params.put("c_date", date + "00:00:00");
        aq.ajax("http://52.41.218.18:8080/getDiary", params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                if (html != null) {
                    jsonArrayToArrayList(html);
                } else {
                   Toast.makeText(getApplicationContext(),"해당날짜의 일지가 없습니다.",Toast.LENGTH_SHORT).show();
                    jsonArrayToArrayListNoData();
                }
            }
        });
    }

    public void jsonArrayToArrayList(JSONObject jsonObject) {
        ArrayList<DiaryVO> arrayList = new ArrayList<>();
        arrayList.add(new DiaryVO(jsonObject));
        linktoAdapter(arrayList);
    }

    public void jsonArrayToArrayListNoData( ) {
        ArrayList<DiaryVO> arrayList = new ArrayList<>();
        linktoAdapter(arrayList);
    }


    public void linktoAdapter(ArrayList<DiaryVO> list) {
        //어댑터 생성
        DiaryAdapter diaryAdapter = new DiaryAdapter(this, R.layout.itemsfordiarylist, list , previousIntent.getIntExtra("u_id",0));
        //어댑터 연결
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(diaryAdapter);
    }

    public static String getDayKor() {
        Calendar cal = Calendar.getInstance();
        int cnt = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String[] week = {"일", "월", "화", "수", "목", "금", "토"};

        return "( " + week[cnt] + " )";
    }

    public String getChangeDayKor() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, start_year);
        cal.set(Calendar.MONTH, start_month - 1);
        cal.set(Calendar.DATE, start_day);
        int cnt = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String[] week = {"일", "월", "화", "수", "목", "금", "토"};

        return "( " + week[cnt] + " )";
    }

    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            start_year = year;
            start_month = monthOfYear + 1;
            start_day = dayOfMonth;
            date = start_year + "-" + start_month + "-" + start_day + " ";
            clockPieHelperArrayListForSleep.clear();
            clockPieHelperArrayListForFeed.clear();
            pieView.setDate(clockPieHelperArrayListForSleep);
            pieView2.setDate(clockPieHelperArrayListForFeed);
            set();
            readDiary();
            today.setText(start_year + "년 " + (start_month) + "월 " + start_day + "일 " + getChangeDayKor());
        }
    };


}