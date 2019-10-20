package com.example.a2019_seg2105_project.ui.clinic.register;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// UI.
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

// Observer
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

//  Intent
import android.content.Intent;

//  Basics
import com.example.a2019_seg2105_project.R;
import androidx.annotation.Nullable;
import com.example.a2019_seg2105_project.data.LoginRepository;
/**
 * RegisterActivity is the class that inspects and responds to change on UI of register page.
 * To inspect changes on UI, it
 *
 *
 *
 * @see RegisterViewModel
 */
public class RegisterActivity extends AppCompatActivity {
    //                          Fields
    // Inspect UI
    private RegisterViewModel registerViewModel;

    // UI fields
    private EditText user_firstName;
    private EditText user_lastName;
    private EditText user_email;
    private EditText user_password;
    private EditText user_password_verify;
    private EditText unregistered_username;
    // This field will only appear if user select 'Employee' radio button
    private EditText user_employeeAccessCode;
    private RadioGroup user_accountType;
    // Indicate if the user can regisetr as an employee
    private boolean user_hasEmployeeAccess; // true = user selected employee type and
                                            // entered right access code

    @Override

    public void onCreate(Bundle savedInstanceState) {

        // Initialize : load associated XML layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize: set loginViewModel
        registerViewModel = ViewModelProviders.of(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);
        // Link fields to corresponding XML layout
        user_firstName = findViewById(R.id.fName);
        user_lastName = findViewById(R.id.lName);
        unregistered_username = findViewById(R.id.register_userName);
        user_email = findViewById(R.id.emailID );
        user_password = findViewById(R.id.register_password);
        user_password_verify = findViewById(R.id.register_validatePassword);
        user_employeeAccessCode = findViewById(R.id.employeeValidation );
        user_hasEmployeeAccess = false;    // By default,user has NO access to register as employee
        // Get buttons on XML layout
        final Button registerButton = findViewById(R.id.register);
        final Button cancelButton = findViewById(R.id.cancelRegister);

        // Inspect if radio button 'employee' has been selected.
        // Prompt text area for user to enter employee number if selected.
        user_accountType  = findViewById(R.id.radioAccountType);
    /*  ================================================================================
        1. Inspect changes made to text fields. Get error message if not validated.
     ================================================================================ */
        registerViewModel.getRegisterFormState().observe(this, new Observer <RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }

                //1. If password field is not empty, enable password verification.
                if( !user_password.getText().toString().equals("")) {
                    if(!user_password_verify.isEnabled())
                        user_password_verify.setEnabled(true);
                }
                else {
                    if (user_password_verify.isEnabled())
                        user_password_verify.setEnabled(false);
                }

                // 2. If all input has valid format, display register button.
                //     a)Check if all UI inputs are valid.
                if (registerFormState.isDataValid()) {
                    // b) If user did not select employee type , simply enable.
                    if (! user_employeeAccessCode.isShown()) {
                        registerButton.setEnabled(true);
                    }
                    // c) If employee access code field is shown, check if access code is correct.
                    else{
                        // Enable if access code is correct.
                        if(user_hasEmployeeAccess)
                            registerButton.setEnabled(true);
                        else
                            registerButton.setEnabled(false);
                    }
                }
                else
                {
                            registerButton.setEnabled(false);
                }

                //3.    Display error/validated messages otherwise
                // a) Empty input error message
                if(unregistered_username.getText().toString().equals(""))
                    unregistered_username.setError(getString(R.string.empty_username));
                if(user_firstName.getText().toString().equals(""))
                    user_firstName.setError(getString(R.string.empty_firstName));
                if(user_lastName.getText().toString().equals(""))
                    user_lastName.setError(getString(R.string.empty_lastName));
                if(user_email.getText().toString().equals(""))
                   user_email.setError(getString(R.string.empty_email));
                if(user_password.getText().toString().equals(""))
                    user_password.setError(getString(R.string.empty_password));
                // When access code field is displayed ,require access code
                if(user_employeeAccessCode.isShown()){
                    if(user_employeeAccessCode.getText().toString().equals(""))
                        user_employeeAccessCode.setError(getString(R.string.empty_employee_access_code));
                }

                // b) Standard error message
                if (registerFormState.getUsernameError() != null) {
                    unregistered_username.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getFirstNameError() != null) {
                    user_firstName.setError(getString(registerFormState.getFirstNameError()));
                }
                if (registerFormState.getLastNameError() != null) {
                    user_lastName.setError(getString(registerFormState.getLastNameError()));
                }
                if (registerFormState.getEmailError() != null) {
                    user_email.setError(getString(registerFormState.getEmailError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    user_password.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getPasswordVerificationError() != null) {
                    user_password_verify.setError(getString(registerFormState.getPasswordVerificationError()));
                }
                // If employee access code is invalid, display error message
               if(!user_hasEmployeeAccess){

                    user_employeeAccessCode.setError("Access Code is Invalid.");
                    registerButton.setEnabled(false);
               }
               else
               {
                   user_hasEmployeeAccess = true;
               }
            }
        });

