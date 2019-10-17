package com.example.a2019_seg2105_project.ui.clinic;

/*
        Created : 2019/10/16
        Last Modified: 2019/10/16

        InitActivity.java
        - default activity page
        - it is linked to init_interface.xml layout.
        - it directs user to log in page or register page
           when the user successfully logged in, it directs to MainActivity.java

 */
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

//  ************ Import class Intent
import android.content.Intent;

import android.view.View;
import android.widget.Button;

import com.example.a2019_seg2105_project.ui.clinic.login.LoginActivity;
import com.example.a2019_seg2105_project.ui.clinic.register.RegisterActivity;
import com.example.a2019_seg2105_project.R;


public class InitActivity extends AppCompatActivity {
    //Fields
    private  Button loginButton;
    private  Button registerButton;
    static final int PICK_LOGIN_RESULT = 1;
    static final int PICK_REGISTER_RESULT = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_interface);
        loginButton = findViewById(R.id.init_login);
        registerButton = findViewById(R.id.init_register);

        // Listenr for Login Button
        // 1. Set Login butter using setOnClickListener
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Run LoginActivity
                Intent intent = new Intent(InitActivity.this, LoginActivity.class);
                startActivityForResult(intent,PICK_LOGIN_RESULT);//wait for result
            }
        });
        // 2. Set Login butter using setOnClickListener
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Run LoginActivity
                Intent intent = new Intent(InitActivity.this, RegisterActivity.class);
                startActivityForResult(intent,PICK_REGISTER_RESULT); //wait for result
            }
        });
    }
    // After receiving result of previous onClick operation
    // indicate what to do next
    // Note: requestCode specifies which button has been clicked
    //       and resultCode has only 2 value:RESULT_OK or RESULT_CANCELED
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_LOGIN_RESULT)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK){
                //1. Make LOGIN button and REGISTER button dissapear gradually
                // Make welcome message dissapear gradually in meantime


                //2. Display Weclome message, jump to MainActivity
            }
        }
        if (requestCode == PICK_REGISTER_RESULT)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK){
                //Display message indicating successful registration
            }
        }


    } // end of onCreate()
}// end of Class
