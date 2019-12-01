package com.example.a2019_seg2105_project.ui.clinicApp.login;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Method;

public class UsernameLengthTest {

    //test: Username should not have space.
    @Test
    public void check_UsernameSpace(){
        LoginViewModel loginViewModel = new LoginViewModel(null);
        Class<LoginViewModel> loginViewModelClass = LoginViewModel.class;
        try {
            Method methodIsUsernameLengthValid = loginViewModelClass.getDeclaredMethod("isUsernameLengthValid", String.class);
            methodIsUsernameLengthValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameLengthValid.invoke(loginViewModel, "name space"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: The length of username should not be bigger than 10.
    @Test
    public void check_UsernameInvalidLength(){
        LoginViewModel loginViewModel = new LoginViewModel(null);
        Class<LoginViewModel> loginViewModelClass = LoginViewModel.class;
        try {
            Method methodIsUsernameLengthValid = loginViewModelClass.getDeclaredMethod("isUsernameLengthValid", String.class);
            methodIsUsernameLengthValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameLengthValid.invoke(loginViewModel, "012345678910"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

    //test: Username should not be null.
    @Test
    public void check_UsernameIsNull(){
        LoginViewModel loginViewModel = new LoginViewModel(null);
        Class<LoginViewModel> loginViewModelClass = LoginViewModel.class;
        try {
            Method methodIsUsernameLengthValid = loginViewModelClass.getDeclaredMethod("isUsernameLengthValid", String.class);
            methodIsUsernameLengthValid.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsUsernameLengthValid.invoke(loginViewModel, " "));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

}