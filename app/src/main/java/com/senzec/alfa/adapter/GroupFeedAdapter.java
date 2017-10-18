package com.senzec.alfa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzec.alfa.R;
import com.senzec.alfa.activity.GroupNameActivity;
import com.senzec.alfa.model.groupfeed.GroupFeedResponseModel;
import com.senzec.alfa.utils.SharedPrefClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by senzec on 7/10/17.
 */

public class GroupFeedAdapter extends RecyclerView.Adapter<GroupFeedAdapter.MyViewHolder> implements Filterable{

    Context context;
    List<GroupFeedResponseModel.Result> mGroupfeedList;
//    private ArrayList<GroupfeedModel> mFilteredList;
    private List<GroupFeedResponseModel.Result> mFilteredList;
    private static final String TAG = "GroupFeedAdapter";
    public GroupFeedAdapter(Context context, List<GroupFeedResponseModel.Result> mGroupfeedList){
        this.context = context;
        this.mGroupfeedList = mGroupfeedList;
//        mFilteredList = mGroupfeedList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_group_name,tv_job_count;
        ImageView iv_go_forward;
        public MyViewHolder(View itemView) {
            super(itemView);

            tv_group_name = itemView.findViewById(R.id.idGroupNameTV);
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
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if(mGroupfeedList.size()>0){
            GroupFeedResponseModel.Result results = mGroupfeedList.get(position);
            try {
                holder.tv_group_name.setText(results.group_name);
                holder.tv_job_count.setText(results.jobCounts);
            }catch (ArrayIndexOutOfBoundsException aiobe){
                Log.e(TAG, "Error ; "+aiobe);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Group - "+holder.tv_group_name.getText(), Toast.LENGTH_LONG).show();
                  String group_name = holder.tv_group_name.getText().toString();
// context.startActivity(new Intent(context,GroupNameActivity.class));
                new SharedPrefClass(context).setCurrentGroup(group_name);
                Intent nameIntent = new Intent(context, GroupNameActivity.class);
           //     nameIntent.putExtra("current_group", group_name);
                context.startActivity(nameIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroupfeedList.size();
    }

    //FILTER
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mGroupfeedList;
                } else {
                    List<GroupFeedResponseModel.Result> filteredList = new ArrayList<>();
                    for (GroupFeedResponseModel.Result groupfeedModel : mGroupfeedList) {
                        //if (apiToViewModel.getShorts().toLowerCase().contains(charString)) {
                        if (groupfeedModel.group_name.toUpperCase().contains(charString)) {

                            filteredList.add(groupfeedModel);
                        }else if (groupfeedModel.group_name.toLowerCase().contains(charString)) {

                            filteredList.add(groupfeedModel);
                        }else if (groupfeedModel.group_name.contains(charString)) {

                            filteredList.add(groupfeedModel);
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
                mFilteredList = (List<GroupFeedResponseModel.Result>) filterResults.values;
                notifyDataSetChanged();
            }

            /*@Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<GroupFeedResponseModel.Result> FilteredArrList = new ArrayList<>();

                if (mFilteredList == null) {
                    mFilteredList = new ArrayList<>(mGroupfeedList); // saves the original data in mOriginalValues
                }

                *//********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********//*
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mFilteredList.size();
                    results.values = mFilteredList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mFilteredList.size(); i++) {
                        Log.i(TAG, "Filter : " + mFilteredList.get(i).group_name + " -> " + mFilteredList.get(i).jobCounts);
                        String data = mFilteredList.get(i).group_name;
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(mFilteredList.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }*/
        };
    }

}