package com.example.a2019_seg2105_project.ui.clinicApp.login;

import static org.junit.Assert.*;
import org.junit.Test;

public class LoginFormStateTest {
    LoginFormState logIn1 = new LoginFormState(10,11);
    LoginFormState logIn2 = new LoginFormState(12,18);

    @Test
    public void check_UsernameLength1(){
        assertTrue("check the length of username", 10 >= logIn1.getUsernameError() );
        System.out.println("UserNameLength1 --> Pass");
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