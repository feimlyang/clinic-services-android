package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.model.Clinic;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.AppointmentViewModel;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.PatientMainFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookAppointmentFragment extends Fragment {

    private AppointmentViewModel appointmentViewModel;
    private ListView listOfClinics;
    private List<ClinicDataModel> clinicData;
    private Button returnButton;
    //private ArrayList<HashMap<String, String>> searchResult;
    // GlobalObjectManager helper = GlobalObjectManager.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        appointmentViewModel = ViewModelProviders.of(this, new AppointmentModelFactory()).get(AppointmentViewModel.class);
        View root = inflater.inflate(R.layout.patient_fragment_bookappointment, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listOfClinics = (ListView) getActivity().findViewById(R.id.listViewSearch);
        clinicData = new ArrayList<>();

        appointmentViewModel.searchClinicData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                clinicData.clear();
                if (result instanceof Result.Failure || result instanceof Result.Error) {
                    Toast.makeText(getContext(), "Failed to list search result.", Toast.LENGTH_SHORT).show();
                } else {
                    Result.Success<ArrayList<HashMap<String, String>>> searchResult;
                    try {
                        searchResult = (Result.Success<ArrayList<HashMap<String, String>>>) (result);

                    } catch (Exception e) {
                        ArrayList<HashMap<String, String>> emptyMap = new ArrayList<>();
                        searchResult = new Result.Success<ArrayList<HashMap<String, String>>>(emptyMap);
                    }
                    ArrayList<HashMap<String, String>> resultList = searchResult.getData();

                    for (HashMap clinicMap : resultList) {
                        ClinicDataModel clinicInfo = new ClinicDataModel(
                                (String) clinicMap.get("employeeName"),
                                (String) clinicMap.get("clinicName"),
                                (String) clinicMap.get("clinicAddress"),
                                (String) clinicMap.get("clinicRate"));
                        clinicData.add(clinicInfo);
                    }
                }

                AdapterClinicSearch adapterClinicSearch = new AdapterClinicSearch(getContext(), clinicData);
                listOfClinics.setAdapter(adapterClinicSearch);
                adapterClinicSearch.notifyDataSetChanged();
            }
        });


        Button returnButton = (Button) getActivity().findViewById(R.id.btn_Return);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.patient_layout_bookAppointment, new PatientMainFragment());
                transaction.commit();
            }
        });


        //function calls
        //defualt search:list all clinc info
        appointmentViewModel.searchClinic(null, null, null);

    }
}
