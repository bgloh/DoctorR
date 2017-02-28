package com.coawesome.hosea.dr_r.activity;

import android.app.Application;

public class MyApplication extends Application {

    private int u_id;
    private String deviceId;
    @Override
    public void onCreate() {
        //전역 변수 초기화
        u_id = 0;
        deviceId = "";
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setU_id(int u_id){
       this.u_id = u_id;
    }

    public int getU_id(){
        return u_id;
    }

    public void setDeviceId(String deviceId){
        this.deviceId = deviceId;
    }

    public String getDeviceId(){
        return deviceId;
    }

}