package com.example.a2019_seg2105_project.ui.clinicApp.register;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class UsernameFormatTest {

    //test: Username should not have symbol such as "."
    @Test
    public void check_UsernameFormat1(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsUsernameFormatValid = registerViewModelClass.getDeclaredMethod("isUsernameFormatValid", String.class);
            methodIsUsernameFormatValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameFormatValid.invoke(registerViewModel, "username."));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: Username should not have symbol such as "!"
    @Test
    public void check_UsernameFormat2(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsUsernameFormatValid = registerViewModelClass.getDeclaredMethod("isUsernameFormatValid", String.class);
            methodIsUsernameFormatValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameFormatValid.invoke(registerViewModel, "username!"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: Username should not have symbol such as "#"
    @Test
    public void check_UsernameFormat3(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsUsernameFormatValid = registerViewModelClass.getDeclaredMethod("isUsernameFormatValid", String.class);
            methodIsUsernameFormatValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameFormatValid.invoke(registerViewModel, "username#"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: Username should not have symbol such as "@"
    @Test
    public void check_UsernameFormat4(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsUsernameFormatValid = registerViewModelClass.getDeclaredMethod("isUsernameFormatValid", String.class);
            methodIsUsernameFormatValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameFormatValid.invoke(registerViewModel, "username@"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: Username should not have symbol such as "()"
    @Test
    public void check_UsernameFormat5(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsUsernameFormatValid = registerViewModelClass.getDeclaredMethod("isUsernameFormatValid", String.class);
            methodIsUsernameFormatValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameFormatValid.invoke(registerViewModel, "username()"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }



}