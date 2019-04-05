package com.example.novelroomverbeta;

import java.io.Serializable;

public class DataBook extends DataRegister implements Serializable {

    private String novelname;
    private String textdata;
    private String url;
    private String ep;
    private String username;
    private String EPdataText;
    private int a;

    public DataBook(){
    }

    public DataBook(String novelname,String ep, String EPdataText,String username,int a){
        this.novelname = novelname;
        this.ep = ep;
        this.EPdataText = EPdataText;
        this.username = username;
        this.a = a;
    }

    public DataBook(String novelname,String textdata ,String username, String url) {
        this.novelname = novelname;
        this.textdata = textdata;
        this.username = username;
        this.url = url;
    }

    public String getNovelname() {        return novelname;    }
    public void setNovelname(String novelname) {        this.novelname = novelname;    }

    public String getTextdata() {        return textdata;    }
    public void setTextdata(String textdata) {        this.textdata = textdata;    }

    public String getUsername() {        return username;    }
    public void setUsername(String username) {        this.username = username;    }

    public String getUrl() {        return url;    }
    public void setUrl(String url) {        this.url = url;    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public String getEPdataText() {
        return EPdataText;
    }

    public void setEPdataText(String EPdataText) {
        this.EPdataText = EPdataText;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
