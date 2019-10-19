package com.example.a2019_seg2105_project.ui.clinic.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2019_seg2105_project.data.LoginRepository;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.model.LoggedInUser;
import com.example.a2019_seg2105_project.R;
/**
 * LoginViewModel is a class that observes user input change on UI (registration)
 * and check if input are legal.
 *
 * Note:
 * The class Contains a login() that will call login() of LoginRepository.
 * LoginActivity will call this method while LOGIN button is clicked.
 * Created : 2019/10/16 , by Android Sample Project
 *
 * Last Modified: 2019/10/17
 */
public class LoginViewModel extends ViewModel {
    // Fields
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    // Constructors
    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }
    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
    /**
     * Pass user information to login authentication,store user name if login is successful.
     *
     * @param username  current entered
     * @param password  current password
     */
    public void login(String username, String password) {
        //TODO:
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            //Pass current logged-in user name, stored in LoggedInUser, to LoggedInUserView
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    /**
     * Insepct data change in username/password text fields.
     * @param username  current entered username.
     * @param password  current entered password.
     */
    protected  void loginDataChanged(String username, String password) {
        if (! isUsernameLengthValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_usernameLength, null));
        } else if (!isPasswordWithinRange(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_passwordLength));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    /**
     *  Check if current entered username has valid length.
     *  Note: username must be 1) Not null 2) Bigegr than 5 characters
     *  @param username  current entered username (on UI)
     */
    private boolean isUsernameLengthValid(String username)
    {
        return (username.equals("") && username.length()<= 10) ?true:false;
    }

    /**
     *  Check if current entered password  meet length requirement.
     *  @param password current content of password field.(on UI)
     */
    private boolean isPasswordWithinRange(String password){
        // Return false if password < 5 character or >16
        return (password != null && password.trim().length() >= 5 && password.trim().length() <= 16);
    }


}
