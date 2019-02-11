package mhk.com.presentation.uimodel;

import java.io.Serializable;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public class ForecastViewData implements Serializable {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private Temperature mTemperature;
    private String mWeatherDescription;
    private String mPressure;
    private String mHumidity;
    private String mWindSpeed;
    private String mDateString;
    private int mBackground;
    private int mForeground;
    private boolean mIsDayTime;
    private String htmlFormattedWeatherText;

    public Temperature getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Temperature mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getWeatherDescription() {
        return mWeatherDescription;
    }

    public void setWeatherDescription(String mWeatherDescription) {
        this.mWeatherDescription = mWeatherDescription;
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String mPressure) {
        this.mPressure = mPressure;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String mHumidity) {
        this.mHumidity = mHumidity;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(String mWindSpeed) {
        this.mWindSpeed = mWindSpeed;
    }

    public String getDateString() {
        return mDateString;
    }

    public void setDateString(String mDateString) {
        this.mDateString = mDateString;
    }

    public int getBackground() {
        return mBackground;
    }

    public void setBackground(int mBackground) {
        this.mBackground = mBackground;
    }

    public int getForeground() {
        return mForeground;
    }

    public void setForeground(int mForeground) {
        this.mForeground = mForeground;
    }

    public boolean isIsDayTime() {
        return mIsDayTime;
    }

    public void setIsDayTime(boolean mIsDayTime) {
        this.mIsDayTime = mIsDayTime;
    }

    public String getHtmlFormattedWeatherText() {
        return htmlFormattedWeatherText;
    }

    public void setHtmlFormattedWeatherText(String htmlFormattedWeatherText) {
        this.htmlFormattedWeatherText = htmlFormattedWeatherText;
    }

    public static class Temperature implements Serializable {

        public Temperature(String temp, String tempMax, String tempMin) {
            this.temp = temp;
            this.tempMax = tempMax;
            this.tempMin = tempMin;
        }
        public String temp;
        public String tempMax;
        public String tempMin;
    }
}
