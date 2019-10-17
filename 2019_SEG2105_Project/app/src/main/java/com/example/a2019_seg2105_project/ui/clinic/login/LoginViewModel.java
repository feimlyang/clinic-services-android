package com.example.a2019_seg2105_project.ui.clinic.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.a2019_seg2105_project.data.LoginRepository;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.model.LoggedInUser;
import com.example.a2019_seg2105_project.R;
/**
 * LoginViewModel is a class that
 * More specifically, it:
 * 1) Store 'live' data in UI text field, using MutableLiveData
 * @see LiveData
 * 2) Check if user's login input has valid FORMAT.
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
     * @param username  current entered username
     * @param password  current entered password
     */
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordWithinRange(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_passwordLength));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    /**
     *  Check if current entered username is valid.
     *  @param username  current entered username
     */
    private boolean isUserNameValid(String username) {
        //TODO: Check if username entered is valid.
        //Note: username should be an email address
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    /**
     *  Check if current entered password  meet length requirement.
     *  @param password current content of password field.
     */
    private boolean isPasswordWithinRange(String password) {
        // Return false if password < 5 character or >16
        boolean validPW = false;
        if(password != null
                && password.trim().length() > 5
                && password.trim().length() < 16)
        {
            validPW = true;
        }
        return validPW;
    }


}
