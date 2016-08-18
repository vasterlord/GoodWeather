package com.example.yulian.goodweather.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yulian.goodweather.R;
import com.example.yulian.goodweather.adapter.DayAdapter;
import com.example.yulian.goodweather.helpers.AlertDialogFragment;
import com.example.yulian.goodweather.helpers.Helper;
import com.example.yulian.goodweather.helpers.JSONWeatherParser;
import com.example.yulian.goodweather.helpers.RemoteFetch;
import com.example.yulian.goodweather.model.DailyWeather;
import com.example.yulian.goodweather.model.Forecast;

import org.json.JSONException;

import java.util.Arrays;


public class DailyWeatherFragment extends Fragment {
    public double mLat = 0.0;
    public double mLon = 0.0;
    public String mCityCountry = "";
    TextView mLocationLabel;
    private DailyWeather[] mDays;
    public Helper mHelper = new Helper();
    Handler handler;
    ListView mListView;
    TextView mEmptyTextView;
    public Forecast mForecast = new Forecast();
    public DailyWeatherFragment() {handler = new Handler();}
    RelativeLayout dailyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View dailyFragment = inflater.inflate(R.layout.fragment_daily_weather, container, false);
        if (getArguments() != null) {
            mLat = DailyWeatherFragment.this.getArguments().getDouble("lati");
            mLon = DailyWeatherFragment.this.getArguments().getDouble("lon");
            mCityCountry = DailyWeatherFragment.this.getArguments().getString("city_counrty");
           /* Log.e("TESTING","Lat = : " + mLat);
            Log.e("TESTING","Lon = " + mLon);
            Log.e("TESTING","City&Country: " + mCityCountry);*/
        }
        else
        {
            Log.i("LOG", "getArgument is null");
        }
        mLocationLabel = (TextView) dailyFragment.findViewById(R.id.locationLabel);
        mListView = (ListView) dailyFragment.findViewById(android.R.id.list);
        mEmptyTextView = (TextView) dailyFragment.findViewById(android.R.id.empty);
        dailyLayout = (RelativeLayout) dailyFragment.findViewById(R.id.daily_layout);
        updateWeatherData(mLat, mLon);
        mLocationLabel.setText(mCityCountry);
        return dailyFragment;
    }

    private void updateWeatherData(final double lat, final double lon){
        new Thread(){
            public void run(){
                final String jsonDaily = RemoteFetch.getDaily(getContext(), lat, lon);
                if(mHelper.isNetworkAvailable(getContext())){
                        handler.post(new Runnable(){
                            public void run(){
                                try {
                                    mForecast.setDailyForecast(JSONWeatherParser.getDailyForecast(jsonDaily));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                handler.post(new Runnable(){
                                    public void run(){
                                        Parcelable[] parcelables = mForecast.getDailyForecast();
                                        mDays = Arrays.copyOf(parcelables, parcelables.length, DailyWeather[].class);
                                        DayAdapter adapter = new DayAdapter(getContext(), mDays);
                                        mListView.setAdapter(adapter);

                                        mListView.setEmptyView(mEmptyTextView);
                                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String dayOfTheWeek = mDays[position].getDayOfTheWeek();
                                                String conditions = mDays[position].getDescription();
                                                String highTemp = String.format("%.0f",mDays[position].avarageTemp());
                                                String message = String.format("У %s the avarage temperature will be %s℃ and it will be %s",
                                                        dayOfTheWeek,
                                                        highTemp,
                                                        conditions);
                                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                });
                            }
                        });
                }
                else
                {

                }
            }
        }.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLat = DailyWeatherFragment.this.getArguments().getDouble("lati");
            mLon = DailyWeatherFragment.this.getArguments().getDouble("lon");
            mCityCountry = DailyWeatherFragment.this.getArguments().getString("city_counrty");
            /*Log.e("TESTING","Lat = : " + mLat);
            Log.e("TESTING","Lon = " + mLon);
            Log.e("TESTING","City&Country: " + mCityCountry);*/
        }
        else
        {
            Log.i("LOG", "getArgument is null");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            mLat = DailyWeatherFragment.this.getArguments().getDouble("lati");
            mLon = DailyWeatherFragment.this.getArguments().getDouble("lon");
            mCityCountry = DailyWeatherFragment.this.getArguments().getString("city_counrty");
            /*Log.e("TESTING","Lat = : " + mLat);
            Log.e("TESTING","Lon = " + mLon);
            Log.e("TESTING","City&Country: " + mCityCountry);*/
            updateWeatherData(mLat, mLon);
            mLocationLabel.setText(mCityCountry);
        }
        else
        {
            Log.i("LOG", "getArgument is null");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            mLat = DailyWeatherFragment.this.getArguments().getDouble("lati");
            mLon = DailyWeatherFragment.this.getArguments().getDouble("lon");
            mCityCountry = DailyWeatherFragment.this.getArguments().getString("city_counrty");
            /*Log.e("TESTING","Lat = : " + mLat);
            Log.e("TESTING","Lon = " + mLon);
            Log.e("TESTING","City&Country: " + mCityCountry);*/
            updateWeatherData(mLat, mLon);
            mLocationLabel.setText(mCityCountry);
        }
        else
        {
            Log.i("LOG", "getArgument is null");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mLat = DailyWeatherFragment.this.getArguments().getDouble("lati");
            mLon = DailyWeatherFragment.this.getArguments().getDouble("lon");
            mCityCountry = DailyWeatherFragment.this.getArguments().getString("city_counrty");
            /*Log.e("TESTING","Lat = : " + mLat);
            Log.e("TESTING","Lon = " + mLon);
            Log.e("TESTING","City&Country: " + mCityCountry);*/
            updateWeatherData(mLat, mLon);
            mLocationLabel.setText(mCityCountry);
        }
        else
        {
            Log.i("LOG", "getArgument is null");
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (getArguments() != null) {
            mLat = DailyWeatherFragment.this.getArguments().getDouble("lati");
            mLon = DailyWeatherFragment.this.getArguments().getDouble("lon");
            mCityCountry = DailyWeatherFragment.this.getArguments().getString("city_counrty");
            /*Log.e("TESTING","Lat = : " + mLat);
            Log.e("TESTING","Lon = " + mLon);
            Log.e("TESTING","City&Country: " + mCityCountry);*/
            updateWeatherData(mLat, mLon);
            mLocationLabel.setText(mCityCountry);
        }
        else
        {
            Log.i("LOG", "getArgument is null");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mLat = DailyWeatherFragment.this.getArguments().getDouble("lati");
            mLon = DailyWeatherFragment.this.getArguments().getDouble("lon");
            mCityCountry = DailyWeatherFragment.this.getArguments().getString("city_counrty");
            /*Log.e("TESTING","Lat = : " + mLat);
            Log.e("TESTING","Lon = " + mLon);
            Log.e("TESTING","City&Country: " + mCityCountry);*/
            updateWeatherData(mLat, mLon);
            mLocationLabel.setText(mCityCountry);
        }
        else
        {
            Log.i("LOG", "getArgument is null");
        }
    }

    private OnFragmentInteractionListener mListener;

    public static DailyWeatherFragment newInstance(double param1, double param2, String param3) {
        DailyWeatherFragment fragment = new DailyWeatherFragment();
        Bundle args = new Bundle();
        args.putDouble("lati", param1);
        args.putDouble("lon", param2);
        args.putString("city", param3);
        fragment.setArguments(args);
        return fragment;
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
