package com.senzec.alfa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.senzec.alfa.R;
import com.senzec.alfa.font.FontChangeCrawler;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener{


    LinearLayout mUploadCv;
    ImageView mBackIV, mEditProfileIV;
    Button btn_create_job, btn_enquiries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_new);

        btn_enquiries = (Button)findViewById(R.id.btn_enquires) ;
        mUploadCv = (LinearLayout)findViewById(R.id.idUploadCv) ;
        mBackIV = (ImageView)findViewById(R.id.idBackIV);
        mEditProfileIV = (ImageView)findViewById(R.id.idEditProfileIV);
        btn_create_job = (Button) findViewById(R.id.btn_create_job);

        btn_enquiries.setOnClickListener(this);
        mUploadCv.setOnClickListener(MyProfileActivity.this);
        mBackIV.setOnClickListener(MyProfileActivity.this);
        mEditProfileIV.setOnClickListener(MyProfileActivity.this);
        btn_create_job.setOnClickListener(this);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) findViewById(android.R.id.content));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.idBackIV:
                startActivity(new Intent(MyProfileActivity.this, MainActivity.class));
                break;
            case R.id.idEditProfileIV:
                startActivity(new Intent(MyProfileActivity.this, EditProfileActivity.class));
                break;
             case R.id.btn_enquires:
                startActivity(new Intent(MyProfileActivity.this, GroupNameActivity.class));
                break;
            case R.id.idUploadCv:
                Toast.makeText(MyProfileActivity.this, "Upload CV Clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_create_job:
                startActivity(new Intent(MyProfileActivity.this, EditProfileActivity.class));
                break;
        }
    }
}
