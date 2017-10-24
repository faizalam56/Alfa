package com.senzec.alfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.senzec.alfa.R;
import com.senzec.alfa.model.profile_detail.JobInfo;

import java.util.List;

/**
 * Created by senzec on 5/10/17.
 */

public class ProfileJobInfoAdapter extends BaseAdapter {

    Context mContext;
    View view;
    LayoutInflater inflater;
    List<JobInfo> jobInfoList;
    TextView tvDesignation, tvCompany, tvJoinYear, tvFinalYear;

    public ProfileJobInfoAdapter(Context mContext, List<JobInfo> jobInfoList){
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.jobInfoList = jobInfoList;
    }
    @Override
    public int getCount() {
        return jobInfoList.size();
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
        view = inflater.inflate(R.layout.row_profile_job_layout, null);
        tvDesignation = (TextView)view.findViewById(R.id.idDesignationTV);
        tvCompany = (TextView)view.findViewById(R.id.idCompanyTV);
        tvJoinYear = (TextView)view.findViewById(R.id.idJobJoiningYearTV);
        tvFinalYear = (TextView)view.findViewById(R.id.idJobFinalYearTV);

        tvDesignation.setText(jobInfoList.get(i).getJobDesignation());
        tvCompany.setText(jobInfoList.get(i).getCompanyName());
        tvJoinYear.setText(String.valueOf(jobInfoList.get(i).getJoiningYear()));
        tvFinalYear.setText(String.valueOf(jobInfoList.get(i).getFinalYear()));

        return view;
    }
}
