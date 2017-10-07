package com.senzec.alfa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzec.alfa.R;
import com.senzec.alfa.activity.GroupMemberActivity;

import java.util.ArrayList;

/**
 * Created by senzec on 7/10/17.
 */

public class GroupFeedAdapter extends RecyclerView.Adapter<GroupFeedAdapter.MyViewHolder> {

    Context context;
   /* ArrayList<ApiToViewModel> mFrontList;
    private ArrayList<ApiToViewModel> mFilteredList;*/
    public GroupFeedAdapter(Context context){
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_group_name,tv_job_count;
        ImageView iv_go_forward;
        public MyViewHolder(View itemView) {
            super(itemView);

            tv_group_name = itemView.findViewById(R.id.idGroupName);
            tv_job_count = itemView.findViewById(R.id.tv_job_count);
            iv_go_forward = itemView.findViewById(R.id.iv_go_forward);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_feed_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_group_name.setText("Group Name - "+(position+1));
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

    //FILTER
    /*@Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mFrontList;
                } else {
                    ArrayList<ApiToViewModel> filteredList = new ArrayList<>();
                    for (ApiToViewModel apiToViewModel : mFrontList) {
                        //if (apiToViewModel.getShorts().toLowerCase().contains(charString)) {
                        if (apiToViewModel.getShorts().toUpperCase().contains(charString)) {

                            filteredList.add(apiToViewModel);
                        }else if (apiToViewModel.getShorts().toLowerCase().contains(charString)) {

                            filteredList.add(apiToViewModel);
                        }else if (apiToViewModel.getShorts().contains(charString)) {

                            filteredList.add(apiToViewModel);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ApiToViewModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
*/
}