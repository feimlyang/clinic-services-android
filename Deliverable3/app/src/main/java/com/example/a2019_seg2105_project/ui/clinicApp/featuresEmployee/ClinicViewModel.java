package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.ClinicRepository;
import com.example.a2019_seg2105_project.data.model.Clinic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ClinicViewModel extends ViewModel {

    private ClinicRepository clinicRepository;

    private MediatorLiveData<Result> addServiceToProfileData = new MediatorLiveData<>();
    private MediatorLiveData<Result> deleteServiceFromProfileData = new MediatorLiveData<>();
    private MediatorLiveData<Result> setClinicProfileData = new MediatorLiveData<>();
    private MediatorLiveData<Result> setWorkingHoursData = new MediatorLiveData<>();
    private MediatorLiveData<ArrayList<String>> getServicesOfferedListData = new MediatorLiveData<>();
    private MediatorLiveData<ArrayList<String>> getProfileInfoData = new MediatorLiveData<>();
    private MediatorLiveData<ArrayList<String>> getWorkingHoursData = new MediatorLiveData<>();
    private MutableLiveData<ClinicFormState> clinicFormState = new MutableLiveData<ClinicFormState>();

    public ClinicViewModel(ClinicRepository repo)
    {
        this.clinicRepository = repo;
    }
    public LiveData<ClinicFormState> getServiceFormState(){ return clinicFormState;}

    public void addServiceToProfile(String employeeUsername, String serviceName)
    {
        final LiveData<Result> resultLiveData = clinicRepository.addServiceToProfile(employeeUsername.toLowerCase(), serviceName.toLowerCase());
        this.addServiceToProfileData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                addServiceToProfileData.removeSource(resultLiveData);
                addServiceToProfileData.setValue(result);
                addServiceToProfileData.setValue(null);
            }
        });
    }



}
