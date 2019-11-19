package com.example.a2019_seg2105_project.ui.clinicApp;

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
 *  Last Modified: 2019/11/7
 */
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//  ************ Import class Intent
import android.content.Intent;
import java.io.Serializable;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee.EmployeeMainFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.login.LoggedInUserView;
import com.example.a2019_seg2105_project.ui.clinicApp.login.LoginActivity;
import com.example.a2019_seg2105_project.ui.clinicApp.register.RegisterActivity;
import com.example.a2019_seg2105_project.R;


public class InitActivity extends AppCompatActivity {
    //Fields
    private TextView welcomeMessage; //Store initial welcoming message.
    private  Button loginButton;
    private  Button registerButton;
    static final int PICK_LOGIN_RESULT = 1;
    static final int PICK_REGISTER_RESULT = 2;
    static final int DELAY_THEN_JUMP = 2000; // Time Delay after logged-in, then jump to MainActivity.
                                             // Initially set to 2 second (2000 milliseconds)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user has already logged in. If it is the case, jump to
        // the welcome mssage and jump to MainActivity.
        if(false)    //TODO: check local cache to know if the user has already logged in.
        {
            //Jump to MainActivity, and use the latter one as Starting page
            Intent intent = new Intent(InitActivity.this, MainActivity.class);
            startActivity(intent);
        }

        // If user has not logged in, display default initpage.
        setContentView(R.layout.init_interface);
        loginButton = findViewById(R.id.init_login);
        registerButton = findViewById(R.id.init_register);
        welcomeMessage = findViewById(R.id.init_welcomeMessage);

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
        super.onActivityResult(requestCode, resultCode, data);
        // 1. Result from Login Activity
        if (requestCode == PICK_LOGIN_RESULT)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //1. Make original interface dissapear gradually
                loginButton.setVisibility(View.GONE); //set Gone to free-up space
                registerButton.setVisibility(View.GONE);
                welcomeMessage.setVisibility(View.INVISIBLE);
                //2. Display Weclome message, then jump to MainActivity.
                String userName = " "; //
                String accountType = ""; //
                // Show the logged-in user's information
                final Serializable userSerialization = data.getSerializableExtra(getString(R.string.logged_in_user));
                if (null != userSerialization) {
                    LoggedInUserView user = (LoggedInUserView) userSerialization;
                    userName = user.getDisplayName();
                    accountType = user.getRole();
                }
                welcomeMessage.setText("Welcome " + userName + " ! You are logged in as " + accountType + "." +
                        " The system will jump Main page in 2 seconds...");
                welcomeMessage.setVisibility(View.VISIBLE);

                // Jump to Main Page after few seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(InitActivity.this, MainActivity.class);
                        //Pass user information to Main
                        //TODO: pass user information to Main
                        LoggedInUserView user = (LoggedInUserView)userSerialization;
                        //Pass information about username/usertype to the intent to be jumped
                        intent.putExtra(getString(R.string.loggedIn_userName),user.getDisplayName());
                        intent.putExtra(getString(R.string.loggedIn_userType),user.getRole());
                        startActivity(intent);
                    }
                },DELAY_THEN_JUMP);
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
        //2. Result from Register Activity
        if (requestCode == PICK_REGISTER_RESULT)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK){
                //Display message indicating successful registration
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Registration was successful ! Please log in.",
                        Toast.LENGTH_SHORT );
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
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
    }
}
