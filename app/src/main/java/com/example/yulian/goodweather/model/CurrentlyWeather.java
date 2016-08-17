package com.example.yulian.goodweather.model;

import java.io.Serializable;

/**
 * Created by Yulian on 06.08.2016.
 */
public class CurrentlyWeather {
    public LocationCurrentWeather mLocationCurrentWeather = new LocationCurrentWeather();
    public CurrentCondition mCurrentCondition = new CurrentCondition();
    public Temperature mTemperature = new Temperature();
    public Wind mWind = new Wind();
    public Clouds mClouds = new Clouds();
    public LastUpdate mLastUpdate = new LastUpdate();
    public int getIconId() {
        return Forecast.getFullIconId(mCurrentCondition.getIcon());
    }

    public  class CurrentCondition {
        private int mWeatherId;
        private String mCondition;
        private String mDescription;
        private String mIcon;
        private double mPressure;
        private double humidity;

        public int getWeatherId() {
            return mWeatherId;
        }
        public void setWeatherId(int weatherId) {
            this.mWeatherId = weatherId;
        }
        public String getCondition() {
            return mCondition;
        }
        public void setCondition(String condition) {
            this.mCondition = condition;
        }
        public String getDescription() {
            return mDescription;
        }
        public void setDescription(String description) {
            this.mDescription = description;
        }
        public String getIcon() {
            return mIcon;
        }
        public void setIcon(String icon) {
            this.mIcon = icon;
        }
        public double getPressure() {
            return mPressure;
        }
        public void setPressure(double pressure) {
            this.mPressure = pressure;
        }
        public double getHumidity() {
            return humidity;
        }
        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }
    }

    public class LocationCurrentWeather implements Serializable {
        private double mLongitude;
        private double mLatitude;
        private long mSunset;
        private long mSunrise;
        private String mCountry;
        private String mCity;

        public double getLongitude() {
            return mLongitude;
        }
        public void setLongitude(double longitude) {
            this.mLongitude = longitude;
        }
        public double getLatitude() {
            return mLatitude;
        }
        public void setLatitude(double latitude) {
            this.mLatitude = latitude;
        }
        public long getSunset() {
            return mSunset;
        }
        public void setSunset(long sunset) {
            this.mSunset = sunset;
        }
        public long getSunrise() {
            return mSunrise;
        }
        public void setSunrise(long sunrise) {
            this.mSunrise = sunrise;
        }
        public String getCountry() {
            return mCountry;
        }
        public void setCountry(String country) {
            this.mCountry = country;
        }
        public String getCity() {
            return mCity;
        }
        public void setCity(String city) {
            this.mCity = city;
        }
    }

    public  class Temperature {
        private double mTemperature;
        private double mMinTemperature;
        private double mMaxTemperature;

        public double getTemperature() {
            return mTemperature;
        }
        public void setTemperature(double temperature) {
            this.mTemperature = temperature;
        }
        public double getMinTemperature() {
            return mMinTemperature;
        }
        public void setMinTemperature(double minTemperature) {
            this.mMinTemperature = minTemperature;
        }
        public double getMaxTemperature() {
            return mMaxTemperature;
        }
        public void setMaxTemperature(double maxTemperature) {
            this.mMaxTemperature = maxTemperature;
        }
    }

    public  class Wind {
        private double mSpeed;
        private double mDegree;

        public double getSpeed() {
            return mSpeed;
        }
        public void setSpeed(double speed) {
            this.mSpeed = speed;
        }
        public double getDegree() {
            return mDegree;
        }
        public void setDegree(double degree) {
            this.mDegree = degree;
        }
    }

    public  class Clouds {
        private int mPrecipitation;

        public int getPrecipitation() {
            return mPrecipitation;
        }
        public void setPrecipitation(int precipitation) {
            this.mPrecipitation = precipitation;
        }
    }

    public class LastUpdate {
        private String timeUpdate;

        public void settimeUpdate(String timeUpdate) {
            this.timeUpdate = timeUpdate;
        }
        public String gettimeUpdate() {
            return timeUpdate;
        }
    }

}
