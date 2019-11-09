package com.example.a2019_seg2105_project.ui.clinicApp.login;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Method;

public class PasswordLengthTest {

    //test: The length of password should not be bigger than 16.
    @Test
    public void check_PasswordLengthInvalid1(){
        LoginViewModel loginViewModel = new LoginViewModel(null);
        Class<LoginViewModel> loginViewModelClass = LoginViewModel.class;
        try {
            Method methodIsPasswordWithinRange = loginViewModelClass.getDeclaredMethod("isPasswordWithinRange", String.class);
            methodIsPasswordWithinRange.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsPasswordWithinRange.invoke(loginViewModel, "12345678910111213"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: The length of password should not be smaller than 5.
    @Test
    public void check_PasswordLengthInvalid2(){
        LoginViewModel loginViewModel = new LoginViewModel(null);
        Class<LoginViewModel> loginViewModelClass = LoginViewModel.class;
        try {
            Method methodIsPasswordWithinRange = loginViewModelClass.getDeclaredMethod("isPasswordWithinRange", String.class);
            methodIsPasswordWithinRange.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsPasswordWithinRange.invoke(loginViewModel, "1234"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

}