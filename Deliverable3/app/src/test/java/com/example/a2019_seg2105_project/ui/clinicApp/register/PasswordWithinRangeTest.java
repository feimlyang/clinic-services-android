package com.example.a2019_seg2105_project.ui.clinicApp.register;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Method;

public class PasswordWithinRangeTest {

    //test: The length of password should not be bigger than 16.
    @Test
    public void check_PasswordWithinRange1(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> loginViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsPasswordWithinRange = loginViewModelClass.getDeclaredMethod("isPasswordWithinRange", String.class);
            methodIsPasswordWithinRange.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsPasswordWithinRange.invoke(registerViewModel, "passwordwithinrange"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: The length of password should not be smaller than 5.
    @Test
    public void check_PasswordWithinRange2(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> loginViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsPasswordWithinRange = loginViewModelClass.getDeclaredMethod("isPasswordWithinRange", String.class);
            methodIsPasswordWithinRange.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsPasswordWithinRange.invoke(registerViewModel, "pass"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: The length of password can be exactly 5.
    @Test
    public void check_PasswordWithinRange3(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> loginViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsPasswordWithinRange = loginViewModelClass.getDeclaredMethod("isPasswordWithinRange", String.class);
            methodIsPasswordWithinRange.setAccessible(true);
            boolean shouldPass = (boolean)(methodIsPasswordWithinRange.invoke(registerViewModel, "12345"));
            assertTrue(shouldPass);
        }catch (Exception e)
        {
            assertFalse(true);
        }
    }

    //test: The length of password can be exactly 16.
    @Test
    public void check_PasswordWithinRange4(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> loginViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsPasswordWithinRange = loginViewModelClass.getDeclaredMethod("isPasswordWithinRange", String.class);
            methodIsPasswordWithinRange.setAccessible(true);
            boolean shouldPass = (boolean)(methodIsPasswordWithinRange.invoke(registerViewModel, "1234567890123456"));
            assertTrue(shouldPass);
        }catch (Exception e)
        {
            assertFalse(true);
        }
    }

}