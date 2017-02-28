package com.coawesome.hosea.dr_r.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coawesome.hosea.dr_r.R;
import com.coawesome.hosea.dr_r.dao.SleepVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Hosea on 2016-11-01.
 */


public class SleepAdapter extends BaseAdapter {
    private Context sContext;
    private int sResource;
    private ArrayList<SleepVO> sItems = new ArrayList<>();

    public SleepAdapter(Context context, int resource, ArrayList<SleepVO> items) {
        sContext = context;
        sResource = resource;
        sItems = items;
    }

    @Override
    public int getCount() {
        return sItems.size();
    }

    @Override
    public Object getItem(int i) {
        return sItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) sContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(sResource, viewGroup,false);
        }

        // Set DayName
        TextView sleepStartTime = (TextView) view.findViewById(R.id.tv_item_sleep_start_time);
        TextView sleepEndTime = (TextView) view.findViewById(R.id.tv_item_sleep_end_time);
        TextView total = (TextView) view.findViewById(R.id.tv_item_sleep_total);

        // Set Text 01
        SleepVO sleepVO = sItems.get(i);
        final SimpleDateFormat curHourFormat = new SimpleDateFormat("HH", Locale.KOREA);
        curHourFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        final SimpleDateFormat curMinuteFormat = new SimpleDateFormat("mm", Locale.KOREA);
        final SimpleDateFormat curSecFormat = new SimpleDateFormat("ss", Locale.KOREA);
        Date sleep_start = new Date(sleepVO.getS_start().getTime());
        Date sleep_end = new Date(sleepVO.getS_end().getTime());

        int s_hour = Integer.parseInt(curHourFormat.format(sleep_start));
        int s_min = Integer.parseInt(curMinuteFormat.format(sleep_start));

        int e_hour = Integer.parseInt(curHourFormat.format(sleep_end));
        int e_min = Integer.parseInt(curMinuteFormat.format(sleep_end));

        sleepStartTime.setText(s_hour+"시"+s_min+"분");
        sleepEndTime.setText(e_hour+"시"+e_min+"분");
        total.setText(""+(sleepVO.getS_total()/60) + "분");

        return view;
    }

}
