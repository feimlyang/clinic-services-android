package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2019_seg2105_project.data.ServiceRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServiceViewModel extends ViewModel {

    private ServiceRepository serviceRepository;
    private MediatorLiveData<List<Map<String, String>>> servicesList = new MediatorLiveData<>();
    public AdminServiceViewModel(ServiceRepository repo)
    {
        this.serviceRepository = repo;
    }
    public LiveData<List<Map<String, String>>> getServicesListLiveData()
    {
        return servicesList;
    }





}
