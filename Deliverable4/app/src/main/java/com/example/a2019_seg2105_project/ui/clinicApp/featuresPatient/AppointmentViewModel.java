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


public class AppointmentViewModel extends ViewModel {

    private ClinicRepository clinicRepository;
    private ServiceRepository serviceRepository;
    private AppointmentRepository appointmentRepository;

    public MediatorLiveData<Result> getAddressSpinnerData = new MediatorLiveData<>();
    public MediatorLiveData<Result> getServiceSpinnerData = new MediatorLiveData<>();
    public MediatorLiveData<Result> getServicesListLiveData = new MediatorLiveData<>();
    public MediatorLiveData<Result> searchClinicData = new MediatorLiveData<>();
    public MediatorLiveData<Result> addAppointmentData = new MediatorLiveData<>();
    public MediatorLiveData<Result> getAllAppointmentsData = new MediatorLiveData<>();
    public MediatorLiveData<Result> isCheckedInData = new MediatorLiveData<>();
    public MediatorLiveData<Result> calculateWaitingTimeData = new MediatorLiveData<>();
    public MediatorLiveData<Result> rateAppointmentData = new MediatorLiveData<>();
    public MediatorLiveData<Result> getWorkingHoursData = new MediatorLiveData<>();



    public AppointmentViewModel(AppointmentRepository repo) {
        this.appointmentRepository = repo;
        this.clinicRepository = ClinicRepository.getInstance();
        this.serviceRepository = ServiceRepository.getInstance();
    }


    /*spinner value for existing address in all clinics*/
    public void getAddressSpinner() {
        final LiveData<Result> resultLiveData = appointmentRepository.getAddressSpinner();
        this.getAddressSpinnerData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                getAddressSpinnerData.removeSource(resultLiveData);
                getAddressSpinnerData.setValue(result);
                getAddressSpinnerData.setValue(null);
            }
        });
    }

    /*spinner value for existing serviceOffered in all clinics*/
    public void getServiceSpinner() {
        final LiveData<Result> resultLiveData = appointmentRepository.getServiceSpinner();
        this.getServiceSpinnerData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                getServiceSpinnerData.removeSource(resultLiveData);
                getServiceSpinnerData.setValue(result);
                getServiceSpinnerData.setValue(null);
            }
        });
    }

    public void getServicesOfClinic(String employeename) {
        final LiveData<Result> servicesLiveData = this.clinicRepository.getServicesOfferedList(employeename);
        this.getServicesListLiveData.addSource(servicesLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                getServicesListLiveData.removeSource(servicesLiveData);
                getServicesListLiveData.setValue(result);
                getServicesListLiveData.setValue(null);
            }
        });




    }


    /*search for a walk in clinic by address, working hours, type of services
     * returns ArrayList of hashmap
     * List( employeeName[i]( List of clinicInfo[i]: Map(elemKey, elemValue))
     * clinicInfo[i] includes: employeeName, clinicName, clinicAddress, rate->score(String)
     *
     * Input: ArraysList(address), ArrayList(serviceOffered), ArrayList(timeslot)*/
    public void searchClinic( ArrayList<String> selectedAddressList,
                                          ArrayList<String> selectedServiceList,
                                          ArrayList<String> selectedTimeSlotList){

        final LiveData<Result> resultLiveData = appointmentRepository.searchClinic(
                selectedAddressList, selectedServiceList, selectedTimeSlotList);
        this.searchClinicData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                searchClinicData.removeSource(resultLiveData);
                searchClinicData.setValue(result);
                searchClinicData.setValue(null);
            }
        });
    }


    /*book an appointment, should provide all of the appointment info */
    public void addAppointment( String patientUsername, String dateTime,
                                            String employeeUsername, String clinicName, String clinicAddress,
                                String bookedService){
        final LiveData<Result> resultLiveData = appointmentRepository.addAppointment(
            patientUsername,dateTime, employeeUsername, clinicName, clinicAddress, bookedService);
        this.addAppointmentData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                addAppointmentData.removeSource(resultLiveData);
                addAppointmentData.setValue(result);
                addAppointmentData.setValue(null);
            }
        });
    }


    /*get all appointmnet information for a paitent*/
    public void getAllAppointments(String patientUsername){
        final LiveData<Result> resultLiveData = appointmentRepository.getAllAppointments(patientUsername);
        this.getAllAppointmentsData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                getAllAppointmentsData.removeSource(resultLiveData);
                getAllAppointmentsData.setValue(result);
                getAllAppointmentsData.setValue(null);
            }
        });
    }


    /*checkin a appointment if it is booked*/
    public void isCheckedIn(String patientUsername, String dateTime){
        final LiveData<Result> resultLiveData = appointmentRepository.isCheckedIn(patientUsername, dateTime);
        this.isCheckedInData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                isCheckedInData.removeSource(resultLiveData);
                isCheckedInData.setValue(result);
                isCheckedInData.setValue(null);
            }
        });
    }

    /*calculate the waiting hours on a certain clinic and a certain datetime
     * 15mins for each person in line*/
    public void calculateWaitingTime(String employeeUsername, String dateTime){
        final LiveData<Result> resultLiveData = appointmentRepository.calculateWaitingTime(employeeUsername, dateTime);
        this.calculateWaitingTimeData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                calculateWaitingTimeData.removeSource(resultLiveData);
                calculateWaitingTimeData.setValue(result);
                calculateWaitingTimeData.setValue(null);
            }
        });
    }

    /*rate a service after check in, each rating include a score and a comment*/
    public void rateAppointment(String employeeName, Float score, String comment){
        final LiveData<Result> resultLiveData = appointmentRepository.rateAppointment(employeeName, score, comment);
        this.rateAppointmentData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                rateAppointmentData.removeSource(resultLiveData);
                rateAppointmentData.setValue(result);
                rateAppointmentData.setValue(null);
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



    /*====== Validators =====*/
    //length cannot exceed 100 char
    public boolean isCommentWithinRange(String comment){
        if (comment.length() <= 100) return true;
        else return false;
    }

    //rating score should be all digital and value between 0-5
    public boolean isRateValid(String score){
        for (int i = 0; i < score.length(); i++){
            Character letter = score.charAt(i);
            if (! Character.isDigit(letter)) {
                return false;
            }
        }
        return (Float.valueOf(score) >= 0 && Float.valueOf(score) <=5);
    }

    //each elem should not be repeated in a list
    public boolean isValueUnique(ArrayList<String> list){
        for (int i = 0; i < list.size()-1; i++){
            for (int j = i+1; j <list.size(); j++){
                if (list.get(i).equals(list.get(j))){
                    return false;
                }
            }
        }
        return true;
    }

}




