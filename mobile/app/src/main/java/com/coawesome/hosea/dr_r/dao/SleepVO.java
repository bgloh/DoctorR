package com.coawesome.hosea.dr_r.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;


public class SleepVO {
    private int s_id;
    private int u_id;
    private String u_name;
    private Timestamp s_start;
    private Timestamp s_end;
    private int s_total;

    public SleepVO(int s_id, int u_id, String u_name, Timestamp s_start, Timestamp s_end, int s_total) {
        this.s_id = s_id;
        this.u_id = u_id;
        this.u_name = u_name;
        this.s_start = s_start;
        this.s_end = s_end;
        this.s_total = s_total;
    }

    public SleepVO(JSONObject jsonObject) {
        try {
            this.s_id = jsonObject.getInt("s_id");
            this.u_id = jsonObject.getInt("u_id");
            Long s_sleep = Long.parseLong(jsonObject.getString("s_start"));
            Long e_sleep = Long.parseLong(jsonObject.getString("s_end"));
            this.s_start = new Timestamp(s_sleep);
            this.s_end = new Timestamp(e_sleep);
            this.s_total = jsonObject.getInt("s_total");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public Timestamp getS_start() {
        return s_start;
    }

    public void setS_start(Timestamp s_start) {
        this.s_start = s_start;
    }

    public Timestamp getS_end() {
        return s_end;
    }

    public void setS_end(Timestamp s_end) {
        this.s_end = s_end;
    }

    public int getS_total() {
        return s_total;
    }

    public void setS_total(int s_total) {
        this.s_total = s_total;
    }
}