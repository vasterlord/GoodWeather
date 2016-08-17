package com.example.yulian.goodweather.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yulian.goodweather.R;
import com.example.yulian.goodweather.helpers.AlertDialogFragment;
import com.example.yulian.goodweather.helpers.Helper;
import com.example.yulian.goodweather.helpers.GPSTracker;
import com.example.yulian.goodweather.helpers.JSONWeatherParser;
import com.example.yulian.goodweather.helpers.OnDataPass;
import com.example.yulian.goodweather.model.CurrentlyWeather;
import com.example.yulian.goodweather.model.Forecast;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;


public class CurrentWeatherFragment extends Fragment implements Serializable, View.OnClickListener {
    private String mParam1;
    private String mParam2;
    private double mParam3;
    private double mParam4;
    private String mParam5;
    public static final String TAG = CurrentWeatherFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView descriptonField;
    ImageView iconView;
    Button btnCurrentPlace;
    Button btnCityChanged;
    Button btnMapsLocation;
    ImageView mRefreshImageView;
    ProgressBar mProgressBar;
    TextView mEmptyTextView;
    Handler handler;
    public Forecast mForecast = new Forecast();
    public Helper mHelper = new Helper();
    Drawable drawable;
    private static final String BASE_CURRENT_WEATHER_URL_CITY  = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&APPId=%s";
    private static final String BASE_CURRENT_WEATHER_URL_COORD=  "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric&APPId=%s";
    String nowURL = BASE_CURRENT_WEATHER_URL_COORD;
    String city = "";
    String jsonData = "";
    double [] coord = new double[2];
    private OnDataPass mDataPass;

