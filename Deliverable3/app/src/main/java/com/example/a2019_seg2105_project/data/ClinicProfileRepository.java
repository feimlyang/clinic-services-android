package com.example.a2019_seg2105_project.data;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.model.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ClinicProfileRepository {

    private static volatile ClinicProfileRepository instance;

    final String servicesOffered = "servicesOffered"; //name as subkey in database

    //constructor
    private ClinicProfileRepository() {}


    public static ClinicProfileRepository getInstance() {
        if (instance == null) {
            instance = new ClinicProfileRepository();
        }
        return instance;
    }

    //Services Features:
    /* add new service to clinic Profile by service Name, those services are created by admin user
     * too lookup attributes in each service, use the method in ServiceRepo*/
    public LiveData<Result> addServiceToProfile(final String clinicName, final String serviceName) {
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataClinic = new MutableLiveData<>();

        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(! dataSnapshot.hasChild(clinicName))
                    {
                        liveDataClinic.setValue(new Result.Error(new IOException("clinicName invalid")));
                    }
                    else
                    {
                        if(dataSnapshot.child(clinicName).child(servicesOffered).hasChild(serviceName))
                        {
                            liveDataClinic.setValue(new Result.Failure(R.string.serviceoffered_exits));
                        }
                        else{
                            Map<String, Boolean> serviceInfo = new HashMap<>();
                            serviceInfo.put(serviceName, Boolean.TRUE);
                            databaseClinics.child(clinicName).child(servicesOffered).setValue(serviceInfo);
                        }
                        liveDataClinic.setValue(new Result.Success(R.string.serviceOffered_added));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataClinic.setValue(new Result.Failure(R.string.addServiceOffered_failed));
                }
            });
        }
        catch(Exception e)
        {
            liveDataClinic.setValue(new Result.Failure(R.string.addServiceOffered_failed));
        }
        return liveDataClinic;
    }

    /*Delete service from clinic profile, delete by service name */
    public LiveData<Result> deleteServiceFromProfile(final String clinicName, final String serviceName) {
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataClinic = new MutableLiveData<>();

        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(! dataSnapshot.hasChild(clinicName))
                    {
                        liveDataClinic.setValue(new Result.Error(new IOException("clinicName invalid")));
                    }
                    else {
                        if (!dataSnapshot.child(clinicName).child(servicesOffered).hasChild(serviceName)) {
                            liveDataClinic.setValue(new Result.Error(new IOException("service is invalid in this clinic")));
                        }
                        else {
                            DatabaseReference serviceToRemove = databaseClinics.child(clinicName).child(servicesOffered).child(serviceName);
                            serviceToRemove.removeValue();
                            liveDataClinic.setValue(new Result.Success(R.string.serviceOffered_deleted));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataClinic.setValue(new Result.Failure(R.string.deleteServiceOffered_failed));
                }
            });
            }
        catch(Exception e)
        {
            liveDataClinic.setValue(new Result.Failure(R.string.deleteServiceOffered_failed));
        }
        return liveDataClinic;
    }


    /*get all services added into profile in a list*/
    public LiveData<Result> getServicesOfferedList(final String clinicName){
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataServicelist = new MutableLiveData<>();
        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<String> serviceOfferedList = new ArrayList<>();
                    for (DataSnapshot serviceSnapshot : dataSnapshot.child(clinicName).child(servicesOffered).getChildren()) {
                        String serviceKey = serviceSnapshot.getKey();
                        serviceOfferedList.add(serviceKey);
                    }
                    liveDataServicelist.setValue(new Result.Success<ArrayList<String>>(serviceOfferedList));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataServicelist.setValue(new Result.Error(new IOException("Failed to get service list in the profile")));
                }
            });
        } catch (Exception e) {
            liveDataServicelist.setValue(new Result.Error(new IOException("Failed to get service list in the profile")));
        }
        return liveDataServicelist;
    }
    


    //Clinic Profile Features:
    /*set or reset profile*/


    /*get profile by give employee user name if the profile is already set under this user*/

    // Working hours Features:
    /*set or reset working hours list in a given date*/

    /*get working hours list in a give date*/






}
