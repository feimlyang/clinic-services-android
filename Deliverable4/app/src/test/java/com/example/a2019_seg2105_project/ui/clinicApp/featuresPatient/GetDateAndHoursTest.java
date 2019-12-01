package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetDateAndHoursTest {
    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getDateAndHours1(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,"20191225 08:00-09:00",null,null,null,null,null);
        String expected = "20191225 08:00-09:00";
        assertEquals(appointmentDataModel.getDateAndHours(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getDateAndHours2(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,"20200102 10:00-11:00",null,null,null,null,null);
        String expected = "20200102 10:00-11:00";
        assertEquals(appointmentDataModel.getDateAndHours(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getDateAndHours3(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null," ",null,null,null,null,null);
        String expected = " ";
        assertEquals(appointmentDataModel.getDateAndHours(),expected);

    }
}