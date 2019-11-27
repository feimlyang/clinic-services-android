package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

public class AppointmentDataModel {
    String dateAndHours;
    String clinicName;
    String address;
    String serviceName;
    String waitingTime;
    Boolean isCheckedIn;
    String employeeName;


    public AppointmentDataModel(String employeeName ,String dateAndHours, String clinicName, String address, String serviceName,String waitingTime,
                                Boolean isCheckedIn){
        this.employeeName = employeeName;
        this.dateAndHours = dateAndHours;
        this.clinicName = clinicName;
        this.address = address;
        this.serviceName = serviceName;
        this.waitingTime = waitingTime;
        this.isCheckedIn = isCheckedIn;

    }
//    public AppointmentDataModel(String dateAndHours, String clinicName, String address, String serviceName,String waitingTime,
//                                Boolean isCheckedIn){
//        this.dateAndHours = dateAndHours;
//        this.clinicName = clinicName;
//        this.address = address;
//        this.serviceName = serviceName;
//        this.waitingTime = waitingTime;
//        this.isCheckedIn = isCheckedIn;
//
//    }

    public String getEmployeeName(){
        return employeeName;
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

    public void setIsCheckedIn(Boolean isChecked){
        this.isCheckedIn = isChecked;
    }

    public Boolean getIsCheckedIn(){
        return  isCheckedIn;
    }

}
