package com.senzec.alfa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.senzec.alfa.R;
import com.senzec.alfa.adapter.GroupFeedAdapter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.groupfeed.GroupfeedModel;

import java.util.ArrayList;

public class GroupFeedActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv_group_feed;
    ImageView iv_menu;
    ArrayList<GroupfeedModel> groupfeedModelArrayList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_group_feed);

        //Data
        groupfeedModelArrayList = new ArrayList<>();
     //   groupfeedModelArrayList.add();

        rv_group_feed = (RecyclerView) findViewById(R.id.rv_group_feed);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_group_feed.setLayoutManager(layoutManager);

        GroupFeedAdapter adapter = new GroupFeedAdapter(this);
        rv_group_feed.setAdapter(adapter);

        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())){

        }
    }
}

