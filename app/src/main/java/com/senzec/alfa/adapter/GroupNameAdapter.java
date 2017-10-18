package com.senzec.alfa.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.senzec.alfa.activity.EnquiryActivity;
import com.senzec.alfa.activity.GroupMemberActivity;
import com.senzec.alfa.model.group_name.GroupNameWebApiModel;
import com.senzec.alfa.model.group_name.Result;
import com.senzec.alfa.socket_chat.SocketChatActivity;
import com.senzec.alfa.utils.SharedPrefClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by senzec on 7/10/17.
 */

public class GroupNameAdapter extends RecyclerView.Adapter<GroupNameAdapter.MyViewHolder>{

    Context mContext;
    List<Result> groupNameResultList;
    public GroupNameAdapter(Context mContext, List<Result> groupNameResultList){
        this.mContext = mContext;
        this.groupNameResultList = groupNameResultList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvdept, tvPrice, tvAddress, tvYear;
        ImageView ivProfile;
        Button btnSendCV;
        LinearLayout mReplylayout, mShareLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.idPosition);
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvName.setText(groupNameResultList.get(position).getJobsDetail().getJobName());
        holder.tvdept.setText(groupNameResultList.get(position).getJobsDetail().getJobType());
        holder.tvPrice.setText(String.valueOf(groupNameResultList.get(position).getJobsDetail().getSalary()));
        holder.tvYear.setText(groupNameResultList.get(position).getJobsDetail().getJobExperience()+" year");

        holder.mReplylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        mContext.startActivity(new Intent(mContext, SocketChatActivity.class));
         //       Toast.makeText(mContext, "Reply", Toast.LENGTH_LONG).show();
     //           Toast.makeText(mContext, "Output : "+position, Toast.LENGTH_LONG).show();
                new SharedPrefClass(mContext).setReceiverID(groupNameResultList.get(position).getJobsDetail().getJobCreaterId().getId());
                new SharedPrefClass(mContext).setPostID(groupNameResultList.get(position).getJobsDetail().getId());
                mContext.startActivity(new Intent(mContext, SocketChatActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupNameResultList.size();
    }
}