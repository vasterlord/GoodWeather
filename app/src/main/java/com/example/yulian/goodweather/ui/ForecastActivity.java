package com.example.yulian.goodweather.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yulian.goodweather.R;
import com.example.yulian.goodweather.helpers.OnDataPass;

import java.io.Serializable;


public class ForecastActivity  extends AppCompatActivity implements Serializable, OnDataPass {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public double mLatitube = 0.0;
    public double mLongtitube = 0.0;
    public String mCityCountry = "";
    public double mLat = 0.0;
    public double mLon = 0.0;
    public String mCity = "";
    Bundle bundle = new Bundle();
    Bundle bundle1 = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        final CurrentWeatherFragment currentWeatherFragment = new CurrentWeatherFragment();
        final HourlyWeatherFragment hourlyWeatherFragment = new HourlyWeatherFragment();
        final DailyWeatherFragment dailyWeatherFragment = new DailyWeatherFragment();

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return currentWeatherFragment;
                }
                else if(position == 1) {
                    hourlyWeatherFragment.setArguments(bundleBulid(bundle));
                    return hourlyWeatherFragment;
                }
                else
                {
                    dailyWeatherFragment.setArguments(bundleBulid(bundle1));
                    return dailyWeatherFragment;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) return "CURRENT";
                else if(position == 1) return "HOURLY";
                else  return "7 DAY";
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    private Bundle bundleBulid(Bundle mbundle) {
        mbundle.putDouble("lati", mLatitube);
        mbundle.putDouble("lon", mLongtitube);
        mbundle.putString("city_counrty" , mCityCountry);
        return  mbundle;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if(item.getItemId() == R.id.exit)
        {
            finish();
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }


    @Override
    public void onDataPass(double lat, double lon, String city_country_text) {
        mLatitube = lat;
        mLongtitube = lon;
        mCityCountry = city_country_text;
       /* Log.e("LOG","привет, я строка из фрагмента: " + mLatitube);
        Log.e("LOG","привет, я строка из фрагмента: " + mLongtitube);
        Log.e("LOG","привет, я строка из фрагмента: " + mCityCountry);*/
    }

}
