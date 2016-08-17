package com.example.yulian.goodweather.helpers;

import com.example.yulian.goodweather.model.CurrentlyWeather;
import com.example.yulian.goodweather.model.DailyWeather;
import com.example.yulian.goodweather.model.HourlyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;


public class JSONWeatherParser {

    public static CurrentlyWeather getWeather(String data) throws JSONException  {
        CurrentlyWeather currentlyWeather = new CurrentlyWeather();

        JSONObject jObj = new JSONObject(data);
        JSONObject coordObj = getObject("coord", jObj);
        JSONObject sysObj = getObject("sys", jObj);

        currentlyWeather.mLocationCurrentWeather.setLatitude(getFloat("lat", coordObj));
        currentlyWeather.mLocationCurrentWeather.setLongitude(getFloat("lon", coordObj));
        currentlyWeather.mLocationCurrentWeather.setCountry(getString("country", sysObj));
        currentlyWeather.mLocationCurrentWeather.setSunrise(getInt("sunrise", sysObj));
        currentlyWeather.mLocationCurrentWeather.setSunset(getInt("sunset", sysObj));
        currentlyWeather.mLocationCurrentWeather.setCity(getString("name", jObj));

        JSONArray jArr = jObj.getJSONArray("weather");
        JSONObject JSONWeather = jArr.getJSONObject(0);
        currentlyWeather.mCurrentCondition.setWeatherId(getInt("id", JSONWeather));
        currentlyWeather.mCurrentCondition.setDescription(getString("description", JSONWeather));
        currentlyWeather.mCurrentCondition.setCondition(getString("main", JSONWeather));
        currentlyWeather.mCurrentCondition.setIcon(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jObj);
        currentlyWeather.mCurrentCondition.setHumidity(getInt("humidity", mainObj));
        currentlyWeather.mCurrentCondition.setPressure(getInt("pressure", mainObj));
        currentlyWeather.mTemperature.setMaxTemperature(getFloat("temp_max", mainObj));
        currentlyWeather.mTemperature.setMinTemperature(getFloat("temp_min", mainObj));
        currentlyWeather.mTemperature.setTemperature(getFloat("temp", mainObj));

        JSONObject wObj = getObject("wind", jObj);

        currentlyWeather.mWind.setSpeed(getFloat("speed", wObj));
        currentlyWeather.mWind.setDegree(getFloat("deg", wObj));

        JSONObject cObj = getObject("clouds", jObj);

        currentlyWeather.mClouds.setPrecipitation(getInt("all", cObj));

        DateFormat df = DateFormat.getDateTimeInstance();
        String updatedOn = df.format(new Date(jObj.getLong("dt")*1000));
        currentlyWeather.mLastUpdate.settimeUpdate(updatedOn);

        return currentlyWeather;
    }


    public static DailyWeather[] getDailyForecast(String data) throws JSONException  {
        JSONObject jObj = new JSONObject(data);

        JSONArray jArr = jObj.getJSONArray("list");
        DailyWeather[] days = new DailyWeather[jArr.length()];

        for (int i=0; i < jArr.length(); i++) {
            JSONObject jDayForecast = jArr.getJSONObject(i);

            DailyWeather day = new DailyWeather();

            day.setTimestamp(jDayForecast.getLong("dt"));
            day.setHumidity(jDayForecast.getDouble("humidity"));
            day.setPressure(jDayForecast.getDouble("pressure"));
            day.setWindSpeed(jDayForecast.getDouble("speed"));
            day.setWindDegree(jDayForecast.getDouble("deg"));


            JSONObject jTempObj = jDayForecast.getJSONObject("temp");

            day.setDayTemp(jTempObj.getDouble("day"));
            day.setMinTemp(jTempObj.getDouble("min"));
            day.setMaxTemp(jTempObj.getDouble("max"));
            day.setNightTemp(jTempObj.getDouble("night"));
            day.setEveTemp(jTempObj.getDouble("eve"));
            day.setMorningTemp(jTempObj.getDouble("morn"));
            day.setClouds(jDayForecast.getDouble("clouds"));
            day.setDescription(jArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
            day.setIcon(jArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon"));
            day.setCondition(jArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main"));
            day.setWeatherId(jArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getInt("id"));

            days[i] = day;
        }
        return days;
    }
    public static HourlyWeather[] getHourlyForecast(String data) throws JSONException  {
        JSONObject jObj = new JSONObject(data);

        JSONArray jArr = jObj.getJSONArray("list");
        HourlyWeather[] hours = new HourlyWeather[jArr.length()];

        for (int i=0; i < jArr.length(); i++) {

            JSONObject jDayForecast = jArr.getJSONObject(i);
            HourlyWeather hour = new HourlyWeather();
            hour.setTimestamp(jDayForecast.getLong("dt"));
            hour.setDateTxt(jDayForecast.getString("dt_txt"));
            JSONObject jTempObj = jDayForecast.getJSONObject("main");
            hour.setHumidity(jTempObj.getDouble("humidity"));
            hour.setPressure(jTempObj.getDouble("pressure"));
            hour.setGroundLevel(jTempObj.getDouble("grnd_level"));
            hour.setSeaLevel(jTempObj.getDouble("sea_level"));
            hour.setTemperature(jTempObj.getDouble("temp"));

            hour.setDescription(jArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
            hour.setIcon(jArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon"));
            hour.setCondition(jArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main"));
            hour.setWeatherId(jArr.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getInt("id"));
            hour.setClouds(jDayForecast.getJSONObject("clouds").getDouble("all"));
            hour.setWindSpeed(jDayForecast.getJSONObject("wind").getDouble("speed"));
            hour.setWindDegree(jDayForecast.getJSONObject("wind").getDouble("deg"));


            hours[i] = hour;
        }
            return hours;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}
