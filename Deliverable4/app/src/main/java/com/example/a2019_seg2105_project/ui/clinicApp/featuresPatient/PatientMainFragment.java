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
import java.util.Set;

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
    private List<AppointmentDataModel> appointmentsData;
    private Button returnButton, bookAppointmentButton;

    GlobalObjectManager helper = GlobalObjectManager.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        appointmentViewModel = ViewModelProviders.of(this, new AppointmentModelFactory()).get(AppointmentViewModel.class);
        View root = inflater.inflate(R.layout.patient_fragment_home, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appointmentsData = new ArrayList<>();


        listOfAppointments = (ListView) getActivity().findViewById(R.id.appointListView);
        returnButton = (Button) getActivity().findViewById(R.id.btn_Return);
        bookAppointmentButton = (Button) getActivity().findViewById(R.id.btn_BookAppointment);

        appointmentViewModel.getAllAppointmentsData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result == null) return;
                appointmentsData.clear();
                if(result instanceof Result.Failure || result instanceof Result.Error)
                {
                    Toast.makeText(getContext(), "Failed to get appointments.", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Map<String, Map<String, String>> appointListResult =
                            (Map<String, Map<String, String>>)((Result.Success)result).getData();
                        //AppointmentDataModel(String dateAndHours, String clinicName, String address, String serviceName,String waitingTime)
                        for (String eachAppointTime : appointListResult.keySet()){
                            Map<String, String> appointInfo = appointListResult.get(eachAppointTime);
                            Boolean covertIsCheckedIn = Boolean.valueOf(appointInfo.get("isCheckedIn"));
                            AppointmentDataModel appointAttributes = new AppointmentDataModel(appointInfo.get("employeeName"), eachAppointTime, appointInfo.get("clinicName"),
                                appointInfo.get("clinicAddress"), appointInfo.get("bookedService"), appointInfo.get("waitingTime"),
                                    covertIsCheckedIn);
                            appointmentsData.add(appointAttributes);

                        }
                    }

                AdapterPatientMain appointmentAdapter  = new AdapterPatientMain( getContext(), appointmentsData, PatientMainFragment.this);
                listOfAppointments.setAdapter(appointmentAdapter);
                appointmentAdapter.notifyDataSetChanged();
            }
        });


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

        appointmentViewModel.getAllAppointments(helper.getCurrentUsername());
    }

    public void setCheckedIn (String dateAndHours){
        appointmentViewModel.isCheckedIn(helper.getCurrentUsername(), dateAndHours);
    }



}
