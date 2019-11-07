package com.example.a2019_seg2105_project.ui.clinicApp.login;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Method;

public class LoginViewModelTest {
    LoginFormState logIn1 = new LoginFormState(10,11);
    LoginFormState logIn2 = new LoginFormState(12,18);

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
    @Test
    public void check_UsernameLength2(){
        assertFalse("check the length of username", 10 >= logIn2.getUsernameError() );
        System.out.println("UserNameLength2 --> Fail");
    }

    @Test
    public void check_PasswordLength1(){
        assertTrue("check the length of password", 5 <= logIn1.getPasswordError() && 16 >= logIn1.getPasswordError());
        System.out.println("PasswordLength1 --> Pass");
    }

    @Test
    public void check_PasswordLength2() {
        assertFalse("check the length of password", 5 <= logIn2.getPasswordError() && 16 >= logIn2.getPasswordError());
        System.out.println("PasswordLength2 --> Fail");
    }

}