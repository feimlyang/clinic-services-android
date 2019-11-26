package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class CommentLengthTest {

    //test: The length of comment should not be bigger than 100.
    @Test
    public void check_CommentLengthInvalid(){
        AppointmentViewModel appointmentViewModel = new AppointmentViewModel(null);
        Class<AppointmentViewModel> appointmentViewModelClass = AppointmentViewModel.class;
        try {
            Method methodIsCommentWithinRange = appointmentViewModelClass.getDeclaredMethod("isCommentWithinRange", String.class);
            methodIsCommentWithinRange.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsCommentWithinRange.invoke(appointmentViewModel, "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: The length of comment can be exactly 100.
    @Test
    public void check_CommentLengthValid(){
        AppointmentViewModel appointmentViewModel = new AppointmentViewModel(null);
        Class<AppointmentViewModel> appointmentViewModelClass = AppointmentViewModel.class;
        try {
            Method methodIsCommentWithinRange = appointmentViewModelClass.getDeclaredMethod("isCommentWithinRange", String.class);
            methodIsCommentWithinRange.setAccessible(true);
            boolean shouldPass = (boolean)(methodIsCommentWithinRange.invoke(appointmentViewModel, "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"));
            assertTrue(shouldPass);
        }catch (Exception e)
        {
            assertFalse(false);
        }
    }
}