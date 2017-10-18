package com.senzec.alfa.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzec.alfa.R;
import com.senzec.alfa.adapter.CompaniesListAdapter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.utils.CircleImageView;

public class CompaniesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv_companies_list;
    ImageView mBackIV, mProfileIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_companies);

        mBackIV = (ImageView) findViewById(R.id.idBackIV);
        mProfileIV = (ImageView)findViewById(R.id.idProfileIV) ;

        rv_companies_list = (RecyclerView) findViewById(R.id.rv_companies_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_companies_list.setLayoutManager(layoutManager);

        CompaniesListAdapter adapter = new CompaniesListAdapter(this);
        rv_companies_list.setAdapter(adapter);

        mBackIV.setOnClickListener(this);
        mProfileIV.setOnClickListener(this);

        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idBackIV:
                CompaniesActivity.this.finish();
                break;
            case R.id.idProfileIV:
                startActivity(new Intent(CompaniesActivity.this, MyProfileActivity.class));
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}


