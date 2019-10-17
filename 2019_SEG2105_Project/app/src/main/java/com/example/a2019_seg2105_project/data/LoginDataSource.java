package com.example.a2019_seg2105_project.data;

import com.example.a2019_seg2105_project.data.model.LoggedInUser;

import java.io.IOException;

/**
 * LoginDataSource is Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle log - in authentication

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe"); //Jane Doe = default user name
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication, handle user logging out
    }
}
