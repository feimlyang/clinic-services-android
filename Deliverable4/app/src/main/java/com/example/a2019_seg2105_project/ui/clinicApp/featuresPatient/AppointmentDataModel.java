package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

public class AppointmentDataModel {
    String dateAndHours;
    String clinicName;
    String address;
    String serviceName;
    String waitingTime;


    public AppointmentDataModel(String dateAndHours, String clinicName, String address, String serviceName,String waitingTime){
        this.dateAndHours = dateAndHours;
        this.clinicName = clinicName;
        this.address = address;
        this.serviceName = serviceName;
        this.waitingTime = waitingTime;
    }

    public String getDateAndHours(){
        return dateAndHours;
    }

    public String getClinicName(){
        return clinicName;
    }

    public String getAddress(){
        return address;
    }

    public String getServiceName(){
        return serviceName;
    }

    public String getWaitingTime(){
        return waitingTime;
    }

}