    public CurrentWeatherFragment() {handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentFragment = inflater.inflate(R.layout.fragment_current_weather, container, false);
        mRefreshImageView = (ImageView) currentFragment.findViewById(R.id.refreshImageView) ;
        mProgressBar = (ProgressBar) currentFragment.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        cityField = (TextView) currentFragment.findViewById(R.id.city_field);
        updatedField = (TextView) currentFragment.findViewById(R.id.updated_field);
        detailsField = (TextView) currentFragment.findViewById(R.id.details_field);
        currentTemperatureField = (TextView) currentFragment.findViewById(R.id.current_temperature_field);
        descriptonField = (TextView) currentFragment.findViewById(R.id.decription_field);
        iconView = (ImageView) currentFragment.findViewById(R.id.icon_Image);
        btnCurrentPlace = (Button) currentFragment.findViewById(R.id.currentLoc);
        btnCityChanged = (Button) currentFragment.findViewById(R.id.cityLoc);
        btnMapsLocation = (Button) currentFragment.findViewById(R.id.mapsLoc);
        mEmptyTextView = (TextView) currentFragment.findViewById(android.R.id.empty);
        mEmptyTextView.setVisibility(View.INVISIBLE);
        coord = mHelper.CoordTracker(getContext());
        Log.e("TESTING","Lat = : " + coord[0]);
        Log.e("TESTING","Lon = " + coord[1]);
        try {
            getForecast(city,  coord[0],  coord[1], nowURL);
            city = "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        btnCurrentPlace.setOnClickListener(this);
        btnCityChanged.setOnClickListener(this);
        btnMapsLocation.setOnClickListener(this);
        mRefreshImageView.setOnClickListener(this);
        return currentFragment;
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        mDataPass = (OnDataPass) a;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public static CurrentWeatherFragment newInstance(String param1, String param2, double param3, double param4, String param5) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putString("CITY", param1);
        args.putString("URL", param2);
        args.putDouble("LATITUBE", param3);
        args.putDouble("LONGTITUBE", param4);
        args.putString("JSON", param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("CITY");
            mParam2 = getArguments().getString("URL");
            mParam3 = getArguments().getDouble("LATITUBE");
            mParam4 = getArguments().getDouble("LONGTITUBE");
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.currentLoc:
                nowURL = BASE_CURRENT_WEATHER_URL_COORD;
                coord = mHelper.CoordTracker(getContext());
                try {
                    getForecast(city,  coord[0],  coord[1], nowURL);
                    city = "";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.cityLoc:
                nowURL = BASE_CURRENT_WEATHER_URL_CITY;
                showInputDialog();
                coord[0] = 0.0;
                coord[1] = 0.0;
                break;
            case R.id.mapsLoc:
              alertUserAboutError();
            case R.id.refreshImageView:
                try {
                    getForecast(city,  coord[0],  coord[1], nowURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void getForecast(String tempCity, double tempLat, double tempLon, String tempForecastUrl) throws MalformedURLException {
        String apiKey = "ddec71381c5621cdddefb5c58581e5bc";
        String forecastUrl = "";
        if(tempForecastUrl == BASE_CURRENT_WEATHER_URL_CITY)
        {
        forecastUrl = String.valueOf(new URL(String.format(tempForecastUrl, tempCity, apiKey)));
        }
        else
        {
        forecastUrl = String.valueOf(new URL(String.format(tempForecastUrl, tempLat, tempLon, apiKey)));
        }
        if (Helper.isNetworkAvailable(getContext())) {
            mHelper.toggleRefresh(mProgressBar, mRefreshImageView);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.post(new Runnable(){
                        public void run(){
                            mHelper.toggleRefresh(mProgressBar, mRefreshImageView);
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHelper.toggleRefresh(mProgressBar, mRefreshImageView);
                        }
                    });

                    try {
                        jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            try {
                                mForecast.setCurrent(JSONWeatherParser.getWeather(jsonData));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                    mEmptyTextView.setVisibility(View.INVISIBLE);
                                    mDataPass.onDataPass(mForecast.getCurrent().mLocationCurrentWeather.getLatitude(),
                                            mForecast.getCurrent().mLocationCurrentWeather.getLongitude(),
                                            (mForecast.getCurrent().mLocationCurrentWeather.getCity().toUpperCase(Locale.US) +
                                                    ", " +
                                                    mForecast.getCurrent().mLocationCurrentWeather.getCountry().toUpperCase(Locale.US)));
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        else {
            mEmptyTextView.setVisibility(View.VISIBLE);
           Toast.makeText(getContext(), getString(R.string.network_unavailable_message),
                   Toast.LENGTH_LONG).show();
        }
    }
    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Change city");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                city = input.getText().toString();
                try {
                    getForecast(city ,  coord[0],  coord[1], nowURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                input.setText("");
            }
        });
        builder.show();
    }
    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
        Context context = getActivity();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error_title))
                .setMessage(context.getString(R.string.error_message))
                .setPositiveButton(context.getString(R.string.error_ok_button_text), null);

        builder.show();
    }
    protected void updateDisplay() {
        CurrentlyWeather currentlyWeather = mForecast.getCurrent();
        cityField.setText(currentlyWeather.mLocationCurrentWeather.getCity().toUpperCase(Locale.US) +
                ", " +
                currentlyWeather.mLocationCurrentWeather.getCountry().toUpperCase(Locale.US));
        descriptonField.setText(currentlyWeather.mCurrentCondition.getCondition() + "(" + currentlyWeather.mCurrentCondition.getDescription() + ")");
        detailsField.setText( "Humidity: " + String.format("%.0f", currentlyWeather.mCurrentCondition.getHumidity()) + "%" +
                "\n" + "Pressure: " + String.format("%.0f", currentlyWeather.mCurrentCondition.getPressure()) + " hPa" +
                "\n" + "Wind speed: " + String.format("%.0f", currentlyWeather.mWind.getSpeed()) + " mps" +
                "\n" + "Wind direction: " + String.format("%.0f", currentlyWeather.mWind.getDegree())   + "º" +
                "\n" + "Cloudness: " + currentlyWeather.mClouds.getPrecipitation() + " %"
        );
        currentTemperatureField.setText(String.format("%.0f", currentlyWeather.mTemperature.getTemperature()) + "℃");
        updatedField.setText("Last update: " + currentlyWeather.mLastUpdate.gettimeUpdate().toUpperCase(Locale.US));
        drawable = getResources().getDrawable(currentlyWeather.getIconId());
        iconView.setImageDrawable(drawable);
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
