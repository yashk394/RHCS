package com.example.rhcs;

import com.google.firebase.database.Exclude;

public class Uploadimage {
    public String imgname;
    public String imgurl;
    public String mkey;

    public Uploadimage() {

    }

    public Uploadimage(String imgname, String imgurl) {

        if (imgname.trim().equals("")) {
        }
        this.imgname = imgname;
        this.imgurl = imgurl;

    }

    public String getImgname() {
        return imgname;
    }

    public void setName(String imgname) {
        this.imgname = imgname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Exclude
    public String getkey() {
        return mkey;
    }

    @Exclude
    public void setkay(String key) {
        mkey = key;
    }
}
