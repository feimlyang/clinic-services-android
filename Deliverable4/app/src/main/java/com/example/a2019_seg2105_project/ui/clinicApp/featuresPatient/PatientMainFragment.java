package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import android.os.Bundle;
import android.view.LayoutInflater;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.google.firebase.database.annotations.Nullable;

public class PatientMainFragment extends Fragment {

    private AppointmentViewModel appointmentViewModel;
    private ListView listOfAppointments;
    private List<Map<String, String>> appointmentAttributes;
    private List<String> appointments;
    private Button returnButton;
    private Button bookAppointmentButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        //Set View Model
        View root = inflater.inflate(R.layout.patient_fragment_home, container, false);
        appointmentViewModel =
                ViewModelProviders.of(this).get(AppointmentViewModel.class);

        return root;
    }//end of onCreateView()

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appointments = new ArrayList<String>();
        appointmentAttributes = new ArrayList<Map<String, String>>();
        listOfAppointments = (ListView) getActivity().findViewById(R.id.listView);
        listOfAppointments.setAdapter(new MyAdapter(getContext(),R.layout.patient_listview_item_home,appointments));
        returnButton = (Button) getActivity().findViewById(R.id.btn_Return);
        bookAppointmentButton = (Button) getActivity().findViewById(R.id.btn_BookAppointment);
//        appointmentViewModel.getAvailableServicesData.observe(this, new Observer<Result>() {
//            @Override
//            public void onChanged(Result result) {
//                if(result == null) return;
//                services.clear();
//                servicesAttributes.clear();
//                if(result instanceof Result.Failure || result instanceof Result.Error)
//                {
//                    Toast.makeText(getContext(), "Failed to list all services.", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Result.Success<Map<String, Map<String, String>>> services_map;
//                    try{
//                        services_map  = (Result.Success<Map<String, Map<String, String>>>)(result);
//
//                    }catch (Exception e)
//                    {
//                        Map<String, Map<String, String>> emptyMap = new HashMap<>();
//                        services_map = new Result.Success<Map<String, Map<String, String>>>(emptyMap);
//                    }
//                    Map<String, Map<String, String>> resultMap = services_map.getData();
//                    for(String key : resultMap.keySet())
//                    {
//                        services.add(key);
//                        servicesAttributes.add(resultMap.get(key));
//                    }
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>( getContext(), android.R.layout.simple_list_item_1, services);
//                listOfServices.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//        });

//        listOfServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final String serviceName = services.get(position);
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
//                dialogBuilder.setTitle(serviceName);
//                dialogBuilder.setMessage("Would you like to add it to your clinic?");
//                dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i){
//                        appointmentViewModel.addServiceToProfile(helper.getCurrentUsername(),serviceName);
//                    }
//                } );
//                dialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i){
//                        Toast.makeText(getContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dialogBuilder.show();
//            }
//        });

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
//        serviceViewModel.listAvailableServices();
    }

    private class MyAdapter extends ArrayAdapter<String>{

        private int layout;
        private MyAdapter(Context context, int resource, List<String>objects){
            super(context,resource,objects);
            layout = resource;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.dateAndTime = (TextView) convertView.findViewById(R.id.textViewDateAndHours);
                viewHolder.clinicName = (TextView) convertView.findViewById(R.id.textViewClinicNameInfo);
                viewHolder.address = (TextView) convertView.findViewById(R.id.textViewAddressInfo);
                viewHolder.serviceName = (TextView) convertView.findViewById(R.id.textViewServiceNameInfo);
                viewHolder.waitingTime = (TextView) convertView.findViewById(R.id.textViewWaitingTimeInfo);
                viewHolder.checkIn = (Button) convertView.findViewById(R.id.btn_CheckIn);
                viewHolder.rate = (Button) convertView.findViewById(R.id.btn_Rate);

                viewHolder.checkIn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getContext(),"You've checked in successfully",Toast.LENGTH_SHORT).show();
                        //viewHolder.checkIn.setEnabled(false);
                        //need some changes!!!
                    }
                });
                viewHolder.rate.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.patient_layout_itemHome, new RateClinicFragment());
                        transaction.commit();
                        //viewHolder.rate.setEnabled(false);
                        //need some changes!!!
                    }
                });

                convertView.setTag(viewHolder);


            }
//            else{
//                mainViewHolder = (ViewHolder) convertView.getTag();
//                mainViewHolder.
//            }
            return convertView;
        }

    }

    public class ViewHolder{
        TextView dateAndTime;
        TextView clinicName;
        TextView address;
        TextView serviceName;
        TextView waitingTime;
        Button checkIn;
        Button rate;
    }


}
