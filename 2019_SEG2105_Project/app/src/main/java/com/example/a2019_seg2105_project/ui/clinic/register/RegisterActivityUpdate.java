package com.example.a2019_seg2105_project.ui.clinic.register;



import android.os.Bundle;

import java.security.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//  ************ Import class Intent
import android.content.Intent;
import android.widget.Toast;

import com.example.a2019_seg2105_project.R;

import java.util.List;

public class RegisterActivityUpdate extends AppCompatActivity {
    //                          Fields
    //Set all user information to be private for data protection.
    private EditText user_firstName;
    private EditText user_lastName;
    private EditText user_email;
    private EditText user_password;
    private RadioGroup user_accountType;
    DatabaseReference mDatabase;
    List<User> users;



    @Override

    public void onCreate(Bundle savedInstanceState) {

        // Initialize : load associated XML layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Link fields to corresponding XML layout
        user_firstName = findViewById(R.id.fName);
        user_lastName = findViewById(R.id.lName);
        user_email = findViewById(R.id.emailID);
        user_password = findViewById(R.id.password);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Get buttons on XML layout
        final Button registerButton = findViewById(R.id.register);
        final Button cancelButton = findViewById(R.id.cancelRegister);
        // Inspect if radio button 'employee' has been selected.
        // Prompt text area for user to enter employee number if selected.
        user_accountType  = findViewById(R.id.radioAccountType);

    /*  ================================================================================
        1. Inspect RadioGroup activity: if 'Employee' is selected, display a text area
            for user to enter Employee ID.
            Otherwise, make the Employee ID area invisible.
        <Note>   Employee ID should :
                 - Start with 777
                - End with 9
                - Has an exact length of '8'
                The design of Employee ID is aimed to inhibit User from registering as Employee.
                By setting a length of 8, there will be 10^4 possible combination of Employee ID.

     ================================================================================ */
        user_accountType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.btnEmployee){
                    final EditText employeeNumber = findViewById(R.id.employeeNumber);
                    employeeNumber.setVisibility(View.VISIBLE);

                    /*employeeNumber.addTextChangedListener(afterTextChangedListener);*/
                }
                else
                {
                    final EditText employeeNumber = findViewById(R.id.employeeNumber);
                    employeeNumber.setVisibility(View.INVISIBLE);
                }
            }
        });
      /*  ================================================================================
        2. Set EditText listeners

     ================================================================================ */
      /*  TextWatcher afterTextChangedListener = new TextWatcher() {
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


    /* ================================================================================
        OnClick listener of REGISTER button
        It redirect user to the InitActivity page
        with a 'successfully registered' message
    ================================================================================ */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Extra information that will be provided

                setResult(RESULT_OK,intent);// Set the result that will be returned to caller
                finish();  // end current activity
            }
        });
    /* ================================================================================
            OnClick listener of CANCEL button
            It redirect user to the InitActivity page
     ================================================================================ */
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

    private void writeNewUser(String email, String firstName, boolean isLoggedin, String lastName, String password, String role) {
        User user = new User(email, firstName, isLoggedin, lastName, password, role);

        mDatabase.child("users").setValue(user);
    }

    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                users.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }
//                UserList usersAdapter = new UserList( RegisterActivityUpdate.this, users);
//                listViewUsers.setAdapter(usersAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        }
        );
    }
    private void addUser() {
        String email = user_email.getText().toString().trim();
        String firstName = user_firstName.getText().toString().trim();
        String lastName = user_lastName.getText().toString().trim();
        String password = user_password.getText().toString().trim();
        String role = Integer.toString(user_accountType.getId());



        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
        && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(role)){
            String id = mDatabase.push().getKey();
           //User user = new User(id, email, isLoggedin, firstName, lastName, password, role);
            //mDatabase.child(id).setValue(user);

            user_firstName.setText("");
            user_lastName.setText("");
            user_email.setText("");
            user_password.setText("");


            Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Please enter all the information", Toast.LENGTH_LONG).show();
        }


    }
    public static StringBuffer hash(String password) throws NoSuchAlgorithmException {

        byte [] input = password.getBytes();

        MessageDigest SHA256 = MessageDigest.getInstance("SHA-256");

        SHA256.update(input);
        byte [] digest = SHA256.digest();

        StringBuffer hexDigest = new StringBuffer();
        for (int i=0;i<digest.length;i++)
            hexDigest.append(Integer.toString((digest[i]&0xff)+0x100,16).substring(1));


        return(hexDigest);


    }


}// end of RegisterActivity