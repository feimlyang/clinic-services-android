package com.example.a2019_seg2105_project.ui.clinicApp.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
    // loginResultLiveData observed by Activity
    private MediatorLiveData<LoginResult> loginResultLiveData = new MediatorLiveData<LoginResult>();
    // loginRepository responsible for sending queries to firebase
    private LoginRepository loginRepository;

    // Constructors
    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }
    LiveData<LoginResult> getLoginResult() {
        return loginResultLiveData;
    }
    /**
     * Pass user information to login authentication,store user name if login is successful.
     *
     * @param username  current entered
     * @param password  current password
     */
    public void login(String username, String password) {
        final LiveData<Result> resultLiveData = loginRepository.login(username, password);
        loginResultLiveData.addSource(resultLiveData, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                loginResultLiveData.removeSource(resultLiveData);
                if(result != null) {
                    if(result instanceof Result.Error)
                    {
                        loginResultLiveData.setValue(new LoginResult(R.string.login_failed));
                    }
                    else
                    {
                        LoggedInUser loggedInUser = ((Result.Success<LoggedInUser>)result).getData();
                        loginResultLiveData.setValue(new LoginResult(new LoggedInUserView(loggedInUser.firstName, loggedInUser.role)));
                    }
                }
                else
                {
                    loginResultLiveData.setValue(new LoginResult(R.string.login_failed));
                }
                // remove the value after dispatching to all of its observers.
                loginResultLiveData.setValue(null);
            }
        });
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
        return !username.contains(" ") && username.length() <= 10;
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
