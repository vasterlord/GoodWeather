package com.example.yulian.goodweather.helpers;

/**
 * Created by Yulian on 30.07.2016.
 */

import android.content.Context;

import com.example.yulian.goodweather.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteFetch {

    private static final String BASE_CURRENT_WEATHER_URL  = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static String BASE_DAILY_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&lat=%s&lon=%s&units=metric";
    private static String BASE_HOURLY_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/hourly?mode=json&lat=%s&lon=%s&units=metric";

    public static String getCurrent(Context context, String city){
        try {
            URL url = new URL(String.format(BASE_CURRENT_WEATHER_URL, city));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data.toString();
        }catch(Exception e){
            return null;
        }
    }
    public static String getDaily(Context context,  double tempLat, double tempLon){
        try {
            URL url = new URL(String.format(BASE_DAILY_FORECAST_URL, tempLat, tempLon));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data.toString();
        }catch(Exception e){
            return null;
        }
    }
    public static String getHourly(Context context,  double tempLat, double tempLon){
        try {
            URL url = new URL(String.format(BASE_HOURLY_FORECAST_URL, tempLat, tempLon));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data.toString();
        }catch(Exception e){
            return null;
        }
    }

}
