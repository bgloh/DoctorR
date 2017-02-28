package com.coawesome.hosea.dr_r.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.coawesome.hosea.dr_r.R;
import com.coawesome.hosea.dr_r.adapter.QnaAdapter;
import com.coawesome.hosea.dr_r.dao.QnaVO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ApplicationQnaActivity extends AppCompatActivity {
    private Intent previousIntent;
    private AQuery aq = new AQuery(this);
    TextView tv;
    ListView lv;
    Button btn;
    QnaAdapter qnaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_qna);
        previousIntent = getIntent();

        //이름 설정
        tv = (TextView) findViewById(R.id.tv_listView_title);
        tv.setText("Q & A 목록");
        btn = (Button) findViewById(R.id.btn_qna_makeQuestion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MakeQuestionActivity.class);
                intent.putExtra("u_id", previousIntent.getIntExtra("u_id", 0))
                        .putExtra("u_name", previousIntent.getStringExtra("u_name"));
                startActivity(intent);
            }
        });

        listingQna();
    }

    public void listingQna() {
        aq.ajax("http://52.41.218.18:8080/getQnaList", JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray html, AjaxStatus status) {
                if (html != null) {
                    jsonArrayToArrayList(html);
                } else {
                    tv.setText("연결 상태가 좋지 않습니다.");
                    ArrayList<QnaVO> arrayList = new ArrayList<QnaVO>();

                    arrayList.add(new QnaVO(1,"호세아","제3목","컨5텐츠", "날짜", 1));
                    arrayList.add(new QnaVO(1,"호아","제목2","컨텐41츠", "날짜", 1));
                    arrayList.add(new QnaVO(1,"호세","제1목","컨텐3츠", "날짜", 1));
                    arrayList.add(new QnaVO(1,"세아","제1목","컨텐2츠", "날짜", 1));
                    arrayList.add(new QnaVO(1,"세","제목","컨텐츠12", "날짜", 1));
                    arrayList.add(new QnaVO(1,"세아","3제목","컨32텐츠", "날짜", 1));
                    linkToAdapter(arrayList);
                }
            }
        });
    }

    public void jsonArrayToArrayList(JSONArray jsonArr) {
        ArrayList<QnaVO> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                arrayList.add(new QnaVO(jsonArr.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        linkToAdapter(arrayList);
    }

    public void linkToAdapter(ArrayList<QnaVO> list) {
        //어댑터 생성
        qnaAdapter = new QnaAdapter(this, R.layout.itemsforqnalist, list);
        //어댑터 연결
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(qnaAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viewClicked, int position, long id) {
                Toast.makeText(getApplicationContext(), ((QnaVO)qnaAdapter.getItem(position)).getQna_content(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


