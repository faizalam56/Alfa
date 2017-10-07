package com.senzec.alfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.senzec.alfa.R;

/**
 * Created by senzec on 5/10/17.
 */

public class CustomJobInfoAdapter extends BaseAdapter {

    Context mContext;
    View view;
    LayoutInflater inflater;
    int size;
    TextView tvDesignation, tvCompany, tvJoinYear, tvFinalYear;

    public CustomJobInfoAdapter(Context mContext, int size){
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.size = size;
    }
    @Override
    public int getCount() {
        return size;
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
        view = inflater.inflate(R.layout.row_job_layout, null);
        tvDesignation = (TextView)view.findViewById(R.id.idDesignationET);
        tvCompany = (TextView)view.findViewById(R.id.idCompanyName);
        tvJoinYear = (TextView)view.findViewById(R.id.idJoinYearJobET);
        tvFinalYear = (TextView)view.findViewById(R.id.idFinalYearJobET);

        return view;
    }
}
