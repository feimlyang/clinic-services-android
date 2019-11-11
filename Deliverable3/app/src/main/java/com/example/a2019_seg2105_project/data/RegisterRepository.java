package com.example.a2019_seg2105_project.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.helpers.HashHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

// singleton RegisterRepository
public class RegisterRepository {

    private static volatile RegisterRepository instance;

    private static final String firstNameKey = "firstName";
    private static final String lastNameKey = "lastName";
    private static final String passwordKey = "password";
    private static final String emailKey = "email";
    private static final String roleKey = "role";
    private static final String patientKey = "patient";
    private static final String employeeKey = "employee";
    private static final String isLoggedInKey = "isLoggedin";

    public static RegisterRepository getInstance() {
        if (instance == null) {
            instance = new RegisterRepository();
        }
        return instance;
    }

    public LiveData<Result> register(final String username, final String password,
                                     final String firstName, final String lastName,
                                     final String emailAddress, final boolean isEmployee)
    {
        final DatabaseReference databaseUsers;

        // each request sends back a liveData for callback
        final MutableLiveData<Result> liveDataRegister = new MutableLiveData<>();
        try {
            databaseUsers = FirebaseDatabase.getInstance().getReference("users");
            databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(username))
                    {
                        liveDataRegister.setValue(new Result.Failure(R.string.register_error_existingAccount));
                    }
                    else
                    {
                        Map<String, String> userInfo = new HashMap<>();

                        userInfo.put(firstNameKey, firstName);
                        userInfo.put(lastNameKey, lastName);
                        userInfo.put(passwordKey, HashHelper.hash(password));
                        userInfo.put(emailKey, emailAddress);
                        userInfo.put(roleKey, isEmployee ? employeeKey : patientKey);

                        databaseUsers.child(username).setValue(userInfo);
                        // login status initialization
                        databaseUsers.child(username).child(isLoggedInKey).setValue(false);
                        liveDataRegister.setValue(new Result.Success<Integer>(R.string.register_succeeds));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataRegister.setValue(new Result.Failure(R.string.register_failed));
                }
            });
        }
        catch(Exception e)
        {
            liveDataRegister.setValue(new Result.Failure(R.string.register_failed));
        }
        return liveDataRegister;
    }
}
