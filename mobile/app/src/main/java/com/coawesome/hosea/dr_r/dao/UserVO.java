package com.coawesome.hosea.dr_r.dao;

/**
 * Created by Hosea on 2016-11-03.
 */

public class UserVO {
    private int u_id;
    private String login_id;
    private String u_name;
    private String u_phone;
    private String u_password;
    private String u_disease;
    private String u_device;
    private String u_hospital;

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String geU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_disease() {
        return u_disease;
    }

    public void setU_disease(String u_disease) {
        this.u_disease = u_disease;
    }

    public String getU_hospital() {
        return u_hospital;
    }
    public void setU_hospital(String u_hospital) {
        this.u_hospital = u_hospital;
    }
    public String getU_device(){return u_device;}

    public void setU_device(String u_device){this.u_device = u_device;}
}
