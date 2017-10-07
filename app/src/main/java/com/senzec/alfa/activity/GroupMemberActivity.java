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
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.utils.CircleImageView;

public class GroupMemberActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv_groupmember_list;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_group_member);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        rv_groupmember_list = (RecyclerView) findViewById(R.id.rv_groupmember_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_groupmember_list.setLayoutManager(layoutManager);

        GroupMemberAdapter adapter = new GroupMemberAdapter(this);
        rv_groupmember_list.setAdapter(adapter);

        iv_back.setOnClickListener(this);

        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())){
            case R.id.iv_back:
                GroupMemberActivity.this.finish();
                break;
        }
    }
}

class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.MyViewHolder>{

    Context context;
    public GroupMemberAdapter(Context context){
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_member_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,EnquiryActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView civ_img1;
        ImageView iv_go_forward;
        TextView tv_member_name;
        public MyViewHolder(View itemView) {
            super(itemView);

            civ_img1 = itemView.findViewById(R.id.civ_img1);
            iv_go_forward = itemView.findViewById(R.id.iv_go_forward);
            tv_member_name = itemView.findViewById(R.id.tv_member_name);
        }
    }
}