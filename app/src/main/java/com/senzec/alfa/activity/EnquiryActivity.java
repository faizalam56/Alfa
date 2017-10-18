package com.senzec.alfa.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.senzec.alfa.R;
import com.senzec.alfa.font.FontChangeCrawler;

public class EnquiryActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lv_enquiry_message;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_enquiry);

//        lv_enquiry_message = (ListView) findViewById(R.id.lv_enquiry_message);
        iv_back = (ImageView) findViewById(R.id.iv_back);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                EnquiryActivity.this.finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}

