package com.example.a2019_seg2105_project.ui.clinic.register;

// UI.
import android.widget.Toast;

// Data observer and storage.
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

// Basics.
import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.LoginRepository;
import com.example.a2019_seg2105_project.data.RegisterRepository;
import com.example.a2019_seg2105_project.data.Result;

import java.util.regex.Pattern;

/**
 * RegisterViewModel is a class that observes user input changes on UI (registration)
 * and check if inputs are legal.
 *
 * Created : 2019/10/16 , by Android Sample Project
 * Last Modified: 2019/10/17
 */
public class RegisterViewModel extends ViewModel
{
    // Use register form to record current UI form status.
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<RegisterFormState>();
    private RegisterRepository registerRepository;
    private MediatorLiveData<RegisterResult> registerResultMediatorLiveData = new MediatorLiveData<>();
    private final String emailAddressPattern = "^(\\d|\\w).*@(\\d|\\w).*\\..+$";
    private final String userNamePattern = "^(\\d|\\w){3,}$";

    public RegisterViewModel(RegisterRepository registerRepository){
        this.registerRepository = registerRepository;
    }
    // Getters
    public LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }
    public LiveData<RegisterResult> getRegisterResultLiveData()
    {
        return this.registerResultMediatorLiveData;
    }
    /**
     *  Insepct data change in username/password text fields.
     *
     *  Note: Constructor in RegisterFromState take argument in the following order:
     *  username, passwrod, firstName,lastName, email. passwordVerification
     * @param username  current 'username' input on UI.
     * @param password  current 'password' input on UI.
     * @param firstName  current 'first name' input on UI.
     * @param lastName  current 'last name' input on UI.
     * @param emailAddress  current 'emaail address' input on UI.
     * @param passwordVerification  current 'password verification' input on UI.
     * @see RegisterFormState
     */
    protected void registerDataChanged(String username, String password,
                                    String firstName, String lastName,
                                    String emailAddress,String passwordVerification) {
        // Check if user name input is valid
        if ( !isUsernameLengthValid(username)) {
            registerFormState.setValue (new RegisterFormState(R.string.invalid_usernameLength,null,null,null,null,null) );
        }
        if( !isUsernameFormatValid(username))
        {
            registerFormState.setValue (new RegisterFormState(R.string.invalid_usernameFormat,null,null,null,null,null) );
        }
        // Check if both name field is valid
        else if(!isNameValid(firstName)) // check first name
        {
            registerFormState.setValue (new RegisterFormState(null,null,R.string.invalid_name,null,null,null));
        }
        else if(!isNameValid(lastName)) // check last name
        {
            registerFormState.setValue (new RegisterFormState(null,null,null,R.string.invalid_name,null,null));
        }
        // Check if email address is valid
        else if(!isEmailValid(emailAddress)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, R.string.invalid_email, null));
        }
        // Check if password input is valid
        // Case 1: Invalid Length
        // Case 2: Invalid password verficiation
        else if (!isPasswordWithinRange(password)) {
            registerFormState.setValue (new RegisterFormState(null, R.string.invalid_passwordLength,null,null,null,null));
        }
        else if(!isPasswordTheSame(password,passwordVerification)) // error type: invalid_password_unmatch
        {
            registerFormState.setValue (new RegisterFormState(null, null,null,null,null,R.string.register_password_unmatch));
        }

        // No error: set form state as 'valid'.
        else
         {
            registerFormState.setValue (new RegisterFormState(true));
        }


    }//end of registerDataChanged()


    //----------------------------- UI Validation -----------------------------
    //Username
    /**
     *  Check if current entered username has valid format.
     *  Username format is valid if it doesn't have symbol such as '.!#@()'
     *  @param username  current entered username (on UI)
     */
    private boolean isUsernameFormatValid(String username) {
        return Pattern.matches(this.userNamePattern, username);
    }
    /**
     *  Check if current entered username has valid length.
     *  Note: username must be 1) Not null 2) Bigegr than 5 characters
     *  @param username  current entered username (on UI)
     */
    private boolean isUsernameLengthValid(String username) {
        return (username.length() <= 10);
    }
    //Name
    /**
     *  Check if current name is valid.
     *  Name is valid if it doesn't contain any digit.
     *  @param name current first/last name input (on UI)
     *  @return boolean
     */
    private boolean isNameValid(String name) {
        char[] nameInArray = name.toCharArray(); // user char array to avoid
                                                // calling String.charAt[i]
        for (int i = 0; i <nameInArray.length; i++) {
            // Note: this will also disable non-english inputs
            if ( !Character.isLetter(nameInArray[i]))
            {
                return false;
            }
        }
        return true;
    }
    //Password
    /**
     *  Check if current entered password  meet length requirement.
     * @param password current content of password field.(on UI)
     */
    private boolean isPasswordWithinRange(String password){
        // Return false if password < 5 character or >16
        return ( !password.contains(" ") && password.length() >= 5 && password.length() <= 16);
    }

    //Re-enter password
    /**
     *  Check if the password re-entered is exactly the same than previous one.
     * @param origin_pw     Value of previous password field.
     * @param current_pw    Value of current password field.
     * @return              If both password are the same.
     */
    private boolean isPasswordTheSame(String origin_pw, String current_pw){
        if( origin_pw .equals("") || current_pw.equals("")) {
            return false;
        }
        else
        {
            return origin_pw.equals(current_pw);
        }
    }
    // Email address

    /**
     *  Check if email address entered has valid format.
     * @param email
     * @return
     */
    private boolean isEmailValid(String email)
    {
        return Pattern.matches(this.emailAddressPattern, email);
    }
    //----------------------------- Register to Database -----------------------------

    /**
     *  Register current information in textfields to the database.
     *
     *  Note: return 1 when successfully registered,
     *        return 0 when userName is duplicated,
     *        retyrb -1 when unknown error occured
     *
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param emailAddress
     * @param isEmployee
     */
    public void register(String username, String password,
                         String firstName, String lastName,
                         String emailAddress, boolean isEmployee)
    {
        final LiveData<Result> resultLiveData = registerRepository.register(
                username,
                password,
                firstName,
                lastName,
                emailAddress,
                isEmployee
        );
        this.registerResultMediatorLiveData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                registerResultMediatorLiveData.removeSource(resultLiveData);
                if(result != null)
                {
                    if(result instanceof Result.Success)
                    {
                        registerResultMediatorLiveData.setValue(new RegisterResult(R.string.register_succeeds));
                    }
                    else if(result instanceof Result.Failure)
                    {
                        registerResultMediatorLiveData.setValue(
                                new RegisterResult((Integer) ((Result.Failure) result).getData()));
                    }
                    else if(result instanceof Result.Error)
                    {
                        registerResultMediatorLiveData.setValue(new RegisterResult(R.string.register_failed));
                    }
                }
                registerResultMediatorLiveData.setValue(null);
            }
        });
    }
}//End of RegisterViewModel
