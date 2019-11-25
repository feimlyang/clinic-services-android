package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

public class ClinicDataModel {
    String employeeName;
    String clinicName;
    String clinicAddress;
    String clinicRate;

    public ClinicDataModel(String employeeName, String clinicName, String clinicAddress, String clinicRate){
        this.employeeName = employeeName;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.clinicRate = clinicRate;
    }

    public String getEmployeeName(){
        return employeeName;
    }

    public String getClinicName(){
        return clinicName;
    }

    public String getClinicAddress(){
        return clinicAddress;
    }

    public String getRating(){
        return clinicRate;
    }
}
