package com.example.a2019_seg2105_project.ui.clinicApp.register;

// UI.

// Data observer and storage.
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

// Basics.
import com.example.a2019_seg2105_project.R;
        import com.example.a2019_seg2105_project.data.RegisterRepository;
import com.example.a2019_seg2105_project.data.Result;

import java.util.regex.Pattern;

/**
 * RegisterViewModel is a class that observes user input changes on UI (registration)
 * and check if inputs are legal.
 *
 * Created : 2019/10/16 , by Android Sample Project
 * Last Modified: 2019/10/20
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
                                       String emailAddress, String passwordVerification,
                                       RegisterFormState.AccountType accountType,
                                       String employAccessCode) {

        RegisterFormState  registerFormStateValue = new RegisterFormState(true);
        // Check if user name input is valid
        if ( !isUsernameLengthValid(username))
            registerFormStateValue.setUsernameError(R.string.invalid_usernameLength);
        if( !isUsernameFormatValid(username))
            registerFormStateValue.setUsernameError(R.string.invalid_usernameFormat);
        // Check if both name field is valid
        if(!isNameValid(firstName)) // check first name
            registerFormStateValue.setFirstNameError(R.string.invalid_name);
        if(!isNameValid(lastName)) // check last name
            registerFormStateValue.setLastNameError(R.string.invalid_name);
        // Check if email address is valid
        if(!isEmailValid(emailAddress)) {
            registerFormStateValue.setEmailError(R.string.invalid_email);
        }
        // Check if password input is valid
        // Case 1: Invalid Length
        // Case 2: Invalid password verification
        if (!isPasswordWithinRange(password))
            registerFormStateValue.setPasswordError(R.string.invalid_passwordLength);
        if(!isPasswordTheSame(password, passwordVerification))
            registerFormStateValue.setPasswordVerificationError(R.string.register_password_unmatch);
        if(accountType == RegisterFormState.AccountType.INVALID || (accountType == RegisterFormState.AccountType.EMPLOYEE && !employAccessCode.equals("1207049")))
        {
            registerFormStateValue.setAccountTypeError(new Integer(1));
            registerFormStateValue.setEmployAccessCodeError(R.string.invalid_access_code);
        }
        registerFormState.setValue(registerFormStateValue);
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
        return ((username.length() <= 10) && (username.length() > 0));
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
        if(nameInArray.length == 0)
            return false;
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
        // ignore when origin password is not set yet.
        if(0 == origin_pw.length()) {
            return true;
        }
        else
        {
            return ( current_pw.length() > 0 && origin_pw.equals(current_pw));
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
