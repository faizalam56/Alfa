package com.senzec.alfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.senzec.alfa.R;

/**
 * Created by senzec on 7/10/17.
 */

public class EnquiryListAdapter extends BaseAdapter {
    Context context;
    public EnquiryListAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 1;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.enquiry_list_item,viewGroup,false);

        return view;
    }
}