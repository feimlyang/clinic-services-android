package com.example.a2019_seg2105_project.data.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Clinic {
    // key
    public String employeeUsername;

    public String clinicName;
    public String clinicAddress;
    public String clinicPhoneNum;
    public ArrayList<String> insuranceType;
    public ArrayList<String> paymentMethod;
    public ArrayList<String> workingHours;
    public ArrayList<String> servicesInProfile;

    public Clinic(final String clinicName, final String clinicAddress, final String clinicPhoneNum,
                  final ArrayList<String> insuranceType, final ArrayList<String> paymentMethod, final ArrayList<String> workingHours,
                  final ArrayList<String> ServicesInProfile){

        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.clinicPhoneNum = clinicPhoneNum;
        this.insuranceType = insuranceType;
        this.paymentMethod = paymentMethod;
        this.workingHours = workingHours;
        this.servicesInProfile = servicesInProfile;

    }
}
