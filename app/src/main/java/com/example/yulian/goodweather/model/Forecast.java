package com.example.yulian.goodweather.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.yulian.goodweather.R;

import java.io.Serializable;

public class Forecast implements Serializable, Parcelable {
    private CurrentlyWeather mCurrent;
    private DailyWeather[] mDailyForecast;
    private HourlyWeather[] mHourlyForecast;

    public Forecast(Parcel in) {
        mDailyForecast = in.createTypedArray(DailyWeather.CREATOR);
        mHourlyForecast = in.createTypedArray(HourlyWeather.CREATOR);
    }

    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

    public Forecast() {

    }

    public static Creator<Forecast> getCREATOR() {
        return CREATOR;
    }

    public CurrentlyWeather getCurrent() {
        return mCurrent;
    }

    public void setCurrent(CurrentlyWeather current) {
        mCurrent = current;
    }

    public DailyWeather[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(DailyWeather[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    public HourlyWeather[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(HourlyWeather[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public static int getFullIconId(String iconString) {

        int iconIdOk = R.drawable.n01;
        int iconIdNo = R.drawable.d01;

        if(iconString!= null) {
            if (iconString.equals("01d")) {
                iconIdOk = R.drawable.d01;
            } else if (iconString.equals("02d")) {
                iconIdOk = R.drawable.d02;
            } else if (iconString.equals("03d")) {
                iconIdOk = R.drawable.d03;
            } else if (iconString.equals("04d")) {
                iconIdOk = R.drawable.d04;
            } else if (iconString.equals("09d")) {
                iconIdOk = R.drawable.d09;
            } else if (iconString.equals("10d")) {
                iconIdOk = R.drawable.d10;
            } else if (iconString.equals("11d")) {
                iconIdOk = R.drawable.d11;
            } else if (iconString.equals("13d")) {
                iconIdOk = R.drawable.d13;
            } else if (iconString.equals("50d")) {
                iconIdOk = R.drawable.d50;
            } else if (iconString.equals("01n")) {
                iconIdOk = R.drawable.n01;
            } else if (iconString.equals("02n")) {
                iconIdOk = R.drawable.n02;
            } else if (iconString.equals("03n")) {
                iconIdOk = R.drawable.n03;
            } else if (iconString.equals("04n")) {
                iconIdOk = R.drawable.n04;
            } else if (iconString.equals("09n")) {
                iconIdOk = R.drawable.n09;
            } else if (iconString.equals("10n")) {
                iconIdOk = R.drawable.n10;
            } else if (iconString.equals("11n")) {
                iconIdOk = R.drawable.n11;
            } else if (iconString.equals("13n")) {
                iconIdOk = R.drawable.n13;
            } else if (iconString.equals("50n")) {
                iconIdOk = R.drawable.n50;
            }
            return iconIdOk;
        }
        else
        {
            return iconIdNo;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(mDailyForecast, i);
        parcel.writeTypedArray(mHourlyForecast, i);
    }
}
