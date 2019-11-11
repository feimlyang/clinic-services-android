package com.example.a2019_seg2105_project.ui.clinicApp.register;


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
import android.widget.TextView;
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
 * RegisterActivity is the class that inspects and responds to change on UI on registration page.
 * To inspect changes on UI, it passes UI change to RegisterViewModel class, whereas the latter
 * one inform if the information provided is valid.
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
    private TextView registration_code_banner;
    private RadioGroup user_accountType;
    // Indicate if the user can regisetr as an employee
    private boolean user_hasEmployeeAccess; // true = user selected employee type and
                                            // entered right access code
    private RegisterFormState.AccountType accountTypeChosen;

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
        registration_code_banner = findViewById(R.id.registrationCodeBanner);
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
                if (registerFormState.getUsernameError() != null) {
                    unregistered_username.setError(getString(registerFormState.getUsernameError()));
                    registerButton.setEnabled(false);
                    return;
                }
                if (registerFormState.getFirstNameError() != null) {
                    user_firstName.setError(getString(registerFormState.getFirstNameError()));
                    registerButton.setEnabled(false);
                    return;
                }
                if (registerFormState.getLastNameError() != null) {
                    user_lastName.setError(getString(registerFormState.getLastNameError()));
                    registerButton.setEnabled(false);
                    return;
                }
                if (registerFormState.getEmailError() != null) {
                    user_email.setError(getString(registerFormState.getEmailError()));
                    registerButton.setEnabled(false);
                    return;
                }
                if (registerFormState.getPasswordError() != null) {
                    user_password.setError(getString(registerFormState.getPasswordError()));
                    registerButton.setEnabled(false);
                    user_password_verify.setEnabled(false);
                    return;
                }
                user_password_verify.setEnabled(true);
                if (registerFormState.getPasswordVerificationError() != null) {
                    user_password_verify.setError(getString(registerFormState.getPasswordVerificationError()));
                    registerButton.setEnabled(false);
                    return;
                }
                if(accountTypeChosen == RegisterFormState.AccountType.INVALID)
                {
                    registerButton.setEnabled(false);
                    return;
                }

                if(accountTypeChosen == RegisterFormState.AccountType.PATIENT )
                {
                    user_employeeAccessCode.setVisibility(View.INVISIBLE);
                    user_employeeAccessCode.setEnabled(false);
                    registration_code_banner.setVisibility(View.INVISIBLE);
                    registration_code_banner.setEnabled(false);
                    user_employeeAccessCode.setEnabled(false);
                    user_hasEmployeeAccess = false;
                    registerButton.setEnabled(true);
                }
                else if(accountTypeChosen == RegisterFormState.AccountType.EMPLOYEE)
                {
                    user_employeeAccessCode.setVisibility(View.VISIBLE);
                    user_employeeAccessCode.setEnabled(true);
                    registration_code_banner.setVisibility(View.VISIBLE);
                    registration_code_banner.setEnabled(true);
                }
                if (registerFormState.getEmployAccessCodeError() != null && user_employeeAccessCode.isShown())
                {
                    user_employeeAccessCode.setError("Access Code is Invalid.");
                    registerButton.setEnabled(false);
                    return;
                }
                else if(user_employeeAccessCode.isShown())
                {
                    user_hasEmployeeAccess = true;
                }
                registerButton.setEnabled(true);
                return;
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
                registerViewModel.registerDataChanged(
                        unregistered_username.getText().toString(),
                        user_password.getText().toString(),
                        user_firstName.getText().toString(), user_lastName.getText().toString(),
                        user_email.getText().toString(), user_password_verify.getText().toString(),
                        accountTypeChosen, user_employeeAccessCode.getText().toString());
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
                RegisterFormState.AccountType accountType = RegisterFormState.AccountType.INVALID;
                if(checkedId == R.id.btnEmployee){
                    accountType = RegisterFormState.AccountType.EMPLOYEE;
                    accountTypeChosen = RegisterFormState.AccountType.EMPLOYEE;
                }
                else
                {
                    accountType = RegisterFormState.AccountType.PATIENT;
                    accountTypeChosen = RegisterFormState.AccountType.PATIENT;
                }
                // Trigger a registermodel event.
                registerViewModel.registerDataChanged(
                        unregistered_username.getText().toString(),
                        user_password.getText().toString(),
                        user_firstName.getText().toString(), user_lastName.getText().toString(),
                        user_email.getText().toString(), user_password_verify.getText().toString(),
                        accountType, user_employeeAccessCode.getText().toString());
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
                                           unregistered_username.getText().toString().toLowerCase(),
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