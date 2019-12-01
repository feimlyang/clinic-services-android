package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetClinicNameTest {

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getClinicName1(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null,"clinic",null,null);
        String expected = "clinic";
        assertEquals(clinicDataModel.getClinicName(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getClinicName2(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null,"name",null,null);
        String expected = "name";
        assertEquals(clinicDataModel.getClinicName(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getClinicName3(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null," ",null,null);
        String expected = " ";
        assertEquals(clinicDataModel.getClinicName(),expected);

    }
}