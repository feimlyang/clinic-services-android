package com.example.a2019_seg2105_project.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.a2019_seg2105_project.data.model.LoggedInUser;

import java.io.IOException;

/**
 * LoginRepository  requests authentication and user information from firebase
 * It maintains an in-memory cache of login status and user credentials information.
 * @see LoggedInUser
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoggedInUser user = null;   // Store current logged in user
    // bad design as LoggedInUser has no field for username...
    private String CurrentLoggedInUserName = null;
    // liveDataLoggedInUser propagated from LoginRepository to ViewModel
    // private constructor : singleton access
    private LoginRepository() {}

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
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
        if(isLoggedIn() && CurrentLoggedInUserName != null)
        {
            final DatabaseReference databaseUsers;
            // each request sends back a liveData for callback
            final MutableLiveData<Result> liveDataLoggedInUser = new MutableLiveData<>();
            try {
                databaseUsers = FirebaseDatabase.getInstance().getReference("users");
                databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(CurrentLoggedInUserName)) {
                            databaseUsers.child(CurrentLoggedInUserName).child("isLoggedin").setValue(false);
                        }
                        user = null;
                        CurrentLoggedInUserName = null;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            catch(Exception e)
            {
            }
        }
    }

    // Set user info when successfully logged - in.
    private void setLoggedInUser(LoggedInUser user) {
        //TODO: cach user credentials +encrypt
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    /**
     * Handling log-in by sending login-request to firebase realtime database.
     * If successful, store cashed user information in 'dataSource'.
     * @param username  username attempting log-in
     * @param password  password associated with username
     * @return Result<LoggedInUser><
     */
    public LiveData<Result> login(final String username, final String password)
    {
        final DatabaseReference databaseUsers;
        // each request sends back a liveData for callback
        final MutableLiveData<Result> liveDataLoggedInUser = new MutableLiveData<>();
        try {
            databaseUsers = FirebaseDatabase.getInstance().getReference("users");
            databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(username))
                    {
                        logout();
                        liveDataLoggedInUser.setValue(new Result.Error(new IOException("Failed to find the user.")));
                    }
                    else
                    {
                        LoggedInUser user = dataSnapshot.child(username).getValue(LoggedInUser.class);
                        if (password.equals(user.password))
                        {
                            setLoggedInUser(user);
                            CurrentLoggedInUserName = username;
                            databaseUsers.child(username).child("isLoggedin").setValue(true);
                            liveDataLoggedInUser.setValue(new Result.Success(user));
                        }
                        else
                        {
                            logout();
                            liveDataLoggedInUser.setValue(new Result.Error(new IOException("Failed to authenticate.")));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataLoggedInUser.setValue(new Result.Error(new IOException("Failed to log in.")));
                }
            });
        }
        catch(Exception e)
        {
            liveDataLoggedInUser.setValue(new Result.Error(new IOException("Failed to log in.")));
        }
        return liveDataLoggedInUser;
    }
}
