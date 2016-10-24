package com.cs442.team2.smartbar;
import android.graphics.Bitmap;

public class FriendDetails {

    public int getImage() {
        return image;
    }
    public void setImage(int imageN) {
        this.image = imageN;
    }


    public String getMsgType() {
        return MsgType;
    }
    public void setMsgType(String text) {
        this.MsgType = text;
    }



    private int image;
    private String MsgType;


}
