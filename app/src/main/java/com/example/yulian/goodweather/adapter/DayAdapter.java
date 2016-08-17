package com.example.yulian.goodweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yulian.goodweather.R;
import com.example.yulian.goodweather.model.DailyWeather;

public class DayAdapter extends BaseAdapter {

    private Context mContext;
    private DailyWeather[] mDays;

    public DayAdapter(Context context, DailyWeather[] days) {
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        DailyWeather day = mDays[position];


        holder.iconImageView.setImageResource(day.getIconId());

        holder.temperatureLabel.setText(String.format("%.0f",day.avarageTemp()));

            holder.dayLabel.setText(day.getDayOfTheWeek());

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
    }
}












