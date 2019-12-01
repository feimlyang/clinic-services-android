package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.a2019_seg2105_project.data.model.TimeSlot;
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
    private Spinner spinnerOfAddress, spinnerOfService, spinnerOfWorkingHours;
    private ArrayList<ClinicDataModel> clinicData;
    private ArrayList<SpinnerDataModel> spinnerAddressData, spinnerServiceData, spinnerWorkingHoursData;
    private Button returnButton;
    private ArrayList<String> selectedAddress = new ArrayList<>();
    private ArrayList<String> selectedService = new ArrayList<>();
    private ArrayList<String> selectedWorkingHours = new ArrayList<>();
    private ArrayList<String> addressList = new ArrayList<>();
    private ArrayList<String> serviceList = new ArrayList<>();
    private TimeSlot timeSlot; //spinner time slot
    private ArrayList<String> spinnerAddressList = new ArrayList<>();

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
        spinnerOfAddress = (Spinner) getActivity().findViewById(R.id.spinner_searchAddress);
        spinnerOfService = (Spinner) getActivity().findViewById(R.id.spinner_searchServices);
        spinnerOfWorkingHours = (Spinner) getActivity().findViewById(R.id.spinner_searchWorkingHours);
        spinnerAddressData = new ArrayList<>();
        spinnerServiceData = new ArrayList<>();
        spinnerWorkingHoursData = new ArrayList<>();

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

        //Address spinner view
        appointmentViewModel.getAddressSpinnerData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                selectedAddress.clear();
                if (result instanceof Result.Failure || result instanceof Result.Error) {
                    Toast.makeText(getContext(), "Failed to get spinner list.", Toast.LENGTH_SHORT).show();
                } else {
                    addressList = (ArrayList<String>) ((Result.Success) result).getData();
                    if (addressList == null) selectedService = null;
                    spinnerAddressData.add(new SpinnerDataModel("Not Selected", false));
                    for (String eachItem : addressList) {
                        SpinnerDataModel item = new SpinnerDataModel(eachItem, false);
                        spinnerAddressData.add(item);
                    }
                }
                AdapterFilterSpinner adapterAddressSpinner = new AdapterFilterSpinner(getContext(), spinnerAddressData, BookAppointmentFragment.this);
                spinnerOfAddress.setAdapter(adapterAddressSpinner);
                adapterAddressSpinner.notifyDataSetChanged();
            }
        });

       //Service spinner view
        appointmentViewModel.getServiceSpinnerData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                selectedService.clear();
                if (result instanceof Result.Failure || result instanceof Result.Error) {
                    Toast.makeText(getContext(), "Failed to get spinner list.", Toast.LENGTH_SHORT).show();
                } else {
                    serviceList = (ArrayList<String>) ((Result.Success) result).getData();
                    if (serviceList == null) selectedService = null;

                    spinnerServiceData.add(new SpinnerDataModel("Not Selected", false));
                    for (String eachItem : serviceList) {
                            SpinnerDataModel item = new SpinnerDataModel(eachItem, false);
                            spinnerServiceData.add(item);
                        }
                    }
                AdapterFilterSpinner adapterServiceSpinner = new AdapterFilterSpinner(getContext(), spinnerServiceData, BookAppointmentFragment.this);
                spinnerOfService.setAdapter(adapterServiceSpinner);
                adapterServiceSpinner.notifyDataSetChanged();
            }
        });


        //Wroking Hours spinner view
        spinnerWorkingHoursData.add(new SpinnerDataModel("Not Selected", false));
        for (String eachItem : timeSlot.getTimeSlots()) {
            SpinnerDataModel item = new SpinnerDataModel(eachItem, false);
            spinnerWorkingHoursData.add(item);
        }
        AdapterFilterSpinner adapterWHSpinner = new AdapterFilterSpinner(getContext(), spinnerWorkingHoursData, BookAppointmentFragment.this);
        spinnerOfWorkingHours.setAdapter(adapterWHSpinner);
        adapterWHSpinner.notifyDataSetChanged();


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


        //init search list
        appointmentViewModel.searchClinic(null, null, null);
        //int spinners
        appointmentViewModel.getAddressSpinner();
        appointmentViewModel.getServiceSpinner();

    }
    public void showFilteredResults()
    {
        ArrayList<String> checkedAddress = new ArrayList<>();
        for(SpinnerDataModel addressModel : spinnerAddressData)
        {
            if(addressModel.isSelected())
                checkedAddress.add(addressModel.getItem());
        }
        ArrayList<String> checkedServices = new ArrayList<>();
        for(SpinnerDataModel serviceModel : spinnerServiceData)
        {
            if(serviceModel.isSelected())
                checkedServices.add(serviceModel.getItem());
        }
        ArrayList<String> checkedWorkingHours = new ArrayList<>();
        for(SpinnerDataModel workingHourModel : spinnerWorkingHoursData)
        {
            if(workingHourModel.isSelected())
                checkedWorkingHours.add(workingHourModel.getItem());
        }

        appointmentViewModel.searchClinic(checkedAddress.size() == 0? null : checkedAddress,
                checkedServices.size() == 0? null : checkedServices,
                checkedWorkingHours.size() == 0? null : checkedWorkingHours);
    }
}
