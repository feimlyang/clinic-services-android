package com.example.a2019_seg2105_project.ui.clinicApp.register;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SamePasswordTest {

    //test: The password re-entered should be the same than previous one.
    @Test
    public void check_SamePassword(){
        RegisterViewModel registerViewModel = new RegisterViewModel(null);
        Class<RegisterViewModel> registerViewModelClass = RegisterViewModel.class;
        try {
            Method methodIsPasswordTheSame = registerViewModelClass.getDeclaredMethod("isPasswordTheSame", String.class, String.class);
            methodIsPasswordTheSame.setAccessible(true);
            boolean shouldFail = (boolean)(methodIsPasswordTheSame.invoke(registerViewModel, "1234", "123456"));
            assertFalse(shouldFail);
        }catch (Exception e)
        {
            assertTrue(false);
        }
    }

}
