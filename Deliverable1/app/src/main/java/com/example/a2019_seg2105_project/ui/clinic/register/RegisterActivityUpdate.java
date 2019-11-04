package com.example.a2019_seg2105_project.ui.clinic.register;



import android.os.Bundle;

import java.security.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.RadioGroup;

import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//  ************ Import class Intent
import android.widget.Toast;

import com.example.a2019_seg2105_project.R;

import java.util.List;

public class RegisterActivityUpdate extends AppCompatActivity {
    //                          Fields
    //Set all user information to be private for data protection.
    private EditText user_firstName;
    private EditText user_lastName;
    private EditText user_email;
    private EditText user_password;
    private RadioGroup user_accountType;
    DatabaseReference mDatabase;
    List<User> users;

    private void writeNewUser(String email, String firstName, boolean isLoggedin, String lastName, String password, String role) {
        User user = new User(email, firstName, isLoggedin, lastName, password, role);

        mDatabase.child("users").setValue(user);
    }

    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                users.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        }
        );
    }
    private void addUser() {
        String email = user_email.getText().toString().trim();
        String firstName = user_firstName.getText().toString().trim();
        String lastName = user_lastName.getText().toString().trim();
        String password = user_password.getText().toString().trim();
        String role = Integer.toString(user_accountType.getId());



        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
        && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(role)){
            String id = mDatabase.push().getKey();
            User user = new User(id, email, firstName, false,lastName, password, role);
            mDatabase.child(id).setValue(user);

            user_firstName.setText("");
            user_lastName.setText("");
            user_email.setText("");
            user_password.setText("");


            Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Please enter all the information", Toast.LENGTH_LONG).show();
        }


    }
    public static StringBuffer hash(String password) throws NoSuchAlgorithmException {

        byte [] input = password.getBytes();

        MessageDigest SHA256 = MessageDigest.getInstance("SHA-256");

        SHA256.update(input);
        byte [] digest = SHA256.digest();

        StringBuffer hexDigest = new StringBuffer();
        for (int i=0;i<digest.length;i++)
            hexDigest.append(Integer.toString((digest[i]&0xff)+0x100,16).substring(1));


        return(hexDigest);


    }


}// end of RegisterActivity