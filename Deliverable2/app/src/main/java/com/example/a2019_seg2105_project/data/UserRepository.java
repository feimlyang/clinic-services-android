package com.example.a2019_seg2105_project.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.model.LoggedInUser;
import com.example.a2019_seg2105_project.data.model.Service;
import com.example.a2019_seg2105_project.helpers.HashHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class UserRepository {
    private static volatile UserRepository instance;

    private static final String firstNameKey = "firstName";
    private static final String lastNameKey = "lastName";
    private static final String passwordKey = "password";
    private static final String emailKey = "email";
    private static final String roleKey = "role";
    private static final String patientKey = "patient";
    private static final String employeeKey = "employee";
    private static final String isLoggedInKey = "isLoggedin";

    //constructor
    private UserRepository() {}


    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    /* delete user account given by username*/
    public LiveData<Result> deleteUser(final String username) {
        final DatabaseReference databaseUsers;
        final MutableLiveData<Result> liveDataService = new MutableLiveData<>();

        try {
            if (username.equals("admin")) throw new Exception("cannot delete admin user");

            databaseUsers = FirebaseDatabase.getInstance().getReference("users");
            databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(username))
                    {
                        liveDataService.setValue(new Result.Failure(R.string.username_not_exist));
                    }
                    else
                    {
                        DatabaseReference userToRemove = databaseUsers.child(username);
                        userToRemove.removeValue();

                        liveDataService.setValue(new Result.Success(R.string.user_deleted));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataService.setValue(new Result.Failure(R.string.deleteUser_failed));
                }
            });
        }
        catch(Exception e)
        {
            liveDataService.setValue(new Result.Failure(R.string.deleteUser_failed));
        }
        return liveDataService;
    }


}
