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
        setContentView(R.layout.activity_enquiry);

//        lv_enquiry_message = (ListView) findViewById(R.id.lv_enquiry_message);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(this);

        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        /*EnquiryListAdapter adapter = new EnquiryListAdapter(this);
        lv_enquiry_message.setAdapter(adapter);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                EnquiryActivity.this.finish();
                break;
        }
    }
}

class EnquiryListAdapter extends BaseAdapter{
    Context context;
    public EnquiryListAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.enquiry_list_item,viewGroup,false);

        return view;
    }
}