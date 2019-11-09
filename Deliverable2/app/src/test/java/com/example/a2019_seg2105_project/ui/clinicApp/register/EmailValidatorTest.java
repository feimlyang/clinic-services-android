package com.example.a2019_seg2105_project.ui.clinicApp.register;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class EmailValidatorTest {

    //test: Email should not ignore the symbol "@"
    @Test
    public void check_EmailFormat1(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsEmailValid = registerViewModelClass.getDeclaredMethod("isEmailValid", String.class);
            methodIsEmailValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsEmailValid.invoke(registerViewModel, "abc.com"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: Email should not ignore the symbol ".com"
    @Test
    public void check_EmailFormat2(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsEmailValid = registerViewModelClass.getDeclaredMethod("isEmailValid", String.class);
            methodIsEmailValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsEmailValid.invoke(registerViewModel, "abc@abc"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: Email should be null
    @Test
    public void check_EmailFormat3(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsEmailValid = registerViewModelClass.getDeclaredMethod("isEmailValid", String.class);
            methodIsEmailValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsEmailValid.invoke(registerViewModel, " "));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

}
