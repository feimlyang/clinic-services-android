package com.example.a2019_seg2105_project.ui.clinicApp.login;


import android.os.Bundle;
// UI.
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Observer and ViewModel
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

//  Activity
import android.content.Intent;
import android.app.Activity;

// Basics
import com.example.a2019_seg2105_project.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import com.example.a2019_seg2105_project.helpers.HashHelper;
/**
 * LoginActivity is the class that inspects and responds to change on UI on login page.
 * To inspect changes on UI, it passes UI change to LoginViewModel class, whereas the latter
 * one inform if the information provided is valid.
 *
 * @see LoginViewModel
 */

public class LoginActivity extends AppCompatActivity {
    // Inspect UI
    private LoginViewModel loginViewModel;
    // UI fields
    private EditText userName;
    private EditText userPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Initialize : load associated XML layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize: set loginViewModel
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        // Initialize: associte UI
        userName = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button cancelButton = findViewById(R.id.cancelLogin);

        // Observe changes made to text fields.
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                // Login button will be disabled until inputs from both fields are valid.
                loginButton.setEnabled(loginFormState.isDataValid());

                if (loginFormState.getUsernameError() != null) {
                    userName.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    userPassword.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        // Obtain login result after attempt.
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    setResult(Activity.RESULT_OK, getIntent());
                    //successfully logged in, save username in globalObj helper
                    GlobalObjectManager saveUsername = GlobalObjectManager.getInstance();
                    saveUsername.setCurrentUsername(new String(userName.getText().toString()));
                    // back to Init Activity
                    finish();
                }
            }
        });


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
                loginViewModel.loginDataChanged(userName.getText().toString(),
                        userPassword.getText().toString());
            }
        };
        userName.addTextChangedListener(afterTextChangedListener);
        userPassword.addTextChangedListener(afterTextChangedListener);

/*
        OnClick listener of LOG IN button.
        If log in is SUCCESSFUL, it redirect user to the InitActivity page.
        User will be greeted with a 'welcome' message.

        If log in is UNSUCCESSFUL,it will remain at this page and ask user to try agian.
 */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hashedPassword = HashHelper.hash(userPassword.getText().toString());
                if(null == hashedPassword)
                    hashedPassword = userPassword.getText().toString();
                loginViewModel.login(userName.getText().toString().toLowerCase(), hashedPassword);
            }
        });
/*
        OnClick listener of CANCEL button
        It redirect user back to the InitActivity page without doing anything.
 */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent); // Set the result that will be returned to caller
                finish(); // end current activity
            }
        });
    }// end of OnCreate()

    /**
     *  Sucessful logged-in.
     * @param model
     */
    private void updateUiWithUser(LoggedInUserView model) {
        // pass loginUser back to Init Activity by Intent
        getIntent().putExtra(getString(R.string.logged_in_user), model);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
