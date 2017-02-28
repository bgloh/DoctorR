package com.coawesome.hosea.dr_r.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coawesome.hosea.dr_r.R;
import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends AppCompatActivity {

    List<Integer> galleryId = new ArrayList<>();
    int currentImageNum;
    ImageView ivNow;
    Button btnPre, btnNext;
    TextView tv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        currentImageNum = 1;
        for (int i = 1; i < 7; i++) {
            galleryId.add(getResources().getIdentifier("t" + i, "drawable", this.getPackageName()));
        }

        tv = (TextView) findViewById(R.id.tv_help_now);
        tv.setText(currentImageNum + " / " + galleryId.size());

        ivNow = (ImageView) findViewById(R.id.iv_help);
        btnNext = (Button) findViewById(R.id.btn_help_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currentImageNum > galleryId.size()-1)) {
                    currentImageNum ++;
                    ivNow.setImageResource(galleryId.get(currentImageNum-1));
                    tv.setText(currentImageNum + " / " + galleryId.size());

                }
            }
        });

        btnPre = (Button) findViewById(R.id.btn_help_previous);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currentImageNum-1 < 1)) {
                    currentImageNum --;
                    ivNow.setImageResource(galleryId.get(currentImageNum-1));
                    tv.setText(currentImageNum + " / " + galleryId.size());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*public boolean dispatchTouchEvent(MotionEvent ev) {

        if(mGestureDetector.onTouchEvent(ev)){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public class GalleryAdapter extends BaseAdapter {

        private final Context mContext;
        LayoutInflater inflater;

        public GalleryAdapter(Context c) {
            mContext = c;
            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return galleryId.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.itemsforslide, parent, false);
            }
            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.img_slide);
            imageView.setImageResource(galleryId.get(position));
            return convertView;
        }
    }*/


}
