package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a2019_seg2105_project.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPatientMain extends ArrayAdapter<PatientHomeModel> {

    private List<PatientHomeModel> data;

    public AdapterPatientMain(Context context, List<PatientHomeModel> data){
        super(context,R.layout.patient_fragment_home,data);
        this.data = data;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){


        ViewHolder mainViewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.patient_listview_item_home,parent,false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.dateAndTime = (TextView) convertView.findViewById(R.id.textViewDateAndHours);
            viewHolder.dateAndTime.setText(data.get(position).getDateAndHours());
            viewHolder.clinicNameInfo = (TextView) convertView.findViewById(R.id.textViewClinicNameInfo);
            viewHolder.clinicNameInfo.setText(data.get(position).getClinicName());
            viewHolder.addressInfo = (TextView) convertView.findViewById(R.id.textViewAddressInfo);
            viewHolder.addressInfo.setText(data.get(position).getAddress());
            viewHolder.serviceNameInfo = (TextView) convertView.findViewById(R.id.textViewServiceNameInfo);
            viewHolder.serviceNameInfo.setText(data.get(position).getServiceName());
            viewHolder.waitingTimeInfo = (TextView) convertView.findViewById(R.id.textViewWaitingTimeInfo);
            viewHolder.waitingTimeInfo.setText(data.get(position).getWaitingTime());
            viewHolder.clinicName = (TextView) convertView.findViewById(R.id.textViewClinicName);
            viewHolder.address = (TextView) convertView.findViewById(R.id.textViewAddress);
            viewHolder.serviceName = (TextView) convertView.findViewById(R.id.textViewServiceName);
            viewHolder.waitingTime = (TextView) convertView.findViewById(R.id.textViewWaitingTime);
            viewHolder.checkIn = (Button) convertView.findViewById(R.id.btn_CheckIn);
            viewHolder.rate = (Button) convertView.findViewById(R.id.btn_Rate);

//                viewHolder.clinicName.setText(getItem(position));
//                viewHolder.waitingTime.setText(getItem(position));


//            viewHolder.rate.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.addToBackStack(null);
//                    transaction.replace(R.id.patient_layout_itemHome, new RateClinicFragment());
//                    transaction.commit();
//                    viewHolder.rate.setEnabled(false);
//                    //need some changes!!!
//                }
//            });

            convertView.setTag(viewHolder);


        }
        else{
            mainViewHolder = (ViewHolder) convertView.getTag();
//                mainViewHolder.dateAndTime.setText(getItem(position));
        }
        return convertView;
    }
    public class ViewHolder{
        TextView dateAndTime;
        TextView clinicNameInfo;
        TextView clinicName;
        TextView addressInfo;
        TextView address;
        TextView serviceNameInfo;
        TextView serviceName;
        TextView waitingTimeInfo;
        TextView waitingTime;
        Button checkIn;
        Button rate;
    }

}




