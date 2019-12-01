package com.example.a2019_seg2105_project.ui.clinicApp.register;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Method;

public class RegisteredNameLengthTest {

    //test: Username should not be null when register.
    @Test
    public void check_RegisteredNameIsNull(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> loginViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsUsernameLengthValid = loginViewModelClass.getDeclaredMethod("isUsernameLengthValid", String.class);
            methodIsUsernameLengthValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameLengthValid.invoke(registerViewModel, ""));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: Username should not have space when register.
    @Test
    public void check_RegisteredNameSpace(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> loginViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsUsernameLengthValid = loginViewModelClass.getDeclaredMethod("isUsernameLengthValid", String.class);
            methodIsUsernameLengthValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameLengthValid.invoke(registerViewModel, "registered user"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: The length of username should not be bigger than 10 when register.
    @Test
    public void check_RegisteredNameInvalidLength(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> loginViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsUsernameLengthValid = loginViewModelClass.getDeclaredMethod("isUsernameLengthValid", String.class);
            methodIsUsernameLengthValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameLengthValid.invoke(registerViewModel, "usernamelength"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }
    //test: The length of username can be exactly 10 when register.
    @Test
    public void check_RegisteredNameValid(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> loginViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsPasswordWithinRange = loginViewModelClass.getDeclaredMethod("isPasswordWithinRange", String.class);
            methodIsPasswordWithinRange.setAccessible(true);
            boolean shouldPass = (boolean)(methodIsPasswordWithinRange.invoke(registerViewModel, "username10"));
            assertTrue(shouldPass);
        }catch (Exception e)
        {
            assertFalse(true);
        }
    }


}