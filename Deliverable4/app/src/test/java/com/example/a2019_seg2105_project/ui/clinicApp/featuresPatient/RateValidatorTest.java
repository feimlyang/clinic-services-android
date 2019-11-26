package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class RateValidatorTest {

    //test: The rate should not be character.
    @Test
    public void check_RateInvalid1(){
        AppointmentViewModel appointmentViewModel = new AppointmentViewModel(null);
        Class<AppointmentViewModel> appointmentViewModelClass = AppointmentViewModel.class;
        try {
            Method methodIsRateValid = appointmentViewModelClass.getDeclaredMethod("isRateValid", String.class);
            methodIsRateValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsRateValid.invoke(appointmentViewModel, "a"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: The rate should be digital, but the value of rate should not be over 5.
    @Test
    public void check_RateInvalid2(){
        AppointmentViewModel appointmentViewModel = new AppointmentViewModel(null);
        Class<AppointmentViewModel> appointmentViewModelClass = AppointmentViewModel.class;
        try {
            Method methodIsRateValid = appointmentViewModelClass.getDeclaredMethod("isRateValid", String.class);
            methodIsRateValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsRateValid.invoke(appointmentViewModel, "6"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: The value of rate should be between 0 and 5.
    @Test
    public void check_RateValid(){
        AppointmentViewModel appointmentViewModel = new AppointmentViewModel(null);
        Class<AppointmentViewModel> appointmentViewModelClass = AppointmentViewModel.class;
        try {
            Method methodIsRateValid = appointmentViewModelClass.getDeclaredMethod("isRateValid", String.class);
            methodIsRateValid.setAccessible(true);
            boolean shouldPass = (boolean)(methodIsRateValid.invoke(appointmentViewModel, "3"));
            assertTrue(shouldPass);
        }catch (Exception e)
        {
            assertFalse(false);
        }
    }

}