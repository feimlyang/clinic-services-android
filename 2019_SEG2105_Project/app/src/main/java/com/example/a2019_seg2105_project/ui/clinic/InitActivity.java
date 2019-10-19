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
import android.widget.TextView;

import com.example.a2019_seg2105_project.ui.clinic.login.LoginActivity;
import com.example.a2019_seg2105_project.ui.clinic.register.RegisterActivity;
import com.example.a2019_seg2105_project.R;

import org.w3c.dom.Text;


public class InitActivity extends AppCompatActivity {
    //Fields
    private TextView welcomeMessage; //Store initial welcoming message.
    private  Button loginButton;
    private  Button registerButton;
    static final int PICK_LOGIN_RESULT = 1;
    static final int PICK_REGISTER_RESULT = 2;


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
        // 1. Result from Login Activity
        if (requestCode == PICK_LOGIN_RESULT)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK){
                //1. Make original interface dissapear gradually
                loginButton.setVisibility(View.GONE); //set Gone to free-up space
                registerButton.setVisibility(View.GONE);
                welcomeMessage.setVisibility(View.INVISIBLE);
                //2. Display Weclome message, then jump to MainActivity.
                String userName = " "; // TODO: Get User Name
                String accountType = "DEFAULT"; // TODO: get Account type
                welcomeMessage.setText("Welcome, " + userName + " ! You are logged in as "+ accountType +" .");
                welcomeMessage.setVisibility(View.VISIBLE);
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

    } // end of onCreate()

}// end of Class
