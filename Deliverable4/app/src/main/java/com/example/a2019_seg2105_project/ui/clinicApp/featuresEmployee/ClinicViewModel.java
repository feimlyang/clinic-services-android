package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;

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

public class ClinicViewModel extends ViewModel {

    private ClinicRepository clinicRepository;
    private ServiceRepository serviceRepository;
    private static String clinicNamePattern = "([a-z]|[A-Z])(\\w|\\d|\\s)*$";

    public MediatorLiveData<Result> addServiceToProfileData = new MediatorLiveData<>();
    public MediatorLiveData<Result> deleteServiceFromProfileData = new MediatorLiveData<>();
    public MediatorLiveData<Result> getServicesOfferedListData = new MediatorLiveData<>();
    public MediatorLiveData<Result> getAvailableServicesData = new MediatorLiveData<>();
    public MediatorLiveData<Result> setClinicProfileData = new MediatorLiveData<>();
    public MediatorLiveData<Result> getProfileInfoData = new MediatorLiveData<>();
    public MediatorLiveData<Result> setWorkingHoursData = new MediatorLiveData<>();
    public MediatorLiveData<Result> getWorkingHoursData = new MediatorLiveData<>();
    public MutableLiveData<ClinicFormState> clinicFormState = new MutableLiveData<ClinicFormState>();


    public void clinicNameValidator(String username)
    {
        clinicFormState.setValue(Pattern.matches(clinicNamePattern, username) ? new ClinicFormState(null): new ClinicFormState(new Integer(1)));
    }

    public ClinicViewModel(ClinicRepository repo)
    {
        this.clinicRepository = repo;
        this.serviceRepository = ServiceRepository.getInstance();
    }


    /* add new service to clinic Profile by service Name, those services are created by admin user
     * too lookup attributes in each service, use the method in ServiceRepo*/
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

    /*Delete service from clinic profile, delete by service name */
    public void deleteServiceFromProfile(String employeeUsername, String serviceName){
        final LiveData<Result> resultLiveData = clinicRepository.deleteServiceFromProfile(employeeUsername.toLowerCase(), serviceName.toLowerCase());
        this.deleteServiceFromProfileData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                deleteServiceFromProfileData.removeSource(resultLiveData);
                deleteServiceFromProfileData.setValue(result);
                deleteServiceFromProfileData.setValue(null);
            }
        });
    }

    /*get all services that already added into profile in a list*/
    public void getServicesOfferedList(String employeeUsername){
        final LiveData<Result> resultLiveData = clinicRepository.getServicesOfferedList(employeeUsername.toLowerCase());
        this.getServicesOfferedListData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                getServicesOfferedListData.removeSource(resultLiveData);
                getServicesOfferedListData.setValue(result);
                getServicesOfferedListData.setValue(null);
            }
        });

    }
    public void listAvailableServices()
    {
        final LiveData<Result> resultLiveData = this.serviceRepository.getServicelist();
        this.getAvailableServicesData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                getAvailableServicesData.removeSource(resultLiveData);
                getAvailableServicesData.setValue(result);
                getAvailableServicesData.setValue(null);
            }
        });
    }

    /*set or reset profile */
    public void setClinicProfile(String employeeUsername, String clinicName, String clinicAddress, String clinicPhoneNum,
                      ArrayList<String> insuranceType, ArrayList<String> paymentMethod ){
        final LiveData<Result> resultLiveData = clinicRepository.setClinicProfile(employeeUsername.toLowerCase(), clinicName.toLowerCase(),
                clinicAddress.toLowerCase(), clinicPhoneNum.toLowerCase(),insuranceType,paymentMethod);
        this.setClinicProfileData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                setClinicProfileData.removeSource(resultLiveData);
                setClinicProfileData.setValue(result);
                setClinicProfileData.setValue(null);
            }
        });

    }

    /*get profile by give employee username if the profile is already set under this user
     * subKey could be either:clinicName, clinicAddress, clinicPhoneNum, insuranceType, paymentMethod */
    public void getProfileInfo(String employeeUsername, String subKey){
        final LiveData<Result> resultLiveData = clinicRepository.getProfileInfo(employeeUsername.toLowerCase(), subKey);
        this.getProfileInfoData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                getProfileInfoData.removeSource(resultLiveData);
                getProfileInfoData.setValue(result);
                getProfileInfoData.setValue(null);
            }
        });
    }


    public void setWorkingHours(String employeeUsername, String date,  ArrayList<String> listOfTimeSlot){
        final LiveData<Result> resultLiveData = clinicRepository.setWorkingHours(employeeUsername.toLowerCase(), date, listOfTimeSlot);
        this.setWorkingHoursData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                setWorkingHoursData.removeSource(resultLiveData);
                setWorkingHoursData.setValue(result);
                setWorkingHoursData.setValue(null);
            }
        });

    }
    public void getWorkingHours(String employeeUsername, String date){
        final LiveData<Result> resultLiveData = clinicRepository.getWorkingHours(employeeUsername.toLowerCase(), date);
        this.getWorkingHoursData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                getWorkingHoursData.removeSource(resultLiveData);
                getWorkingHoursData.setValue(result);
                getWorkingHoursData.setValue(null);
            }
        });

    }

}
