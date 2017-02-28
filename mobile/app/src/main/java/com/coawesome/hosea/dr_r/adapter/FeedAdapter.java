package com.coawesome.hosea.dr_r.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coawesome.hosea.dr_r.R;
import com.coawesome.hosea.dr_r.dao.FeedVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Hosea on 2016-11-01.
 */


public class FeedAdapter extends BaseAdapter {
    private Context fContext;
    private int fResource;
    private ArrayList<FeedVO> fItems = new ArrayList<>();

    public FeedAdapter(Context context, int resource, ArrayList<FeedVO> items) {
        fContext = context;
        fResource = resource;
        fItems = items;
    }

    @Override
    public int getCount() {
        return fItems.size();
    }

    @Override
    public Object getItem(int i) {
        return fItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FeedVO feedVO = fItems.get(i);

        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) fContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(fResource, viewGroup,false);
        }


        final LinearLayout ItemLayout = (LinearLayout) view.findViewById(R.id.tv_item_layout);
        TextView feedStart = (TextView) view.findViewById(R.id.tv_item_feed_start);
        TextView feedEnd = (TextView) view.findViewById(R.id.tv_item_feed_end);
        TextView feedAmount = (TextView) view.findViewById(R.id.tv_item_feed_amount);
        TextView feedValue = (TextView) view.findViewById(R.id.tv_item_feed_value);
        View linear = view.findViewById(R.id.set_feed_list_color);
        if(feedVO.getFeed().equals("분유")){
            linear.setBackgroundColor(0xFFFFDDDD);
//            ItemLayout.setBackgroundColor(0x99FFDDDD);
            feedStart.setText("입력 시간 : ");
            feedEnd.setVisibility(View.INVISIBLE);
            feedAmount.setText("총 분유 량 : ");
            feedValue.setText("ml");
        }
        else{
            //사용자 입력이 분유가 아닐 경우(모유)
            linear.setBackgroundColor(0xFFB2CCFF);
//            ItemLayout.setBackgroundColor(0x00B2CCFF);
            feedStart.setText("수유 시간 : ");
            feedEnd.setVisibility(View.VISIBLE);
            feedEnd.setText("수유 끝 : ");
            feedAmount.setText("총 수유 시간 : ");
            feedValue.setText("분");
        }

        // Set DayName
        TextView feedStartTime = (TextView) view.findViewById(R.id.tv_item_feed_start_time);
        TextView feedEndTime = (TextView) view.findViewById(R.id.tv_item_feed_end_time);
        TextView feed = (TextView) view.findViewById(R.id.tv_item_feed_feed);
        TextView total = (TextView) view.findViewById(R.id.tv_item_feed_total);


        // Set Text 01
        final SimpleDateFormat curHourFormat = new SimpleDateFormat("HH", Locale.KOREA);
        curHourFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        final SimpleDateFormat curMinuteFormat = new SimpleDateFormat("mm", Locale.KOREA);
        final SimpleDateFormat curSecFormat = new SimpleDateFormat("ss", Locale.KOREA);
        Date feed_start = new Date(feedVO.getF_start().getTime());
        Date feed_end = new Date(feedVO.getF_end().getTime());


        int s_hour = Integer.parseInt(curHourFormat.format(feed_start));
        int s_min = Integer.parseInt(curMinuteFormat.format(feed_start));
        int s_sec = Integer.parseInt(curSecFormat.format(feed_start));

        int e_hour = Integer.parseInt(curHourFormat.format(feed_end));
        int e_min = Integer.parseInt(curMinuteFormat.format(feed_end));
        int e_sec = Integer.parseInt(curSecFormat.format(feed_end));


        feedStartTime.setText(s_hour+":"+s_min/*+":"+s_sec*/);
        feed.setText(feedVO.getFeed());
        if (!feedVO.getFeed().equals("분유")) {
            total.setText(""+(feedVO.getF_total()/60));
            feedEndTime.setText(e_hour+":"+e_min/*+":"+e_sec*/);
        } else {
            feedEndTime.setText("");
            total.setText(""+(feedVO.getF_total()));

        }
        return view;
    }

}
