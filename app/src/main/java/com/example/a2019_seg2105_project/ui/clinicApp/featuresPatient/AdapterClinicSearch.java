package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterClinicSearch extends ArrayAdapter<ClinicDataModel> {

    private List<ClinicDataModel> clinicData;
    Context context;

    public AdapterClinicSearch(Context context, List<ClinicDataModel> clinicData) {
        super(context, R.layout.patient_fragment_bookappointment, clinicData);
        this.clinicData = clinicData;
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int entryPosition = position;

        convertView = LayoutInflater.from(context).inflate(R.layout.patient_listview_item_bookappointment, parent,
                false);
        TextView clinicNameView = (TextView) convertView.findViewById(R.id.textViewClinicNameInfo);
        clinicNameView.setText(clinicData.get(position).getClinicName());

        TextView clinicAddressView = (TextView) convertView.findViewById(R.id.textViewAddressInfo);
        clinicAddressView.setText(clinicData.get(position).getClinicAddress());


        TextView clinicRatingView = (TextView) convertView.findViewById(R.id.textViewRatingInfo);
        clinicRatingView.setText(clinicData.get(position).getRating());

        Button bookAppointment = (Button) convertView.findViewById(R.id.btn_bookAppointment);
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClinicDataModel clinicModel = clinicData.get(entryPosition);
                Map<String, String> attributes = new HashMap<>();
                attributes.put("employeeName", clinicModel.getEmployeeName());
                attributes.put("clinicName", clinicModel.getClinicName());
                attributes.put("clinicAddress", clinicModel.getClinicAddress());
                attributes.put("clinicRate", clinicModel.getRating());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.patient_layout_bookAppointment,
                        new SelectServiceAndTimeFragment(attributes)).addToBackStack(null).commit();
            }
        });
        return convertView;
    }

}
