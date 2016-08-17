package com.example.yulian.goodweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yulian on 06.08.2016.
 */
public class DailyWeather implements Parcelable {
    private long mTimestamp;
    private double mDayTemp;
    private double mMinTemp;
    private double mMaxTemp;
    private double mNightTemp;
    private double mEveTemp;
    private double mMorningTemp;
    private String mIcon;
    private String mDescription;
    private String mCondition;
    private int mWeatherId;
    private double mPressure;
    private double mHumidity;
    private double mWindSpeed;
    private double mWindDegree;
    private double mClouds;

    protected DailyWeather(Parcel in) {
        mTimestamp = in.readLong();
        mDayTemp = in.readDouble();
        mMinTemp = in.readDouble();
        mMaxTemp = in.readDouble();
        mNightTemp = in.readDouble();
        mEveTemp = in.readDouble();
        mMorningTemp = in.readDouble();
        mIcon = in.readString();
        mDescription = in.readString();
        mCondition = in.readString();
        mWeatherId = in.readInt();
    }

    public static final Creator<DailyWeather> CREATOR = new Creator<DailyWeather>() {
        @Override
        public DailyWeather createFromParcel(Parcel in) {
            return new DailyWeather(in);
        }

        @Override
        public DailyWeather[] newArray(int size) {
            return new DailyWeather[size];
        }
    };

    public void setWeatherId(int weatherId) {
        this.mWeatherId = weatherId;
    }

    public int getWeatherId() {

        return mWeatherId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String condition) {
        mCondition = condition;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public DailyWeather() {}

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }

    public void setDayTemp(double dayTemp) {
        this.mDayTemp = dayTemp;
    }

    public void setMinTemp(double minTemp) {
        this.mMinTemp = minTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.mMaxTemp = maxTemp;
    }

    public void setNightTemp(double nightTemp) {
        this.mNightTemp = nightTemp;
    }

    public void setEveTemp(double eveTemp) {
        this.mEveTemp = eveTemp;
    }

    public void setMorningTemp(double morningTemp) {
        this.mMorningTemp = morningTemp;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public double getDayTemp() {
        return mDayTemp;
    }

    public double getMinTemp() {
        return mMinTemp;
    }

    public double getMaxTemp() {
        return mMaxTemp;
    }

    public double getNightTemp() {
        return mNightTemp;
    }

    public double getEveTemp() {
        return mEveTemp;
    }

    public double getMorningTemp() {
        return mMorningTemp;
    }

    public int getIconId() {
        return Forecast.getFullIconId(getIcon());
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
    }

    public String getIcon() {
        return mIcon;
    }

    public double getPressure() {
        return mPressure;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public double getWindDegree() {
        return mWindDegree;
    }

    public double getClouds() {
        return mClouds;
    }

    public void setPressure(double pressure) {
        mPressure = pressure;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public void setClouds(double clouds) {
        mClouds = clouds;
    }

    public void setWindDegree(double windDegree) {
        mWindDegree = windDegree;
    }

    public static Creator<DailyWeather> getCREATOR() {
        return CREATOR;
    }

    public double avarageTemp()
    {
        double avarage =  (getMaxTemp() + getMinTemp())/2;
        return avarage;
    }

    public String getDayOfTheWeek() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        Date dateTime = new Date(mTimestamp * 1000);
        return formatter.format(dateTime);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTimestamp);
        parcel.writeDouble(mDayTemp);
        parcel.writeDouble(mMinTemp);
        parcel.writeDouble(mMaxTemp);
        parcel.writeDouble(mNightTemp);
        parcel.writeDouble(mEveTemp);
        parcel.writeDouble(mMorningTemp);
        parcel.writeString(mIcon);
        parcel.writeString(mDescription);
        parcel.writeString(mCondition);
        parcel.writeInt(mWeatherId);
    }
}
