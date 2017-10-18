package com.senzec.alfa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzec.alfa.R;
import com.senzec.alfa.activity.GroupMemberActivity;
import com.senzec.alfa.utils.CircleImageView;

/**
 * Created by senzec on 7/10/17.
 */

public class CompaniesListAdapter extends RecyclerView.Adapter<CompaniesListAdapter.MyViewHolder>{

    Context context;
    public CompaniesListAdapter(Context context){
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.companies_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,GroupMemberActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView civ_img1,civ_img2,civ_img3;
        ImageView iv_go_forward;
        TextView tv_company_name;
        public MyViewHolder(View itemView) {
            super(itemView);

            civ_img1 = itemView.findViewById(R.id.civ_img1);
            civ_img2 = itemView.findViewById(R.id.civ_img2);
//            civ_img3 = itemView.findViewById(R.id.civ_img3);
            iv_go_forward = itemView.findViewById(R.id.iv_go_forward);
            tv_company_name = itemView.findViewById(R.id.tv_company_name);
        }
    }
}