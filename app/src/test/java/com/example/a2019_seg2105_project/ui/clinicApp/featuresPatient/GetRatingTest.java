package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetRatingTest {
    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getRating1(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null,null,null,"5.0");
        String expected = "5.0";
        assertEquals(clinicDataModel.getRating(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getRating2(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null,null,null,"2.5");
        String expected = "2.5";
        assertEquals(clinicDataModel.getRating(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getRating3(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(null,null,null," ");
        String expected = " ";
        assertEquals(clinicDataModel.getRating(),expected);

    }

}