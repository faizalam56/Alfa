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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzec.alfa.R;
import com.senzec.alfa.font.FontChangeCrawler;

public class GroupNameActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv_group_feed;
    ImageView iv_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_group_name);

        rv_group_feed = (RecyclerView) findViewById(R.id.rv_group_feed);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_group_feed.setLayoutManager(layoutManager);

        GroupNameAdapter adapter = new GroupNameAdapter(this);
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

class GroupNameAdapter extends RecyclerView.Adapter<GroupNameAdapter.MyViewHolder>{
    Context context;
    public GroupNameAdapter(Context context){
        this.context = context;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvPosition, tvdept, tvPrice, tvAddress, tvYear;
        ImageView ivProfile;
        Button btnSendCV;
        LinearLayout mReplylayout, mShareLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.idPosition);
            tvdept = itemView.findViewById(R.id.idDept);
            tvPrice = itemView.findViewById(R.id.idPrice);
            tvAddress = itemView.findViewById(R.id.idAddress);
            tvYear = itemView.findViewById(R.id.idYear);
            ivProfile = itemView.findViewById(R.id.idProfile);
            btnSendCV = itemView.findViewById(R.id.idSendCV);
            mReplylayout = itemView.findViewById(R.id.idReplyLayout);
            mShareLayout = itemView.findViewById(R.id.idShareLayout);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_name_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,GroupMemberActivity.class));
            }
        });
        holder.mReplylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Reply", Toast.LENGTH_LONG).show();
            }
        });
        holder.mShareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Share", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}