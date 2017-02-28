package com.coawesome.hosea.dr_r.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LeeMoonSeong on 2016-11-04.
 */
public class SplashActivity extends Activity {
    private AQuery aq = new AQuery(this);
    private static final int MY_READ_PHONE_STATE = 0;
    private String deviceId, u_name;
    private Handler mHandler;
    private int u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        u_id = 0;

        try {
            getUUID();
        } catch (Exception e) {
            checkPermission();
            mHandler = new Handler();

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getUUID();
                }
            }, 9000);
        }

    }

    public void responseCheck(JSONObject jsonObject) {
        try {
            u_id = jsonObject.getInt("num");
            u_name = jsonObject.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (u_id > 0) {
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            mainIntent.putExtra("u_id", u_id);
            mainIntent.putExtra("u_name", u_name);
            mainIntent.putExtra("u_device", deviceId);
            Toast.makeText(getApplicationContext(), u_name + "님 환영합니다.", Toast.LENGTH_SHORT).show();
            startActivity(mainIntent);
            SplashActivity.this.finish();
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.putExtra("u_device", deviceId);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }, 2000);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {    //사용자에게 디바이스 정보 받아오는거 확인
        String[] PERMISSIONS_STORAGE = {

                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE
        };
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(PERMISSIONS_STORAGE,MY_READ_PHONE_STATE);


        } else {
            // 다음 부분은 항상 허용일 경우에 해당이 됩니다.
            getUUID();
        }

    }

    @Override   //디바이스정보 접근 권한 확인
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("test", "It has permission of getUUID");
                    getUUID();
                    // permission was granted, yay! do the
                    // calendar task you need to do.
                } else {
                    Log.d("test", "Permission always deny");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private void getUUID() {   //디바이스 정보 받아오기
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        deviceId = deviceUuid.toString();

        Log.d("test", "Device UUID : " + deviceId);

        mHandler = new Handler();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u_device", deviceId);
        aq.ajax("http://52.41.218.18:8080/checkUserDevice", params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                if (html != null) {
                    Log.d("hosea","html is not null");
                    responseCheck(html);
                } else {
                    Toast.makeText(getApplicationContext(), "연결 상태가 좋지 않습니다.", Toast.LENGTH_SHORT).show();

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.putExtra("u_device", deviceId);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    }, 2500);
                }
            }
        });
    }
}