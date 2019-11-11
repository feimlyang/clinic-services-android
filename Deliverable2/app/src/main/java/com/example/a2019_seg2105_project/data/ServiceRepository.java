package com.example.a2019_seg2105_project.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2019_seg2105_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ServiceRepository {

    private static volatile ServiceRepository instance;

    private static final String serviceNameKey = "serviceName" ;
    private static final String categoryKey = "category";
    private static final String subcategoryKey = "subcategory";
    private static final String rolePerformingKey = "rolePerforming";

    //constructor
    private ServiceRepository() {}


    public static ServiceRepository getInstance() {
        if (instance == null) {
            instance = new ServiceRepository();
        }
        return instance;
    }


    /* add new service*/
    public LiveData<Result> addService(final String serviceName,
                                       final String category,
                                       final String subcategory,
                                       final String rolePerforming) {
        final DatabaseReference databaseServices;
        final MutableLiveData<Result> liveDataService = new MutableLiveData<>();

        try {
            databaseServices = FirebaseDatabase.getInstance().getReference("services");
            databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(serviceName))
                    {
                        liveDataService.setValue(new Result.Failure(R.string.service_exists));
                    }
                    else
                    {
                        Map<String, String> serviceInfo = new HashMap<>();

                        serviceInfo.put(categoryKey, category);
                        serviceInfo.put(subcategoryKey,subcategory );
                        serviceInfo.put(rolePerformingKey, rolePerforming);

                        databaseServices.child(serviceName).setValue(serviceInfo);

                        liveDataService.setValue(new Result.Success(R.string.service_added));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataService.setValue(new Result.Failure(R.string.addService_failed));
                }
            });
        }
        catch(Exception e)
        {
            liveDataService.setValue(new Result.Failure(R.string.addService_failed));
        }
        return liveDataService;
    }


    /* update an existing service
    * assume serviceName itself can not be updated*/
    public LiveData<Result> editService(final String serviceName,
                                       final String category,
                                       final String subcategory,
                                       final String rolePerforming) {
        final DatabaseReference databaseServices;
        final MutableLiveData<Result> liveDataService = new MutableLiveData<>();

        try {
            databaseServices = FirebaseDatabase.getInstance().getReference("services");
            databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(serviceName))
                    {
                        liveDataService.setValue(new Result.Failure(R.string.service_not_exist));
                    }
                    else
                    {
                        Map<String, String> serviceInfo = new HashMap<>();

                        serviceInfo.put(categoryKey, category);
                        serviceInfo.put(subcategoryKey,subcategory );
                        serviceInfo.put(rolePerformingKey, rolePerforming);

                        databaseServices.child(serviceName).setValue(serviceInfo);

                        liveDataService.setValue(new Result.Success(R.string.service_updated));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataService.setValue(new Result.Failure(R.string.editService_failed));
                }
            });
        }
        catch(Exception e)
        {
            liveDataService.setValue(new Result.Failure(R.string.editService_failed));
        }
        return liveDataService;
    }


    /* delete an existing service
    *  delete by serviceName*/
    public LiveData<Result> deleteService(final String serviceName) {
        final DatabaseReference databaseServices;
        final MutableLiveData<Result> liveDataService = new MutableLiveData<>();

        try {
            databaseServices = FirebaseDatabase.getInstance().getReference("services");
            databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(serviceName))
                    {
                        liveDataService.setValue(new Result.Failure(R.string.service_not_exist));
                    }
                    else
                    {
                        DatabaseReference serviceToRemove = databaseServices.child(serviceName);
                        serviceToRemove.removeValue();
                        liveDataService.setValue(new Result.Success(R.string.service_deleted));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataService.setValue(new Result.Failure(R.string.deleteService_failed));
                }
            });
        }
        catch(Exception e)
        {
            liveDataService.setValue(new Result.Failure(R.string.deleteService_failed));
        }
        return liveDataService;
    }

    /* build list of avaliable services*/
    public LiveData<Result> getServicelist() {
        final DatabaseReference databaseServices;
        // each request sends back a liveData for callback
        final MutableLiveData<Result> liveDataServicelist = new MutableLiveData<>();
        try {
            databaseServices = FirebaseDatabase.getInstance().getReference("services");
            databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Map<String, Map<String, String>> servicelist = new HashMap<String, Map<String, String>>();
                    for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                        String serviceKey = serviceSnapshot.getKey();
                        Map<String, String> attributes = new HashMap<String, String>();
                        for (DataSnapshot attributeSnapshot : serviceSnapshot.getChildren()) {
                            attributes.put(attributeSnapshot.getKey(), attributeSnapshot.getValue(String.class));
                        }
                        servicelist.put(serviceKey, attributes);
                    }
                    liveDataServicelist.setValue(new Result.Success<Map<String, Map<String, String>>>(servicelist));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataServicelist.setValue(new Result.Error(new IOException("Failed to get servicelist")));
                }
            });
        } catch (Exception e) {
            liveDataServicelist.setValue(new Result.Error(new IOException("Failed to get servicelist")));
        }
        return liveDataServicelist;
    }

}