      /*  ================================================================================
        2. Set EditText listeners
     ================================================================================ */
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 1. Check the access code field if text field is displayed.
                if (user_employeeAccessCode.isShown()) {
                    //Check if user has right access code,
                    if(user_employeeAccessCode.getText().toString().equals("1207049"))
                    {
                        user_hasEmployeeAccess = true;
                    }
                    else
                    {
                        user_hasEmployeeAccess = false;
                    }
                }
                // 2. Inspect user general information fields.
                registerViewModel.registerDataChanged(
                        unregistered_username.getText().toString(),
                        user_password.getText().toString(),
                        user_firstName.getText().toString(), user_lastName.getText().toString(),
                        user_email.getText().toString(), user_password_verify.getText().toString());
            }
        };
        user_employeeAccessCode.addTextChangedListener(afterTextChangedListener);
    /*  ================================================================================
        3. Inspect RadioGroup activity: if 'Employee' is selected, display a text area
            for user to enter Employee ID.
            Otherwise, make the Employee ID area invisible.
        <Note>   To simplify the task / the maintenance of database, we use the number
                    "1207049" as a validation key for employee registration.
                    Any user attempting to register as Employee should enter this number,
                    otherwise registration will be rejected.

     ================================================================================ */
        user_accountType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.btnEmployee){
                    user_employeeAccessCode.setVisibility(View.VISIBLE);
                    //add listener
                }
                else
                {
                    user_employeeAccessCode.setVisibility(View.INVISIBLE);
                }
            }
        });

       unregistered_username.addTextChangedListener(afterTextChangedListener);
       user_password.addTextChangedListener(afterTextChangedListener);
       user_firstName.addTextChangedListener(afterTextChangedListener);
       user_lastName.addTextChangedListener(afterTextChangedListener);
       user_email.addTextChangedListener(afterTextChangedListener);
       user_password_verify.addTextChangedListener(afterTextChangedListener);
       // Add listener to employee access code field, but will only start listen
       // when field is shown.
       user_employeeAccessCode.addTextChangedListener(afterTextChangedListener);

       // Process the login result in callback onChanged.
       registerViewModel.getRegisterResultLiveData().observe(this, new Observer<RegisterResult>() {
           @Override
           public void onChanged(RegisterResult registerResult) {
               // set null usually for cleaning up status
               if(null == registerResult)
                   return;
               // successful registration
               if(registerResult.getReturnCode().equals(R.string.register_succeeds))
               {
                   Intent intent = new Intent();
                   setResult(RESULT_OK,intent);// Set the result that will be returned to caller
                   finish();  // end current activity
               }
               else
               {
                   showRegisterFailed(registerResult);
               }
           }
       });
    /* ================================================================================
        4. OnClick listener of REGISTER button
        It 1)Call register method in RegisterViewModel to register info to database
           2) Redirect user to the InitActivity page if registration was successful.
           3) Notify user if registration has failed.
    ================================================================================ */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // always log out any logged-in account when registering a new one
                LoginRepository.getInstance().logout();
                // On clicking the register button, call register method
                // Note: if user has employee access, he/she is employee
                // since this program disable register button if access code is incorrect.
                registerViewModel.register(
                                           unregistered_username.getText().toString(),
                                           user_password.getText().toString(),
                                           user_firstName.getText().toString(),
                                           user_lastName.getText().toString(),
                                           user_email.getText().toString(),
                                           user_hasEmployeeAccess);

            }
        });
    /* ================================================================================
            OnClick listener of CANCEL button
            Redirects user to the InitActivity page
     ================================================================================ */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent); // Set the result that will be returned to caller
                finish(); // end current activity
            }
        });

    } // end of onCreate()

    private void showRegisterFailed(RegisterResult result) {
        String message = getString(result.getReturnCode());
        if(result.getReturnCode().equals(R.string.register_error_existingAccount))
        {
            message = "Registration failed: Username already exists! Please try a new one.";
        }
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }
}// end of RegisterActivity