package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetServiceNameTest {

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getServiceName1(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,null,null,null,"Eye examination",null,null);
        String expected = "Eye examination";
        assertEquals(appointmentDataModel.getServiceName(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getServiceName2(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,null,null,null,"blind",null,null);
        String expected = "blind";
        assertEquals(appointmentDataModel.getServiceName(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getServiceName3(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,null,null,null," ",null,null);
        String expected = " ";
        assertEquals(appointmentDataModel.getServiceName(),expected);

    }
}