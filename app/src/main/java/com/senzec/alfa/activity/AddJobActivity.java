package com.senzec.alfa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.senzec.alfa.R;
import com.senzec.alfa.font.FontChangeCrawler;

public class AddJobActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_add_job;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_job);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_add_job = (Button) findViewById(R.id.btn_add_job);

        btn_add_job.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_job:
                startActivity(new Intent(AddJobActivity.this,CompaniesActivity.class));
                break;
            case R.id.iv_back:
                AddJobActivity.this.finish();
                break;
        }
    }
}
