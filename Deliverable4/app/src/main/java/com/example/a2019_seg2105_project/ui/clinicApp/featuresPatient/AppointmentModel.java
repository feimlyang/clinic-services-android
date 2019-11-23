package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

public class AppointmentModel {
    private String id;
    private String dateAndTime;
    private String clinicName;
    private String address;
    private String serviceName;
    private String waitingTime;

    public AppointmentModel() {
    }
    public AppointmentModel(String id, String dateAndTime , String clinicName, String address, String serviceName, String waitingTime) {
        this.id = id;
        this.dateAndTime = dateAndTime;
        this.clinicName = clinicName;
        this.address = address;
        this.serviceName = serviceName;
        this.waitingTime = waitingTime;

    }
    public AppointmentModel(String dateAndTime , String clinicName, String address, String serviceName, String waitingTime) {
        this.dateAndTime = dateAndTime;
        this.clinicName = clinicName;
        this.address = address;
        this.serviceName = serviceName;
        this.waitingTime = waitingTime;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
//    public String getProductName() {
//        return _productname;
//    }
//    public void setPrice(double price) {
//        _price = price;
//    }
//    public double getPrice() {
//        return _price;
//    }
}
