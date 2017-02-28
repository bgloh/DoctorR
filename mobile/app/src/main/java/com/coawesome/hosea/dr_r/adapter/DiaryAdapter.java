package com.coawesome.hosea.dr_r.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;
import com.coawesome.hosea.dr_r.dao.DiaryVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Hosea on 2016-11-01.
 */

public class DiaryAdapter extends BaseAdapter {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    private Context dContext;
    private int dResource;
    private int dU_id;
    private TextView age;
    private ArrayList<DiaryVO> dItems = new ArrayList<>();
    private static final String RED = "#FF0000";
    private static final String BLUE = "#0000FF";
    AQuery mAq;


    public void getExpectedDate() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", dU_id);
        mAq.ajax("http://52.41.218.18:8080/getUserDate", params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                if (html != null) {
                    try {
                        calAge(html);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        });
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
            age.setText(getDateDifferenceInDDMMYYYY(expectedDate, nowDate));
        } else {                           //예정일과 계산날짜가 같은 경우
            age.setText( 0 + "(오늘 태어났습니다.)");
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
        return month + "\t개월\t\t" + day + "\t일";

    }

    public void measureDateLess(int days) {
        int Base = 280;

        int week = 0;
        int day = 0;
        int total = Base - days;
        if (total <= 6) {
            age.setText("0 주" + total + " 일");
        } else {
            while (total > 6) {
                day = total % 7;
                total = total - 7;
                week += 1;

            }
            age.setText( week + " 주" + day + " 일");
        }
    }

    public DiaryAdapter(Context context, int resource, ArrayList<DiaryVO> items , int u_id) {
        dContext = context;
        dResource = resource;
        dItems = items;
        dU_id = u_id;
    }

    @Override
    public int getCount() {
        return dItems.size();
    }

    @Override
    public Object getItem(int i) {
        return dItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) dContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(dResource, viewGroup, false);
        }
        mAq = new AQuery(view);
//        TextView breakfast = (TextView) view.findViewById(R.id.diary_tv_breakfast);
//        TextView lunch = (TextView) view.findViewById(R.id.diary_tv_lunch);
//        TextView dinner = (TextView) view.findViewById(R.id.diary_tv_dinner);
//        TextView temperature = (TextView) view.findViewById(R.id.diary_tv_temp);
//        TextView humid = (TextView) view.findViewById(R.id.diary_tv_humid);
//        TextView sleeptime = (TextView) view.findViewById(R.id.diary_tv_sleepTime);
//        TextView bloodPressure = (TextView) view.findViewById(R.id.diary_tv_bloodPressure);
//        TextView drinking = (TextView) view.findViewById(R.id.diary_tv_drinking);

        age = (TextView) view.findViewById(R.id.diary_tv_age);
        TextView weight = (TextView) view.findViewById(R.id.diary_tv_weight);
        TextView height = (TextView) view.findViewById(R.id.diary_tv_height);
        TextView memo = (TextView) view.findViewById(R.id.diary_tv_memo);
        TextView hospital = (TextView) view.findViewById(R.id.diary_tv_hospital);
        TextView treat = (TextView) view.findViewById(R.id.diary_tv_treat);
        TextView shot = (TextView) view.findViewById(R.id.diary_tv_shot);
        TextView next = (TextView) view.findViewById(R.id.diary_tv_next);
        TextView depart = (TextView) view.findViewById(R.id.diary_tv_depart);


        if (dItems != null) {
            DiaryVO diary = dItems.get(i);

            final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
            final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
            final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
            getExpectedDate();
            weight.setText(diary.getWeight() + "(kg)");
            height.setText(diary.getHeight() + "(cm)");
            memo.setText(diary.getMemo());

//            hospital.setBackgroundColor(0xFF00FF00);
            hospital.setText(diary.getHospital_name());
            treat.setText(diary.getTreat());
            shot.setText(diary.getShot());
            Date next_date = new Date();


            if (!diary.getNext().equals("0")) {
                try {
                    next_date = dateFormat.parse(diary.getNext());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long next_time = next_date.getTime();
                int year = Integer.parseInt(curYearFormat.format(next_date));
                int month = Integer.parseInt(curMonthFormat.format(next_date));
                int day = Integer.parseInt(curDayFormat.format(next_date));
                next.setText(year + "-" + month + "-" + day);
            }
            depart.setText(diary.getDepart());
            String IMG_URL = "http://52.41.218.18/storedimg/" + diary.getC_img();
            mAq.id(R.id.diary_iv_photo).image(IMG_URL);
        } else {
            age.setText("");
            weight.setText("");
            height.setText("");
            memo.setText("");
            hospital.setText("");
            treat.setText("");
            shot.setText("");
            next.setText("");
            depart.setText("");
        }

//        temperature.setText("" + diary.getTemperature());
//        if (diary.getTemperature() > 30) {
//           temperature.setTextColor(Color.parseColor(RED));
//        } else if (diary.getTemperature() < 10) {
//            temperature.setTextColor(Color.parseColor(BLUE));
//        }
//
//        humid.setText("" + diary.getHumid());
//
//        sleeptime.setText("" + diary.getSleepTime());
//        if (diary.getSleepTime() > 6) {
//            sleeptime.setTextColor(Color.parseColor(BLUE));
//        } else if (diary.getSleepTime() < 5) {
//            sleeptime.setTextColor(Color.parseColor(RED));
//        }
//
//        bloodPressure.setText("" + diary.getBloodPressure());
//        if (diary.getBloodPressure() >= 140) {
//            bloodPressure.setTextColor(Color.parseColor(RED));
//        } else if (diary.getBloodPressure() <= 100) {
//            bloodPressure.setTextColor(Color.parseColor(BLUE));
//        }
//
//        if (Integer.parseInt(String.valueOf(diary.getBloodPressure())) != 0) {
//            drinking.setTextColor(Color.parseColor(RED));
//            drinking.setText("O");
//        } else {
//            drinking.setTextColor(Color.parseColor(BLUE));
//            drinking.setText("X");
//        }

        return view;
    }
}
