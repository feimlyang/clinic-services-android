package com.example.a2019_seg2105_project.ui.clinic.register;


        import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;

        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioGroup;

//  ************ Import class Intent
        import android.content.Intent;
        import com.example.a2019_seg2105_project.R;

public class RegisterActivity extends AppCompatActivity {
    //                          Fields
    //Set all user information to be private for data protection.
    private EditText user_firstName;
    private EditText user_lastName;
    private EditText user_email;
    private EditText user_password;
    private EditText user_age;
    private RadioGroup user_accountType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // load activity_register.xml

        // Link fields to corresponding XML layout
        user_firstName = findViewById(R.id.fName);
        user_lastName = findViewById(R.id.lName);
        user_email = findViewById(R.id.emailID);
        user_password = findViewById(R.id.password);
        user_age = findViewById(R.id.age);

        // Get buttons on XML layout
        final Button registerButton = findViewById(R.id.register);
        final Button cancelButton = findViewById(R.id.cancelRegister);
        // Inspect if radio button 'employee' has been selected.
        // Prompt text area for user to enter employee number if selected.
        user_accountType  = (RadioGroup) findViewById(R.id.radioAccountType);

   /*
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
*/

   /*
        Radio Group listener of user account type.
        If the type 'employee' has been selected, prompt text area for user to enter
        associated employee number.
        Otherwise, make the text area invisible.
  */

   /*user_accountType.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener ()
    // Associate an onChecked listner to user_accountType radiogroup
       {
           public void accountTypeCheckedChange(RadioGroup group, int checkedId)
           {
                if(checkedId ==R.id.btnEmployee ) // when Employee has been checked
                {

                }
                else // otherwise
                {

                }
           }
       });*/
    /*
        OnClick listener of CANCEL button
        It redirect user to the InitActivity page
        with a 'successfully registered' message
    */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Extra information that will be provided

                setResult(RESULT_OK,intent);// Set the result that will be returned to caller
                finish();  // end current activity
            }
        });
/*
        OnClick listener of CANCEL button
        It redirect user to the InitActivity page
 */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent); // Set the result that will be returned to caller
                finish(); // end current activity
            }
        });
   /* public static class RegisterViewModel extends ViewModel {

        private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
        private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
        private LoginRepository loginRepository;

        LoginViewModel(LoginRepository loginRepository) {
            this.loginRepository = loginRepository;
        }

        LiveData<LoginFormState> getLoginFormState() {
            return loginFormState;
        }

        LiveData<LoginResult> getLoginResult() {
            return loginResult;
        }

        public void login(String username, String password) {
            // can be launched in a separate asynchronous job
            Result<LoggedInUser> result = loginRepository.login(username, password);

            if (result instanceof Result.Success) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            } else {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        }

        public void loginDataChanged(String username, String password) {
            if (!isUserNameValid(username)) {
                loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
            } else if (!isPasswordValid(password)) {
                loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
            } else {
                loginFormState.setValue(new LoginFormState(true));
            }
        }
*/
    } // end of onCreate()
}// end of RegisterActivity