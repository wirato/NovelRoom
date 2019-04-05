package com.example.novelroomverbeta;

import java.io.Serializable;

public class DataRegister implements Serializable {

    protected static String username;
    private String eMail;
    private String password;

    private static  String re_username;
    private static  String re_password;

    private int loginid;

    private static boolean re;

    public DataRegister(){    }

    public DataRegister(String username,String eMail){
        this.username = username;
        this.eMail = eMail;
    }
    public DataRegister(boolean re,String re_username,String re_password){
        this.setRe(re);
        this.re_username = re_username;
        this.re_password = re_password;
    }

    public DataRegister(String username,String eMail,String password){
        this.username = username;
        this.eMail = eMail;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getRe_username() {        return re_username;    }

    public void setRe_username(String re_username) {        this.re_username = re_username;    }

    public String getRe_password() {        return re_password;    }

    public void setRe_password(String re_password) {        this.re_password = re_password;    }

    public boolean isRe() {        return re;    }

    public void setRe(boolean re) {        this.re = re;    }

    public int getLoginid() {        return loginid;    }

    public void setLoginid(int loginid) {        this.loginid = loginid;    }
}
