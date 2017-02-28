package com.coawesome.hosea.dr_r.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

public class FeedVO {
    private int f_id;
    private int u_id;
    private String u_name;
    private Timestamp f_start;
    private Timestamp f_end;
    private int f_total;
    private String feed;

    public FeedVO(int f_id, int u_id, String u_name, Timestamp f_start, Timestamp f_end, int f_total, String feed) {
        this.f_id = f_id;
        this.u_id = u_id;
        this.u_name = u_name;
        this.f_start = f_start;
        this.f_end = f_end;
        this.f_total = f_total;
        this.feed = feed;
    }

    public FeedVO(JSONObject jsonObject) {
        try {
            this.f_id = jsonObject.getInt("f_id");
            this.u_id = jsonObject.getInt("u_id");
            Long s_feed = Long.parseLong(jsonObject.getString("f_start"));
            Long e_feed = Long.parseLong(jsonObject.getString("f_end"));
            f_start = new Timestamp(s_feed);
            f_end = new Timestamp(e_feed);
            f_total = jsonObject.getInt("f_total");
            feed = jsonObject.getString("feed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
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

    public Timestamp getF_start() {
        return f_start;
    }

    public void setF_start(Timestamp f_start) {
        this.f_start = f_start;
    }

    public Timestamp getF_end() {
        return f_end;
    }

    public void setF_end(Timestamp f_end) {
        this.f_end = f_end;
    }

    public int getF_total() {
        return f_total;
    }

    public void setF_total(int f_total) {
        this.f_total = f_total;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}

