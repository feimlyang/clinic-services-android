package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import com.example.a2019_seg2105_project.data.AppointmentRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.ClinicRepository;
import com.example.a2019_seg2105_project.data.ServiceRepository;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class AppointmentViewModel extends ViewModel{

    private ClinicRepository clinicRepository;
    private ServiceRepository serviceRepository;
    private AppointmentRepository appointmentRepository;

    public MutableLiveData<AppointmentFormState> clinicFormState = new MutableLiveData<AppointmentFormState>();


    public AppointmentViewModel(AppointmentRepository repo) {
        this.appointmentRepository = repo;
        this.clinicRepository = ClinicRepository.getInstance();
        this.serviceRepository = ServiceRepository.getInstance();
    }

}

