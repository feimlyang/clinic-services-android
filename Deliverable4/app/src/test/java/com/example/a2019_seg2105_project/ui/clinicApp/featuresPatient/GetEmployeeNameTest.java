package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class GetEmployeeNameTest {

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getEmployeeName1(){
        ClinicDataModel clinicDataModel = new ClinicDataModel("employee",null,null,null);
        String expected = "employee";
        assertEquals(clinicDataModel.getEmployeeName(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getEmployeeName2(){
        ClinicDataModel clinicDataModel = new ClinicDataModel("name",null,null,null);
        String expected = "name";
        assertEquals(clinicDataModel.getEmployeeName(),expected);

    }

    //test: By this method, it should get the same value as expected.
    @Test
    public void check_getEmployeeName3(){
        ClinicDataModel clinicDataModel = new ClinicDataModel(" ",null,null,null);
        String expected = " ";
        assertEquals(clinicDataModel.getEmployeeName(),expected);

    }


}