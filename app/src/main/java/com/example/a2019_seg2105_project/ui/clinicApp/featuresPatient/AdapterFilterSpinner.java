package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2019_seg2105_project.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterFilterSpinner extends ArrayAdapter<SpinnerDataModel> {

    private List<SpinnerDataModel> spinnerList;
    private  Context context;
    private BookAppointmentFragment fragment;

    public AdapterFilterSpinner(Context context, List<SpinnerDataModel> spinnerList, BookAppointmentFragment fragment){
        super(context, R.layout.patient_fragment_bookappointment, spinnerList);
        this.context = context;
        this.spinnerList = spinnerList;
        this.fragment = fragment;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }


    public View getView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.patient_spinner_item_search, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.spinnerItem);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.spinnerCheckbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(spinnerList.get(position).getItem());

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                spinnerList.get(position).setSelected(isChecked);
                fragment.showFilteredResults();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}


