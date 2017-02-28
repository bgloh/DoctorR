package com.coawesome.hosea.dr_r.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Hosea on 2016-11-09.
 */
public class QnaVO {
    private int u_id;
    private String u_name;
    private String qna_title;
    private String qna_content;
    private String date;
    private int count;

    public QnaVO(JSONObject jsonObject) {
        try {
            u_id = jsonObject.getInt("u_id");
            u_name = jsonObject.getString("u_name");
            qna_title = jsonObject.getString("qna_title");
            qna_content = jsonObject.getString("qna_content");
            Timestamp timestamp = new Timestamp(jsonObject.getLong("date"));
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            date = format.format(new Date(timestamp.getTime()));
            count = jsonObject.getInt("count");
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    //테스트를 위한 생성자
    public QnaVO(int id, String name, String title, String content, String getdate, int getcount) {
        u_id = id;
        u_name = name;
        qna_title = title;
        qna_content = content;
        date = getdate;
        count = getcount;
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

    public String getQna_title() {
        return qna_title;
    }

    public void setQna_title(String qna_title) {
        this.qna_title = qna_title;
    }

    public String getQna_content() {
        return qna_content;
    }

    public void setQna_content(String qna_content) {
        this.qna_content = qna_content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}