package com.senzec.alfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.senzec.alfa.R;
import com.senzec.alfa.model.profile_detail.AcademicInfo;

import java.util.List;

/**
 * Created by senzec on 5/10/17.
 */

public class ProfileAcademicAdapter extends BaseAdapter {

    Context mContext;
    View view;
    LayoutInflater inflater;
    TextView tvQualification, tvInstitute, tvJoinYear, tvFinalYear;
    List<AcademicInfo> academicInfosList;
    public ProfileAcademicAdapter(Context mContext, List<AcademicInfo> academicInfosList){
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.academicInfosList = academicInfosList;
    }
    @Override
    public int getCount() {
        return academicInfosList.size();
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
        view = inflater.inflate(R.layout.row_profile_academic_layout, null);
        tvQualification = (TextView)view.findViewById(R.id.idDegreeTV);
        tvInstitute = (TextView)view.findViewById(R.id.idCollegeTV);
        tvJoinYear = (TextView)view.findViewById(R.id.idAcademicJoinYear);
        tvFinalYear = (TextView)view.findViewById(R.id.idAcademicFinalYear);

        /* SET DATA TO LIST */
        tvQualification.setText(academicInfosList.get(i).getQualification());
        tvInstitute.setText(academicInfosList.get(i).getCollegeName());
        tvJoinYear.setText(String.valueOf(academicInfosList.get(i).getJoiningYear()));
        tvFinalYear.setText(String.valueOf(academicInfosList.get(i).getFinalYear()));


        return view;
    }
}
