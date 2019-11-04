package com.example.a2019_seg2105_project.data.model;

public class Service {
    public String serviceName;
    public String category;
    public String subcategory;
    public enum rolePerforming { //only can be either doctor, nurse, staff
        DOCTOR,
        NURSE,
        STAFF
    }
}
