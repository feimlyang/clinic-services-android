package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckIsCheckedInTest {

    //test: By this method, it should get the same result as expected.
    @Test
    public void check_isCheckedIn1(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,null,null,null,null,null,null);
        appointmentDataModel.setIsCheckedIn(false);
        assertEquals(appointmentDataModel.getIsCheckedIn(),false);

    }

    //test: By this method, it should get the same result as expected.
    @Test
    public void check_isCheckedIn2(){
        AppointmentDataModel appointmentDataModel = new AppointmentDataModel(null,null,null,null,null,null,null);
        appointmentDataModel.setIsCheckedIn(true);
        assertEquals(appointmentDataModel.getIsCheckedIn(),true);

    }


}