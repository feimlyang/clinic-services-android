package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import com.google.firebase.database.annotations.Nullable;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.AdapterPatientMain;

public class PatientMainFragment extends Fragment {

    private AppointmentViewModel appointmentViewModel;
    private ListView listOfAppointments;
    private List<Map<String, String>> appointmentAttributes;
    private List<PatientHomeModel> data;
    private Button returnButton;
    private Button bookAppointmentButton;
    GlobalObjectManager helper = GlobalObjectManager.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        //Set View Model
        View root = inflater.inflate(R.layout.patient_fragment_home, container, false);
//        appointmentViewModel =
//                ViewModelProviders.of(this).get(AppointmentViewModel.class);

        return root;
    }//end of onCreateView()

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data = new ArrayList<PatientHomeModel>();
        appointmentAttributes = new ArrayList<Map<String, String>>();
        listOfAppointments = (ListView) getActivity().findViewById(R.id.listView);
        listOfAppointments.setAdapter(new AdapterPatientMain(getContext(),data));
        returnButton = (Button) getActivity().findViewById(R.id.btn_Return);
        bookAppointmentButton = (Button) getActivity().findViewById(R.id.btn_BookAppointment);
        appointmentViewModel.getAllAppointmentsData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result == null) return;
                data.clear();
                appointmentAttributes.clear();
                if(result instanceof Result.Failure || result instanceof Result.Error)
                {
                    Toast.makeText(getContext(), "Failed to list all services.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Result.Success<Map<String, Map<String, String>>> services_map;
                    try{
                        services_map  = (Result.Success<Map<String, Map<String, String>>>)(result);

                    }catch (Exception e)
                    {
                        Map<String, Map<String, String>> emptyMap = new HashMap<>();
                        services_map = new Result.Success<Map<String, Map<String, String>>>(emptyMap);
                    }
                    Map<String, Map<String, String>> resultMap = services_map.getData();
                    for(String key : resultMap.keySet())
                    {
                        appointments.add(key);
                        appointmentAttributes.add(resultMap.get(key));
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>( getContext(), android.R.layout.simple_list_item_1, appointments);
                listOfAppointments.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


//        serviceViewModel.addServiceToProfileData.observe(this, new Observer<Result>() {
//            @Override
//            public void onChanged(Result result) {
//                if(null == result) return;
//                if(result instanceof Result.Success)
//                {
//                    Integer success = ((Result.Success<Integer>)result).getData();
//                    Toast.makeText(getContext(), getString(success), Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Integer failure = ((Result.Failure<Integer>)result).getData();
//                    Toast.makeText(getContext(), getString(failure), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.patient_layout_home, new BookAppointmentFragment());
                transaction.commit();
            }
        });
    }


//    private class MyAdapter extends ArrayAdapter<PatientHomeModel>{
//
//        private MyAdapter(Context context, List<PatientHomeModel>data){
//            super(context,R.layout.patient_listview_item_home,data);
//        }
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent){
//
//            ViewHolder mainViewHolder = null;
//            if(convertView == null){
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                convertView = inflater.inflate(R.layout.patient_listview_item_home,parent,false);
//                final ViewHolder viewHolder = new ViewHolder();
//                viewHolder.dateAndTime = (TextView) convertView.findViewById(R.id.textViewDateAndHours);
//                viewHolder.dateAndTime.setText(data.get(position).getDateAndHours());
//                viewHolder.clinicNameInfo = (TextView) convertView.findViewById(R.id.textViewClinicNameInfo);
//                viewHolder.clinicNameInfo.setText(data.get(position).getClinicName());
//                viewHolder.addressInfo = (TextView) convertView.findViewById(R.id.textViewAddressInfo);
//                viewHolder.addressInfo.setText(data.get(position).getAddress());
//                viewHolder.serviceNameInfo = (TextView) convertView.findViewById(R.id.textViewServiceNameInfo);
//                viewHolder.serviceNameInfo.setText(data.get(position).getServiceName());
//                viewHolder.waitingTimeInfo = (TextView) convertView.findViewById(R.id.textViewWaitingTimeInfo);
//                viewHolder.waitingTimeInfo.setText(data.get(position).getWaitingTime());
//                viewHolder.clinicName = (TextView) convertView.findViewById(R.id.textViewClinicName);
//                viewHolder.address = (TextView) convertView.findViewById(R.id.textViewAddress);
//                viewHolder.serviceName = (TextView) convertView.findViewById(R.id.textViewServiceName);
//                viewHolder.waitingTime = (TextView) convertView.findViewById(R.id.textViewWaitingTime);
//                viewHolder.checkIn = (Button) convertView.findViewById(R.id.btn_CheckIn);
//                viewHolder.rate = (Button) convertView.findViewById(R.id.btn_Rate);
//
////                viewHolder.clinicName.setText(getItem(position));
////                viewHolder.waitingTime.setText(getItem(position));
//
//
//                viewHolder.rate.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.addToBackStack(null);
//                        transaction.replace(R.id.patient_layout_itemHome, new RateClinicFragment());
//                        transaction.commit();
//                        viewHolder.rate.setEnabled(false);
//                        //need some changes!!!
//                    }
//                });
//
//                convertView.setTag(viewHolder);
//
//
//            }
//            else{
//                mainViewHolder = (ViewHolder) convertView.getTag();
////                mainViewHolder.dateAndTime.setText(getItem(position));
//            }
//            return convertView;
//        }
//
//    }
//
//    public class ViewHolder{
//        TextView dateAndTime;
//        TextView clinicNameInfo;
//        TextView clinicName;
//        TextView addressInfo;
//        TextView address;
//        TextView serviceNameInfo;
//        TextView serviceName;
//        TextView waitingTimeInfo;
//        TextView waitingTime;
//        Button checkIn;
//        Button rate;
//    }


}
