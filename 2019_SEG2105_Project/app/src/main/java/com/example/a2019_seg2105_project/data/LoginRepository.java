package com.example.a2019_seg2105_project.data;

import com.example.a2019_seg2105_project.data.model.LoggedInUser;

/**
 * LoginRepository  requests authentication and user information from remote data source.
 * It maintains an in-memory cache of login status and user credentials information.
 * @see LoginDataSource
 * @see LoggedInUser
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource; // Used to authenticate 'login' operation
    private LoggedInUser user = null;   // Store current logged in user

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }
    // Check if there is logged-in user.
    public boolean isLoggedIn() {
        return user != null;
    }

    // Log the user out.
    //@see LoginDataSource
    public void logout() {
        user = null;
        dataSource.logout();
    }

    // Set user info when successfully logged - in.
    private void setLoggedInUser(LoggedInUser user) {
        //TODO: cach user credentials +encrypt
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }


    /**
     * Handling log-in by calling login() in LoginDataSource class.
     * @see LoginDataSource
     * If successful, store cashed user information in 'dataSource'.
     * @param username  username attempting log-in
     * @param password  password associated with username
     * @return Result<LoggedInUser><
     */
    public Result<LoggedInUser> login(String username, String password) {
        //Cbeck if Login has been successful.
        Result<LoggedInUser> result = dataSource.login(username, password);
        // If Successful: call setLoggedInUser and set current 'logged in user'
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        //else
        return result;
    }
}
