package com.coawesome.hosea.dr_r.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;
import com.coawesome.hosea.dr_r.dao.DiaryVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WriteDiaryActivity extends AppCompatActivity {

    private AQuery aq = new AQuery(this);
    int year, month, day;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    private EditText weight, height;
    EditText memo, etc;
    Date diary_date;
    Spinner spinner_hospital;
    Spinner spinner_depart;
    Spinner spinner_shot;
    Date next_date;
    String date;
    Object depart, hospital_depart, shot;
    String diary_date_string;
    String next_date_string;
    private Intent previousIntent;
    private Button submit, addPhoto;
    private ImageView ivImg;
    boolean photo_has_changed;
    private Bitmap bitmapPhoto;
    String result;
    CheckBox fever, cough, diarrhea, cb_etc;
    TextView tv;
    TextView today;
    TextView age;
    int result_year = 0, result_month = 0, result_day = 0;
    int next_year = 0, next_month = 0, next_day = 0;
    int start_year = 0, start_month = 0, start_day = 0;
    private final int PICK_FROM_ALBUM = 1;
    TextView start;
    TextView end;
    private byte[] byteArray;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writediary);
        photo_has_changed = false;
        previousIntent = getIntent();
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        age = (TextView) findViewById(R.id.todayAge);
        tv = (TextView) findViewById(R.id.date);
        today = (TextView) findViewById(R.id.today);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        memo = (EditText) findViewById(R.id.memo);
        fever = (CheckBox) findViewById(R.id.fever);
        cough = (CheckBox) findViewById(R.id.cough);
        diarrhea = (CheckBox) findViewById(R.id.diarrhea);
        cb_etc = (CheckBox) findViewById(R.id.cb_etc);
        etc = (EditText) findViewById(R.id.et_etc);
        etc.setVisibility(View.GONE);

        cb_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_etc.isChecked()) {
                    etc.setVisibility(View.VISIBLE);
                } else if (!cb_etc.isChecked()) {
                    etc.setVisibility(View.GONE);
                    etc.setText("");
                }
            }
        });

        addPhoto = (Button) findViewById(R.id.add_photo);
        ivImg = (ImageView) findViewById(R.id.photo);

        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });


        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });


        //다음 진료 과
        spinner_hospital = (Spinner) findViewById(R.id.b_spinner1);
        final ArrayAdapter spinnerAdapter_hospital = ArrayAdapter.createFromResource(this,
                R.array.depart, android.R.layout.simple_spinner_item);
        spinnerAdapter_hospital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hospital.setAdapter(spinnerAdapter_hospital);

        spinner_hospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depart = parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                depart = parent.getItemAtPosition(0);
            }
        });
        //오늘 다녀온 과
        spinner_depart = (Spinner) findViewById(R.id.spinner_hospital);
        final ArrayAdapter spinnerAdapter_depart = ArrayAdapter.createFromResource(this,
                R.array.hospital, android.R.layout.simple_spinner_item);
        spinnerAdapter_depart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_depart.setAdapter(spinnerAdapter_depart);

        spinner_depart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hospital_depart = parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                hospital_depart = parent.getItemAtPosition(0);
            }
        });

        //접종
        spinner_shot = (Spinner) findViewById(R.id.spinner_shot);
        final ArrayAdapter spinnerAdapter_shot = ArrayAdapter.createFromResource(this,
                R.array.shot, android.R.layout.simple_spinner_item);
        spinnerAdapter_shot.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_shot.setAdapter(spinnerAdapter_shot);

        spinner_shot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shot = parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                shot = parent.getItemAtPosition(0);
            }
        });


        findViewById(R.id.date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(WriteDiaryActivity.this, dateSetListener, year, month, day);
                datePickerDialog1.show();
            }
        });

        findViewById(R.id.today).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(WriteDiaryActivity.this, dateSetListener2, year, month, day);
                datePickerDialog2.show();
            }
        });
        today.setText(year + "년 " + (month + 1) + "월 " + day + "일 " + getDayKor());

        //아무 선택없을때 db에 보낼 Date;
        result_year = year;
        result_month = month + 1;
        result_day = day;

        date = result_year + "-" + result_month + "-" + result_day + " ";


        readDiary();
        getExpectedDate();

        submit = (Button) findViewById(R.id.submit);
        submit.setText("등록");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    writeDiary();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void getExpectedDate() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", previousIntent.getIntExtra("u_id", 0));
        aq.ajax("http://52.41.218.18:8080/getUserDate", params, JSONObject.class, new AjaxCallback<JSONObject>() {
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


    public void readDiary() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_id", previousIntent.getIntExtra("u_id", 0));
        params.put("c_date", date + "00:00:00");
        aq.ajax("http://52.41.218.18:8080/getDiary", params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                if (html != null) {
                    inputDataForUpdate(html);
                } else {
                    Toast.makeText(getApplicationContext(), "해당하는 데이터가 없습니다", Toast.LENGTH_SHORT).show();
                    inputForNewData();

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
        return  year + "\t년\t\t" + month + "\t월\t\t" + day + "\t일";

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

    public void inputForNewData() {
        submit.setText("등록");
        weight.setText("");
        height.setText("");
        memo.setText("");
        fever.setChecked(false);
        cough.setChecked(false);
        diarrhea.setChecked(false);
        cb_etc.setChecked(false);
        etc.setText("");
        etc.setVisibility(View.GONE);
        tv.setText("날짜 선택");
        next_year = 0;
        next_month = 0;
        next_day = 0;
        spinner_depart.setSelection(0);
        hospital_depart = spinner_depart.getItemAtPosition(0);
        spinner_hospital.setSelection(0);
        depart = spinner_hospital.getItemAtPosition(0);
        spinner_shot.setSelection(0);
        shot = spinner_shot.getItemAtPosition(0);
        ivImg.setImageDrawable(null);
        addPhoto.setVisibility(addPhoto.VISIBLE);

    }

    public void inputDataForUpdate(JSONObject jsonObject) {
        submit.setText("수정");
        DiaryVO diaryVO = new DiaryVO(jsonObject);
        weight.setText(diaryVO.getWeight() + "");
        height.setText(diaryVO.getHeight() + "");
        memo.setText(diaryVO.getMemo());

        //질병 체크 박스
        String original_treat = diaryVO.getTreat();
        String[] splitValue = original_treat.split(",");
        for (int i = 0; i < splitValue.length; i++) {
            if (splitValue[i].equals("열")) {
                fever.setChecked(true);
            } else if (splitValue[i].equals("기침")) {
                cough.setChecked(true);
            } else if (splitValue[i].equals("설사")) {
                diarrhea.setChecked(true);
            } else if (splitValue[i].contains("기타")) {
                cb_etc.setChecked(true);
                etc.setVisibility(View.VISIBLE);
                String[] splitValueForEtc = original_treat.split(":");
                etc.setText(splitValueForEtc[1]);
            }
        }


        if (!diaryVO.getNext().equals("0")) {
            Date next_date = new Date();
            try {
                next_date = dateFormat.parse(diaryVO.getNext());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long next_time = next_date.getTime();

            final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
            final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
            final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
            next_date = new Date(next_time);
            int year = Integer.parseInt(curYearFormat.format(next_date));
            int month = Integer.parseInt(curMonthFormat.format(next_date));
            int day = Integer.parseInt(curDayFormat.format(next_date));
            tv.setText(year + "/" + month + "/" + day);

            next_year = year;
            next_month = month;
            next_day = day;
        } else {
            tv.setText("날짜 선택");
            next_year = 0;
            next_month = 0;
            next_day = 0;
        }

        //오늘 다녀온 과 스피너 선택 적용
        if (diaryVO.getHospital_name().equals("")) {
            spinner_depart.setSelection(0);
            hospital_depart = spinner_depart.getItemAtPosition(0);
        } else if (diaryVO.getHospital_name().equals("소아청소년과")) {
            spinner_depart.setSelection(1);
            hospital_depart = spinner_depart.getItemAtPosition(1);
        } else if (diaryVO.getHospital_name().equals("내과")) {
            spinner_depart.setSelection(2);
            hospital_depart = spinner_depart.getItemAtPosition(2);
        } else if (diaryVO.getHospital_name().equals("안과")) {
            spinner_depart.setSelection(3);
            hospital_depart = spinner_depart.getItemAtPosition(3);
        } else if (diaryVO.getHospital_name().equals("이비인후과")) {
            spinner_depart.setSelection(4);
            hospital_depart = spinner_depart.getItemAtPosition(4);
        } else if (diaryVO.getHospital_name().equals("재활의학과")) {
            spinner_depart.setSelection(5);
            hospital_depart = spinner_depart.getItemAtPosition(5);
        } else if (diaryVO.getHospital_name().equals("정형외과")) {
            spinner_depart.setSelection(6);
            hospital_depart = spinner_depart.getItemAtPosition(6);
        } else if (diaryVO.getHospital_name().equals("치과")) {
            spinner_depart.setSelection(7);
            hospital_depart = spinner_depart.getItemAtPosition(7);
        } else if (diaryVO.getHospital_name().equals("피부과")) {
            spinner_depart.setSelection(8);
            hospital_depart = spinner_depart.getItemAtPosition(8);
        } else {

        }

        //다음 진료 과 스피너 선택 적용
        if (diaryVO.getDepart().equals("")) {
            spinner_hospital.setSelection(0);
            depart = spinner_hospital.getItemAtPosition(0);
        } else if (diaryVO.getDepart().equals("소아청소년과")) {
            spinner_hospital.setSelection(1);
            depart = spinner_hospital.getItemAtPosition(1);
        } else if (diaryVO.getDepart().equals("내과")) {
            spinner_hospital.setSelection(2);
            depart = spinner_hospital.getItemAtPosition(2);
        } else if (diaryVO.getDepart().equals("안과")) {
            spinner_hospital.setSelection(3);
            depart = spinner_hospital.getItemAtPosition(3);
        } else if (diaryVO.getDepart().equals("이비인후과")) {
            spinner_hospital.setSelection(4);
            depart = spinner_hospital.getItemAtPosition(4);
        } else if (diaryVO.getDepart().equals("재활의학과")) {
            spinner_hospital.setSelection(5);
            depart = spinner_hospital.getItemAtPosition(5);
        } else if (diaryVO.getDepart().equals("정형외과")) {
            spinner_hospital.setSelection(6);
            depart = spinner_hospital.getItemAtPosition(6);
        } else if (diaryVO.getDepart().equals("치과")) {
            spinner_hospital.setSelection(7);
            depart = spinner_hospital.getItemAtPosition(7);
        } else if (diaryVO.getDepart().equals("피부과")) {
            spinner_hospital.setSelection(8);
            depart = spinner_hospital.getItemAtPosition(8);
        } else {

        }

        //접종 드롭다운
        if (diaryVO.getShot().equals("")) {
            spinner_shot.setSelection(0);
            shot = spinner_shot.getItemAtPosition(0);
        } else if (diaryVO.getShot().equals("일본뇌염")) {
            spinner_shot.setSelection(1);
            shot = spinner_shot.getItemAtPosition(1);
        } else if (diaryVO.getShot().equals("수두")) {
            spinner_shot.setSelection(2);
            shot = spinner_shot.getItemAtPosition(2);
        } else if (diaryVO.getShot().equals("A형간염")) {
            spinner_shot.setSelection(3);
            shot = spinner_shot.getItemAtPosition(3);
        } else if (diaryVO.getShot().equals("B형간염")) {
            spinner_shot.setSelection(4);
            shot = spinner_shot.getItemAtPosition(4);
        } else if (diaryVO.getShot().equals("폴리오")) {
            spinner_shot.setSelection(5);
            shot = spinner_shot.getItemAtPosition(5);
        } else if (diaryVO.getShot().equals("BCG")) {
            spinner_shot.setSelection(6);
            shot = spinner_shot.getItemAtPosition(6);
        } else if (diaryVO.getShot().equals("DTaP")) {
            spinner_shot.setSelection(7);
            shot = spinner_shot.getItemAtPosition(7);
        } else if (diaryVO.getShot().equals("Rotavirus")) {
            spinner_shot.setSelection(8);
            shot = spinner_shot.getItemAtPosition(8);
        } else if (diaryVO.getShot().equals("MMR")) {
            spinner_shot.setSelection(9);
            shot = spinner_shot.getItemAtPosition(9);
        } else if (diaryVO.getShot().equals("PCV")) {
            spinner_shot.setSelection(10);
            shot = spinner_shot.getItemAtPosition(10);
        } else if (diaryVO.getShot().equals("Hib")) {
            spinner_shot.setSelection(11);
            shot = spinner_shot.getItemAtPosition(11);
        } else {

        }


        if (!diaryVO.getC_img().equals(null) && !diaryVO.getC_img().equals("") && !diaryVO.getC_img().equals("null")) {
            String IMG_URL = "http://52.41.218.18/storedimg/" + diaryVO.getC_img();
            aq.id(R.id.photo).image(IMG_URL);
            addPhoto.setVisibility(addPhoto.GONE);
        } else {
            ivImg.setImageDrawable(null);
            addPhoto.setVisibility(View.VISIBLE);
        }


    }

    public void writeDiary() throws ParseException {
        Map<String, Object> params = new HashMap<String, Object>();
        diary_date_string = result_year + "-" + result_month + "-" + result_day + " " + "00:00:00";
        if (next_year != 0) {
            next_date_string = next_year + "-" + next_month + "-" + next_day + " " + "00:00:00";
            next_date = dateFormat.parse(next_date_string);
            params.put("c_next", dateFormat.format(next_date));
        } else {
            params.put("c_next", next_year);
        }
        diary_date = dateFormat.parse(diary_date_string);

        if (depart.toString().equals("선택")) {
            params.put("c_depart", "");
        } else {
            params.put("c_depart", depart.toString());
        }

        if (hospital_depart.toString().equals("선택")) {
            params.put("c_hospital", "");
        } else {
            params.put("c_hospital", hospital_depart.toString());
        }

        if (shot.toString().equals("선택")) {
            params.put("c_shot", "");
        } else {
            params.put("c_shot", shot.toString());
        }

        params.put("u_id", previousIntent.getIntExtra("u_id", 0));
        params.put("c_memo", memo.getText().toString());
        params.put("c_w", Double.parseDouble(weight.getText().toString()));
        params.put("c_h", Double.parseDouble(height.getText().toString()));
        params.put("c_treat", printdisease());

        params.put("c_date", dateFormat.format(diary_date));


        //c_date 보내기


        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapPhoto = ((BitmapDrawable) ivImg.getDrawable()).getBitmap();
            bitmapPhoto.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byteArray = stream.toByteArray();
            params.put("file", byteArray);
            params.put("c_img", fileName);
            aq.ajax("http://52.41.218.18:8080/writeDiaryWithImg", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject html, AjaxStatus status) {
                    if (html != null) {
                        Toast.makeText(getApplicationContext(), "등록 완료", Toast.LENGTH_SHORT).show();
                        WriteDiaryActivity.this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "네트워크 연결 상태가 좋지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (NullPointerException ignored) {
            aq.ajax("http://52.41.218.18:8080/writeDiary", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject html, AjaxStatus status) {
                    if (html != null) {
                        Toast.makeText(getApplicationContext(), "등록 완료", Toast.LENGTH_SHORT).show();
                        WriteDiaryActivity.this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "네트워크 연결 상태가 좋지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_FROM_ALBUM) {
            if (data != null) {
                addPhoto.setVisibility(addPhoto.GONE);
                Uri mImageCaptureUri = data.getData();
                bitmapPhoto = null;
                try {
                    bitmapPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageCaptureUri);
                    Cursor c = getContentResolver().query(Uri.parse(mImageCaptureUri.toString()), null, null, null, null);
                    c.moveToNext();
                    String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                    fileName = absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
                    if (bitmapPhoto != null) {
                        ivImg.setImageBitmap(bitmapPhoto);
                        photo_has_changed = true;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            start_year = year;
            start_month = monthOfYear + 1;
            start_day = dayOfMonth;

            tv.setText(start_year + "/" + start_month + "/" + start_day);
            next_year = start_year;
            next_month = start_month;
            next_day = start_day;

        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            start_year = year;
            start_month = monthOfYear + 1;
            start_day = dayOfMonth;
            today.setText(start_year + "년 " + (start_month) + "월 " + start_day + "일 " + getChangeDayKor());
            //날짜 선택후  db에 보탤 Date;
            result_year = start_year;
            result_month = start_month;
            result_day = start_day;

            date = result_year + "-" + result_month + "-" + result_day + " ";
            readDiary();
        }
    };

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

    public String printdisease() {
        result = "";
        if (fever.isChecked()) {
            result += fever.getText().toString() + ",";
        }
        if (cough.isChecked()) {
            result += cough.getText().toString() + ",";
        }
        if (diarrhea.isChecked()) {
            result += diarrhea.getText().toString() + ",";
        }
        if (cb_etc.isChecked()) {
            result += cb_etc.getText().toString() + ":" + etc.getText();
        }
        return result;
    }
}