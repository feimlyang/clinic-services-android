package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetClinicAddressTest {
    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getClinicAddress1(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null,null,"105 King Edward",null);
        String expected = "105 King Edward";
        assertEquals(clinicDataModel.getClinicAddress(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getClinicAddress2(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null,null,"Address",null);
        String expected = "Address";
        assertEquals(clinicDataModel.getClinicAddress(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getClinicAddress3(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null,null," ",null);
        String expected = " ";
        assertEquals(clinicDataModel.getClinicAddress(),expected);

    }

}