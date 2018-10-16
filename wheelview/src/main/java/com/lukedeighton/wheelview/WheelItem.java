package com.lukedeighton.wheelview;

import android.graphics.drawable.Drawable;

/**
 * Created by Shamyyoun on 3/10/2015.
 */
public class WheelItem {
    private String title;
    private Drawable icon;

    public WheelItem() {
    }

    public WheelItem(String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
