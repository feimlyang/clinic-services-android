package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.ServiceRepository;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AdminServiceViewModel extends ViewModel {

    private final String serviceNamePattern = "^([a-z]|[A-Z])\\S+$";

    private ServiceRepository serviceRepository;
    private MediatorLiveData<List<Map<String, String>>> servicesList = new MediatorLiveData<>();
    private MediatorLiveData<Result> listServiceResult = new MediatorLiveData<>();
    private MediatorLiveData<Result> addServiceResult = new MediatorLiveData<>();
    private MediatorLiveData<Result> editServiceResult = new MediatorLiveData<>();
    private MediatorLiveData<Result> deleteServiceResult = new MediatorLiveData<>();
    private MutableLiveData<AdminServiceFormState> serviceFormState = new MutableLiveData<AdminServiceFormState>();
    public AdminServiceViewModel(ServiceRepository repo)
    {
        this.serviceRepository = repo;
    }
    public LiveData<Result> getServicesListLiveData()
    {
        return listServiceResult;
    }
    public LiveData<Result> getServicesAddLiveData()
    {
        return addServiceResult;
    }
    public LiveData<Result> getServicesEditLiveData()
    {
        return editServiceResult;
    }
    public LiveData<Result> getServicesDeleteLiveData()
    {
        return deleteServiceResult;
    }
    public LiveData<AdminServiceFormState> getServiceFormState(){ return serviceFormState;}
    public void validateServiceInfo(String service)
    {
        Integer serviceError = isServiceNameValid(service) ? null : R.string.service_name_invalid;
        AdminServiceFormState formState = new AdminServiceFormState(serviceError);
        serviceFormState.setValue(formState);
    }
    public void listService()
    {
        final LiveData<Result> resultLiveData = serviceRepository.getServicelist();
        this.listServiceResult.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                listServiceResult.removeSource(resultLiveData);
                listServiceResult.setValue(result);
                listServiceResult.setValue(null);
            }
        });
    }

    public void addService(String service, String category, String subCategory,
                           String rolePerforming)
    {
        final LiveData<Result> resultLiveData = serviceRepository.addService(service.toLowerCase(), category.toLowerCase(),
                subCategory.toLowerCase(), rolePerforming.toLowerCase());
        this.addServiceResult.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                addServiceResult.removeSource(resultLiveData);
                addServiceResult.setValue(result);
                addServiceResult.setValue(null);
            }
        });
    }
    public void editService(String service, String category, String subCategory,
                           String rolePerforming)
    {
        final LiveData<Result> resultLiveData = serviceRepository.editService(service.toLowerCase(),
                category.toLowerCase(), subCategory.toLowerCase(),rolePerforming.toLowerCase());
        this.editServiceResult.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                editServiceResult.removeSource(resultLiveData);
                editServiceResult.setValue(result);
                editServiceResult.setValue(null);
            }
        });
    }
    public void deleteService(String service)
    {
        final LiveData<Result> resultLiveData = serviceRepository.deleteService(service.toLowerCase());
        this.deleteServiceResult.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                deleteServiceResult.removeSource(resultLiveData);
                deleteServiceResult.setValue(result);
                deleteServiceResult.setValue(null);
            }
        });
    }
    private boolean isServiceNameValid(String service)
    {
        return Pattern.matches(this.serviceNamePattern, service);
    }
}
