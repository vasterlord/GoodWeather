package com.example.yulian.goodweather.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HourlyWeather implements Parcelable {
    private double mTemperature;
    private long mTimestamp;
    private String mIcon;
    private String mDescription;
    private String mCondition;
    private int mWeatherId;
    private double mPressure;
    private double mHumidity;
    private double mWindSpeed;
    private double mWindDegree;
    private double mClouds;
    private String mDateTxt;
    private double mGroundLevel;
    private double mSeaLevel;

    protected HourlyWeather(Parcel in) {
        mTemperature = in.readDouble();
        mTimestamp = in.readLong();
        mIcon = in.readString();
        mDescription = in.readString();
        mCondition = in.readString();
        mWeatherId = in.readInt();
        mPressure = in.readDouble();
        mHumidity = in.readDouble();
        mWindSpeed = in.readDouble();
        mWindDegree = in.readDouble();
        mClouds = in.readDouble();
        mDateTxt = in.readString();
        mGroundLevel = in.readDouble();
        mSeaLevel = in.readDouble();
    }

    public static final Creator<HourlyWeather> CREATOR = new Creator<HourlyWeather>() {
        @Override
        public HourlyWeather createFromParcel(Parcel in) {
            return new HourlyWeather(in);
        }

        @Override
        public HourlyWeather[] newArray(int size) {
            return new HourlyWeather[size];
        }
    };

    public double getGroundLevel() {
        return mGroundLevel;
    }

    public void setGroundLevel(double groundLevel) {
        mGroundLevel = groundLevel;
    }

    public double getSeaLevel() {
        return mSeaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        mSeaLevel = seaLevel;
    }

    public HourlyWeather(){}


    public String getDateTxt() {
        return mDateTxt;
    }

    public void setDateTxt(String dateTxt) {
        mDateTxt = dateTxt;
    }
    public String getDateTxtToHour()
    {
        String str = new String(getDateTxt());
        String time = str.split("\\s")[1].split("\\.")[0];
        return time;
    }

    public String getHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        Date date = new Date((mTimestamp * 1000));
        return formatter.format(date);
    }
    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temp) {
        mTemperature = temp;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }
    public int getIconId() {
        return Forecast.getFullIconId(getIcon());
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String condition) {
        mCondition = condition;
    }

    public int getWeatherId() {
        return mWeatherId;
    }

    public void setWeatherId(int weatherId) {
        mWeatherId = weatherId;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPressure() {
        return mPressure;
    }

    public void setPressure(double pressure) {
        mPressure = pressure;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public double getWindDegree() {
        return mWindDegree;
    }

    public void setWindDegree(double windDegree) {
        mWindDegree = windDegree;
    }

    public double getClouds() {
        return mClouds;
    }

    public void setClouds(double clouds) {
        mClouds = clouds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(mTemperature);
        parcel.writeLong(mTimestamp);
        parcel.writeString(mIcon);
        parcel.writeString(mDescription);
        parcel.writeString(mCondition);
        parcel.writeInt(mWeatherId);
        parcel.writeDouble(mPressure);
        parcel.writeDouble(mHumidity);
        parcel.writeDouble(mWindSpeed);
        parcel.writeDouble(mWindDegree);
        parcel.writeDouble(mClouds);
        parcel.writeString(mDateTxt);
        parcel.writeDouble(mGroundLevel);
        parcel.writeDouble(mSeaLevel);
    }
}
