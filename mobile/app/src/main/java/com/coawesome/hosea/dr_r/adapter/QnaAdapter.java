package com.coawesome.hosea.dr_r.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coawesome.hosea.dr_r.R;
import com.coawesome.hosea.dr_r.dao.QnaVO;

import java.util.ArrayList;

/**
 * Created by Hosea on 2016-11-01.
 */

public class QnaAdapter extends BaseAdapter {
    private Context dContext;
    private int dResource;
    private ArrayList<QnaVO> dItems = new ArrayList<>();
    private QnaVO qnaVO;

    public QnaAdapter(Context context, int resource, ArrayList<QnaVO> items) {
        dContext = context;
        dResource = resource;
        dItems = items;
    }

    @Override
    public int getCount() {
        return dItems.size();
    }

    @Override
    public Object getItem(int i) {
        return dItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) dContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(dResource, viewGroup, false);
        }

        TextView u_name = (TextView) view.findViewById(R.id.qna_tv_writer);
        TextView qna_title = (TextView) view.findViewById(R.id.qna_tv_title);
        TextView date = (TextView) view.findViewById(R.id.qna_tv_writeDate);
        TextView count = (TextView) view.findViewById(R.id.qna_tv_count);

        qnaVO = dItems.get(i);

        u_name.setText(qnaVO.getU_name());
        qna_title.setText(qnaVO.getQna_title());
        date.setText(qnaVO.getDate());
        count.setText("" + qnaVO.getCount());

        return view;
    }
}
