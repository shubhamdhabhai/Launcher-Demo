package com.demo.shubhamdhabhai.launcherdemo;

import android.graphics.drawable.Drawable;

/**
 * Created by shubhamdhabhai on 29/01/18.
 */

public class App {

    private String appName;
    private String appPackageName;

    private Drawable icon;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
