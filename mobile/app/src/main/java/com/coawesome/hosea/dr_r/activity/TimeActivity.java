package com.coawesome.hosea.dr_r.activity;

/**
 * Created by LeeMoonSeong on 2016-12-14.
 */

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.androidquery.AQuery;
import com.coawesome.hosea.dr_r.R;
import com.coawesome.hosea.dr_r.fragment.FeedTimeFragment;
import com.coawesome.hosea.dr_r.fragment.SleepTimeFragment;


public class TimeActivity extends AppCompatActivity {
    private Intent previousIntent;
    private AQuery aq = new AQuery(this);
    Button sleeping;
    Button feeding;
    final SleepTimeFragment sleepTimeFragment = new SleepTimeFragment();
    final FeedTimeFragment feedTimeFragment = new FeedTimeFragment();
    final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    public int u_id;

    //    FragmentManager fm = getFragmentManager();
//    TextView myOutput;
//    TextView myToday;
//    TextView myToggle;
//    long startTime ,endTime;
//    Date s_start;
//    Date s_end;
//    long outTime;
//    final static int Init = 0;
//    final static int Run = 1;
//    final static int Pause = 2;
//    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
//    long myBaseTime;
//    long myPauseTime;
//    final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
//    final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
//    final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
//    Date date = new Date();
//    int year, month, day;
//    SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        previousIntent = getIntent();
        u_id = previousIntent.getExtras().getInt("u_id",0);
         sleeping = (Button) findViewById(R.id.measureSleepingTime);
          feeding = (Button) findViewById(R.id.measureFeedingTime);


        sleeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentTransaction.replace(R.id.time_fragment, sleepTimeFragment).commit();
            }
        });

        feeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentTransaction.replace(R.id.time_fragment, feedTimeFragment).commit();
            }
        });
        sleeping.setAlpha(1);
        feeding.setAlpha(0.3f);
//        myOutput = (TextView) findViewById(R.id.time_out);
//        myToday = (TextView) findViewById(R.id.today_sleep);
//        myToggle = (TextView) findViewById(R.id.sleep_toggle);
//
//        //현재 날짜 받아오기
//        // 년,월,일로 쪼갬
//        year = Integer.parseInt(curYearFormat.format(date));
//        month =Integer.parseInt(curMonthFormat.format(date));
//        day = Integer.parseInt(curDayFormat.format(date));
//
//          myToday.setText(year+"년 "+(month)+"월 "+day+"일 "+ getDayKor() );

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


    protected void switchFragment(int id) {
        final android.app.FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        if (id == 0) {
            fragmentTransaction.replace(R.id.time_fragment, sleepTimeFragment).addToBackStack(null);
        }
        else if (id == 1) {
            fragmentTransaction.replace(R.id.time_fragment, sleepTimeFragment).addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

//    public void myOnClick(View v) {
//        switch (v.getId()) {
//            case R.id.sleep_toggle: //시작버튼을 클릭했을때 현재 상태값에 따라 다른 동작을 할수있게끔 구현.
//                switch (cur_Status) {
//                    case Init:
//                        myBaseTime = SystemClock.elapsedRealtime();
//                        s_start = new Date();
//                        startTime = s_start.getTime()/1000;
//                        System.out.println(myBaseTime);
//                        //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
//                        myTimer.sendEmptyMessage(0);
//                        myToggle.setText("기록 중지"); //버튼의 문자"시작"을 "멈춤"으로 변경
//
//                        cur_Status = Run; //현재상태를 런상태로 변경
//                        break;
//                    case Run:
//                        myTimer.removeMessages(0); //핸들러 메세지 제거
//                        myPauseTime = SystemClock.elapsedRealtime();
//                        s_end = new Date();
//                        endTime = s_end.getTime() /1000;
//                        myToggle.setText("기록 시작");
//                        cur_Status = Pause;
//                        outTime = 0;
//                        String easy_outTime = String.format("%02d:%02d:%02d", outTime / 1000 / 3600 , (outTime / 1000 % 3600) / 60, (outTime / 1000 %3600 %60 ));
//                        myOutput.setText(easy_outTime);
//                        writeDiary();
//                        break;
//                    case Pause:
//                        myBaseTime = SystemClock.elapsedRealtime();
//                        s_start = new Date();
//                        startTime = s_start.getTime()/1000;
//                        myTimer.sendEmptyMessage(0);
//                        myToggle.setText("기록 중지");
//                        cur_Status = Run;
//                        break;
//                }
//                break;
//        }
//    }
//
//    Handler myTimer = new Handler() {
//        public void handleMessage(Message msg) {
//            myOutput.setText(getTimeOut());
//
//            //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
//            myTimer.sendEmptyMessage(0);
//        }
//    };
//
//    //현재시간을 계속 구해서 출력하는 메소드
//    String getTimeOut() {
//        long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간(??)^^;
//        outTime = now - myBaseTime;
//        String easy_outTime = String.format("%02d:%02d:%02d", outTime / 1000 / 3600 , (outTime / 1000 % 3600) / 60, (outTime / 1000 %3600 %60 ));
//        return easy_outTime;
//
//    }
//
//    public static String getDayKor(){
//        Calendar cal = Calendar.getInstance();
//        int cnt = cal.get(Calendar.DAY_OF_WEEK) - 1;
//        String[] week = { "일", "월", "화", "수", "목", "금", "토" };
//
//        return "( "+week[cnt]+" )";
//    }
//    public void writeDiary() {
//        Map<String, Object> params = new HashMap<String, Object>();
//        //params.put("u_id", previousIntent.getIntExtra("u_id", 0));
//        params.put("u_id" , previousIntent.getIntExtra("u_id", 0));
//        params.put("s_start" , dateFormat.format(s_start));
//        params.put("s_end" , dateFormat.format(s_end));
//        params.put("s_total" , endTime-startTime);
//        aq.ajax("http://172.30.1.48:8080/addSleepTime", params, JSONObject.class, new AjaxCallback<JSONObject>() {
//            @Override
//            public void callback(String url, JSONObject html, AjaxStatus status) {
//                try {
//                    if (html.getString("msg").equals("정상 작동")) {
//                        Toast.makeText(getApplicationContext(), "작성 되었습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}