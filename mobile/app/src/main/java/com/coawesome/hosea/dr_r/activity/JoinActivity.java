package com.coawesome.hosea.dr_r.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class JoinActivity extends AppCompatActivity {

    private AQuery aq = new AQuery(this);
    private EditText login_id, name, password1, password2;
    private Date born_date;
    private Date ex_date;
    private EditText a_week, a_date;
    private EditText b_weight, b_height;
    private boolean idChecked = false;
    int year = 0, month = 0, day = 0;
    int year2 = 0, month2 = 0, day2 = 0;
    int result_year = 0, result_month = 0, result_day = 0;   // 출생일
    int ex_year = 0, ex_month = 0, ex_day = 0;   //예정일
    private TextView u_born;
    private TextView u_expected;
    private String born_string = "";
    private String ex_string = "";
    private static final int MY_READ_PHONE_STATE = 0;
    private Button checkId, submit;
    private String array, deviceId;
    private String[] string;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        GregorianCalendar calendar = new GregorianCalendar();
        GregorianCalendar calendar2 = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year2 = calendar2.get(Calendar.YEAR);
        month2 = calendar2.get(Calendar.MONTH);
        day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        try {
            getUUID();
        } catch (Exception e) {
            checkPermission();
        }

        a_week = (EditText) findViewById(R.id.a_week);
        a_date = (EditText) findViewById(R.id.a_date);
        b_weight = (EditText) findViewById(R.id.b_weight);
        b_height = (EditText) findViewById(R.id.b_height);
        login_id = (EditText) findViewById(R.id.login_id);
        name = (EditText) findViewById(R.id.user_name);
        u_born = (TextView) findViewById(R.id.u_born);
        u_expected = (TextView)findViewById(R.id.u_expected);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        checkId = (Button) findViewById(R.id.checkId);
        submit = (Button) findViewById(R.id.joinSubmit);

        login_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                idChecked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
                idChecked = false;
            }
        });

        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioForSex);


        checkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("login_id", login_id.getText().toString());
                aq.ajax("http://52.41.218.18:8080/checkLoginId", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject html, AjaxStatus status) {
                        try {
                            Toast.makeText(getApplicationContext(), html.getString("msg"), Toast.LENGTH_SHORT).show();
                            if (html.getString("msg").startsWith("사용가능한")) {
                                idChecked = true;
                            } else {
                                idChecked = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        u_born.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(JoinActivity.this, dateSetListener, year, month, day);
                datePickerDialog1.show();
            }
        });

        u_expected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(JoinActivity.this, dateSetListener2, year2, month2, day2);
                datePickerDialog2.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                born_date = null;
                ex_date = null;
                try {
                    born_date = dateFormat.parse(born_string);
                    ex_date = dateFormat.parse(ex_string);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //비밀번호 테스트
                if ((login_id.getText().toString().equals("")) || login_id.getText().toString().length() < 5) {
                    Toast.makeText(getApplicationContext(), "id를 다시 입력하세요. (5자 이상)", Toast.LENGTH_SHORT).show();
                    login_id.setText("");
                    login_id.requestFocus();
                } else if (!idChecked) {
                    Toast.makeText(getApplicationContext(), "아이디를 체크하세요", Toast.LENGTH_SHORT).show();
                    login_id.requestFocus();
                } else if (!password1.getText().toString().equals(password2.getText().toString()) || password1.getText().toString().length() < 5) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요. (5자 이상)", Toast.LENGTH_SHORT).show();
                    password1.requestFocus();
                } else if ((name.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "아기 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    name.requestFocus();
                } else if ((a_week.getText().toString().equals("")) || (a_date.getText().toString()==null)) {
                    Toast.makeText(getApplicationContext(), "출생 주 수를 입력하세요.", Toast.LENGTH_SHORT).show();
                    a_week.requestFocus();
                } else if (ex_string.equals("")) {
                    Toast.makeText(getApplicationContext(), "출생 예정일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    u_expected.requestFocus();
                } else if (born_string.equals("")) {
                    Toast.makeText(getApplicationContext(), "출생일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    u_born.requestFocus();
                } else if (rb.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "성별을 입력하세요.", Toast.LENGTH_SHORT).show();
                    rb.requestFocus();
                } else if ((b_weight.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "출생 몸무게를 입력하세요.", Toast.LENGTH_SHORT).show();
                    b_weight.setText("");
                    b_weight.requestFocus();
                } else if ((b_height.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "출생 키를 입력하세요.", Toast.LENGTH_SHORT).show();
                    b_height.setText("");
                    b_height.requestFocus();
                }  else {
                    submit.setEnabled(false);
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("login_id", login_id.getText().toString());
                    params.put("u_password", password1.getText().toString());
                    params.put("u_name", name.getText().toString());
                    params.put("u_a_week", a_week.getText().toString());
                    params.put("u_a_date", a_date.getText().toString());
                    params.put("u_expected" , dateFormat.format(ex_date));
                    params.put("u_born", dateFormat.format(born_date));
                    params.put("u_w", b_weight.getText().toString());
                    params.put("u_h", b_height.getText().toString());
                    params.put("u_sex", rb.getText().toString());
                    params.put("u_device", deviceId);

                    aq.ajax("http://52.41.218.18:8080/joinUser", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject html, AjaxStatus status) {
                            if (html != null) {
                                try {
                                    Toast.makeText(getApplicationContext(), html.getString("msg"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                {
                                    intent.putExtra("u_device", deviceId);
                                    startActivity(intent);
                                    JoinActivity.this.finish();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "연결 상태가 좋지 않습니다.", Toast.LENGTH_SHORT).show();
                                submit.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });
    }

    private void getUUID() {   //디바이스 정보 받아오기
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        deviceId = deviceUuid.toString();

    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub

            result_year = year;
            result_month = monthOfYear+1;
            result_day = dayOfMonth;

            u_born.setText(result_year+"년 "+ result_month+"월 "+result_day+"일");
            born_string = result_year + "-" + result_month + "-" + result_day + " " + "00:00:00";
        }
    };
    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub

            ex_year = year;
            ex_month = monthOfYear+1;
            ex_day = dayOfMonth;

            u_expected.setText(ex_year+"년 "+ ex_month+"월 "+ex_day+"일");
            ex_string = ex_year + "-" + ex_month + "-" + ex_day + " " + "00:00:00";
        }
    };


    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {    //사용자에게 디바이스 정보 받아오는거 확인
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_READ_PHONE_STATE);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            // 다음 부분은 항상 허용일 경우에 해당이 됩니다.
            getUUID();
        }

    }

}