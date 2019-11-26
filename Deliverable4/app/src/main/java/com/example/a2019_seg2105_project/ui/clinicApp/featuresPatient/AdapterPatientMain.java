package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a2019_seg2105_project.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPatientMain extends ArrayAdapter<AppointmentDataModel> {

    private List<AppointmentDataModel> appointmentData;
    Context context;

    public AdapterPatientMain(Context context, List<AppointmentDataModel> appointmentData) {
        super(context, R.layout.patient_fragment_home, appointmentData);
        this.appointmentData = appointmentData;
        this.context = context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int entryPosition = position;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.patient_listview_item_home, parent, false);

            TextView dateAndTime = (TextView) convertView.findViewById(R.id.textViewDateAndHours);
            dateAndTime.setText(appointmentData.get(position).getDateAndHours());

            TextView clinicNameInfo = (TextView) convertView.findViewById(R.id.textViewClinicNameInfo);
            clinicNameInfo.setText(appointmentData.get(position).getClinicName());

            TextView addressInfo = (TextView) convertView.findViewById(R.id.textViewAddressInfo);
            addressInfo.setText(appointmentData.get(position).getAddress());

            TextView serviceNameInfo = (TextView) convertView.findViewById(R.id.textViewServiceNameInfo);
            serviceNameInfo.setText(appointmentData.get(position).getServiceName());

            final Button checkIn = (Button) convertView.findViewById(R.id.btn_CheckIn);
            if (appointmentData.get(entryPosition).getIsCheckedIn() == true){
                checkIn.setEnabled(false);
            }
            else {
                checkIn.setEnabled(true);
            }
            Button rate = (Button) convertView.findViewById(R.id.btn_Rate);
            checkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appointmentData.get(entryPosition).setIsCheckedIn(true);
                    checkIn.setEnabled(false);
                }
            });

            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.patient_layout_home,
                            new RateClinicFragment()).addToBackStack(null).commit();
                }
            });
        }
        return convertView;
    }
}

