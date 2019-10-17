package com.example.a2019_seg2105_project.ui.clinic;

/**
 *
 *      InitActivity is the default activity class of the application.
 *      It:
 *      - Check if there is local user cache.
 *          If no cache exists, display the default layout defined in init_interface.xml
 *          If user has already logged-in, it jump directly to MainActivity.java
 *      - When default layout is displayed, user can either jump to LOGIN or REGISTER activity
 * @see LoginActivity
 * @see RegisterActivity
 * @author Wen Bin Pang
 *  Created : 2019/10/16
 *  Last Modified: 2019/10/16
 */
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

//  ************ Import class Intent
import android.content.Intent;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        // Check if the user has already logged in. If it is the case, jump to
        // the welcome mssage and jump to MainActivity.

        loginButton = findViewById(R.id.init_login);
        registerButton = findViewById(R.id.init_register);

        // 1. Set on click listener for Login button,
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
        // 2. Set on click listener for Register button,
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

    /*
       public boolean checkIfLoggedIn()
       {
       }
     */
    /**
     *  Handle result returned from activity pages called by user click.
     * @param requestCode   Specifies from which button the result is returned.
     * @param resultCode    Specifies either the user cancelled the action or not.
     *                      Cancelled action: RESULT_CANCELED
     *                      Submitted form: RESULT_OK
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_LOGIN_RESULT)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK){
                //1. Make LOGIN button and REGISTER button dissapear gradually
                // Make welcome message dissapear gradually in meantime


                //2. Display Weclome message, then jump to MainActivity.
            }
            else
            {
                //Display a message indicating user has cancelled log-in operation
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Log in has been cancelled",
                        Toast.LENGTH_SHORT );
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        if (requestCode == PICK_REGISTER_RESULT)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK){
                //Display message indicating successful registration
            }
            else
            {
                //Display a message indicating user has cancelled registration
                Toast toast = Toast.makeText(getApplicationContext(),
                                         "Registration has been cancelled",
                                          Toast.LENGTH_SHORT );
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }

    } // end of onCreate()

}// end of Class
