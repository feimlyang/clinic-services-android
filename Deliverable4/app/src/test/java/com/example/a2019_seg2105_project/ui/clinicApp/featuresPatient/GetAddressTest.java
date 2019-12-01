package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetAddressTest {
    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getAddress1(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,null,null,"90 Laurier Ave",null,null,null);
        String expected = "90 Laurier Ave";
        assertEquals(appointmentDataModel.getAddress(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getAddress2(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,null,null,"123 ave",null,null,null);
        String expected = "123 ave";
        assertEquals(appointmentDataModel.getAddress(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getAddress3(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,null,null," ",null,null,null);
        String expected = " ";
        assertEquals(appointmentDataModel.getAddress(),expected);

    }
}