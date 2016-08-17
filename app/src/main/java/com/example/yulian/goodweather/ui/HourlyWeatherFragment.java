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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yulian.goodweather.R;
import com.example.yulian.goodweather.adapter.HourAdapter;
import com.example.yulian.goodweather.helpers.AlertDialogFragment;
import com.example.yulian.goodweather.helpers.Helper;
import com.example.yulian.goodweather.helpers.JSONWeatherParser;
import com.example.yulian.goodweather.helpers.RemoteFetch;
import com.example.yulian.goodweather.model.Forecast;
import com.example.yulian.goodweather.model.HourlyWeather;

import org.json.JSONException;

import java.util.Arrays;


public class HourlyWeatherFragment extends Fragment {
    public double mLati = 0.0;
    public double mLong = 0.0;
    public double mlat = 0.0;
    public double mlon = 0.0;
    public String mCityC = "";
    private HourlyWeather[] mHours;
    RecyclerView mRecyclerView;
    TextView mLocationLabel;
    public Forecast mForecastH = new Forecast();
    public Helper mHelperH = new Helper();
    Handler handler;
    HourAdapter adapter;
    double [] coord = new double[2];
    RelativeLayout hourlyLayout;
    TextView mEmptyTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hourlyFragment = inflater.inflate(R.layout.fragment_hourly_weather, container, false);
        mRecyclerView = (RecyclerView) hourlyFragment.findViewById(R.id.reyclerView);
        mLocationLabel = (TextView) hourlyFragment.findViewById(R.id.locationLabel);
        hourlyLayout = (RelativeLayout) hourlyFragment.findViewById(R.id.hourly_layuot);
        mEmptyTextView = (TextView) hourlyFragment.findViewById(android.R.id.empty);
        mEmptyTextView.setVisibility(View.INVISIBLE);
        coord =  mHelperH.CoordTracker(getContext());
        updateWeatherData(coord[0], coord[1]);
        /* mLocationLabel.setText(mForecastH.getCurrent().mLocationCurrentWeather.getCity().toUpperCase(Locale.US) +
                ", " +
                mForecastH.getCurrent().mLocationCurrentWeather.getCountry().toUpperCase(Locale.US));*/
        mLocationLabel.setText("BRYUKHOVYCHI, UA");
        return hourlyFragment;
    }
    private void updateWeatherData(final double lat, final double lon){
        new Thread(){
            public void run(){
                final String jsonHourly = RemoteFetch.getHourly(getContext(), lat, lon);
                if(mHelperH.isNetworkAvailable(getContext())){
                        handler.post(new Runnable(){
                            public void run(){
                                try {
                                    mForecastH.setHourlyForecast(JSONWeatherParser.getHourlyForecast(jsonHourly));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                handler.post(new Runnable(){
                                    public void run(){
                                        mEmptyTextView.setVisibility(View.INVISIBLE);
                                        Parcelable[] parcelables = mForecastH.getHourlyForecast();
                                        mHours = Arrays.copyOf(parcelables, parcelables.length, HourlyWeather[].class);
                                        adapter = new HourAdapter(getContext(), mHours);
                                        mRecyclerView.setAdapter(adapter);

                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                        mRecyclerView.setLayoutManager(layoutManager);

                                        mRecyclerView.setHasFixedSize(true);
                                    }
                                });
                            }
                        });
                }
                else
                {

                    mLocationLabel.setText("");
                    mHelperH.checkAdapterIsEmpty(adapter , mRecyclerView);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLati = HourlyWeatherFragment.this.getArguments().getDouble("lati");
            mLong = HourlyWeatherFragment.this.getArguments().getDouble("lon");
            mCityC = HourlyWeatherFragment.this.getArguments().getString("city_counrty");
            Log.e("HOURLY","Lat = : " + mLati);
            Log.e("HOURLY","Lon = " + mLong);
            Log.e("HOURLY","City&Country: " + mCityC);
        }
        else
        {
            Log.e("LOG", "getArgument is null");
        }
    }

    private OnFragmentInteractionListener mListener;

    public HourlyWeatherFragment() {handler = new Handler();}
    public static HourlyWeatherFragment newInstance(double param1, double param2, String param3) {
        HourlyWeatherFragment fragment = new HourlyWeatherFragment();
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
