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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ClinicRepository {

    private static volatile ClinicRepository instance;

    final String servicesOffered = "servicesOffered"; //name as subkey in database
    //constructor
    private ClinicRepository() {}


    public static ClinicRepository getInstance() {
        if (instance == null) {
            instance = new ClinicRepository();
        }
        return instance;
    }


    /* add new service to clinic Profile by service Name, those services are created by admin user
     * too lookup attributes in each service, use the method in ServiceRepo*/
    public LiveData<Result> addServiceToProfile(final String employeeUsername, final String serviceName) {
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataClinic = new MutableLiveData<>();

        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(! dataSnapshot.hasChild(employeeUsername))
                    {
                        liveDataClinic.setValue(new Result.Error(new IOException("clinic invalid")));
                    }
                    else
                    {
                        if(dataSnapshot.child(employeeUsername).child(servicesOffered).hasChild(serviceName))
                        {
                            liveDataClinic.setValue(new Result.Failure(R.string.serviceoffered_exits));
                        }
                        else{
                            Map<String, Boolean> serviceInfo = new HashMap<>();
                            serviceInfo.put(serviceName, Boolean.TRUE);
                            databaseClinics.child(employeeUsername).child(servicesOffered).setValue(serviceInfo);
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
    public LiveData<Result> deleteServiceFromProfile(final String employeeUsername, final String serviceName) {
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataClinic = new MutableLiveData<>();

        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(! dataSnapshot.hasChild(employeeUsername))
                    {
                        liveDataClinic.setValue(new Result.Error(new IOException("clinic invalid")));
                    }
                    else {
                        if (!dataSnapshot.child(employeeUsername).child(servicesOffered).hasChild(serviceName)) {
                            liveDataClinic.setValue(new Result.Error(new IOException("service is invalid in this clinic")));
                        }
                        else {
                            DatabaseReference serviceToRemove = databaseClinics.child(employeeUsername).child(servicesOffered).child(serviceName);
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
    public LiveData<Result> getServicesOfferedList(final String employeeUsername){
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataServicelist = new MutableLiveData<>();
        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(! dataSnapshot.hasChild(employeeUsername))
                    {
                        liveDataServicelist.setValue(new Result.Error(new IOException("clinic invalid")));
                    }

                    ArrayList<String> serviceOfferedList = new ArrayList<>();
                    for (DataSnapshot serviceSnapshot : dataSnapshot.child(employeeUsername).child(servicesOffered).getChildren()) {
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


    /*set or reset profile */
    public LiveData<Result> setClinicProfile(final String employeeUsername, final String clinicName, final String clinicAddress, final String clinicPhoneNum,
                                             final ArrayList<String> insuranceType, final ArrayList<String> paymentMethod ){
            final DatabaseReference databaseProfile;
            final MutableLiveData<Result> liveDataProfile = new MutableLiveData<>();

            try {
                databaseProfile = FirebaseDatabase.getInstance().getReference("clinics");
                databaseProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final DatabaseReference fromEmployeeUsername = databaseProfile.child(employeeUsername);
                        Map<String, String> toClinicName= new HashMap<>();
                        Map<String, String> toClinicAddress = new HashMap<>();
                        Map<String, String> toClinicPhoneNum = new HashMap<>();
                        toClinicName.put("clinicName", clinicName);
                        toClinicAddress.put("clinicAddress", clinicAddress);
                        toClinicPhoneNum.put("clinicPhoneNum", clinicPhoneNum);
                        fromEmployeeUsername.setValue(toClinicName);
                        fromEmployeeUsername.setValue(toClinicAddress);
                        fromEmployeeUsername.setValue(toClinicPhoneNum);

                        for(String elemIns : insuranceType){
                            Map<String, Boolean> elemInsMap = new HashMap<>();
                            elemInsMap.put(elemIns, Boolean.TRUE);
                            fromEmployeeUsername.child("insuranceType").setValue(elemInsMap);
                        }
                        for(String elemPay : paymentMethod) {
                            Map<String, Boolean> elemPayMap = new HashMap<>();
                            elemPayMap.put(elemPay, Boolean.TRUE);
                            fromEmployeeUsername.child("paymentMethod").setValue(elemPayMap);
                        }

                        liveDataProfile.setValue(new Result.Success(R.string.profile_updated));
                }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        liveDataProfile.setValue(new Result.Failure(R.string.profile_updated_failed));
                    }
                });
            }
            catch(Exception e)
            {
                liveDataProfile.setValue(new Result.Failure(R.string.profile_updated_failed));
            }
            return liveDataProfile;
    }


    /*get profile by give employee username if the profile is already set under this user
    * subKey could be either:clinicName, clinicAddress, clinicPhoneNum, insuranceType, paymentMethod */
    public LiveData<Result> getProfileInfo(final String employeeUsername, final String subKey){
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataClinic = new MutableLiveData<>();
        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(employeeUsername)) {
                        liveDataClinic.setValue(new Result.Failure(R.string.profile_invalid));
                    }
                    else {
                        final DataSnapshot fromEmployeeUsername = dataSnapshot.child(employeeUsername);
                        if (!fromEmployeeUsername.hasChild(subKey)){
                            liveDataClinic.setValue(new Result.Failure(R.string.profile_invalid));
                        }
                        else{
                            final DataSnapshot fromSubKey = dataSnapshot.child(subKey);
                            if(subKey.equals("clinicName")|| subKey.equals("clinicAddress")|| subKey.equals("clinicPhoneNum")){
                                String returnInfoString = fromSubKey.getValue(String.class);
                                liveDataClinic.setValue(new Result.Success<String>(returnInfoString));
                            }
                            else{
                                ArrayList<String> returnInfoList = new ArrayList();
                                for ( DataSnapshot elem : fromSubKey.getChildren()){
                                    returnInfoList.add(elem.getKey());
                                }
                                liveDataClinic.setValue(new Result.Success<ArrayList<String>>(returnInfoList));
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataClinic.setValue(new Result.Failure(R.string.getProfileInfo_failed));
                }
            });
        } catch (Exception e) {
            liveDataClinic.setValue(new Result.Failure(R.string.getProfileInfo_failed));
        }
        return liveDataClinic;
    }


    /*set or reset working hours list in a given date*/
    public LiveData<Result> setWorkingHours(final String employeeUsername, final String date, final ArrayList<String> listOfTimeSlot){
        final DatabaseReference databaseWorkingHours;
        final MutableLiveData<Result> liveDataWorkingHours = new MutableLiveData<>();

        try {
            databaseWorkingHours = FirebaseDatabase.getInstance().getReference("clinics");
            databaseWorkingHours.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(employeeUsername))
                    {
                        liveDataWorkingHours.setValue(new Result.Failure(R.string.profile_invalid));
                    }
                    else
                    {
                        //wrap data
                        Map<String, Map<String, Boolean>> dataElem = new HashMap<>();
                        Map<String, Boolean> timeSlotElem = new HashMap<>();
                        for (String timeSlot : listOfTimeSlot){
                            timeSlotElem.put(timeSlot, true);
                        }
                        dataElem.put(date, timeSlotElem );

                        if (!dataSnapshot.child(employeeUsername).hasChild("workingHours")){
                            //workingHours has not been created
                            databaseWorkingHours.setValue("workingHours", dataElem);
                        }
                        else {
                            DatabaseReference fromWorkingHours = databaseWorkingHours.child("workingHours");
                            fromWorkingHours.setValue(dataElem);
                        }
                        liveDataWorkingHours.setValue(new Result.Success(R.string.wrokingHours_updated));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataWorkingHours.setValue(new Result.Failure(R.string.wrokingHours_updated_failed));
                }
            });
        }
        catch(Exception e)
        {
            liveDataWorkingHours.setValue(new Result.Failure(R.string.wrokingHours_updated_failed));
        }
        return liveDataWorkingHours;

    }

    /*get working hours list in a give date*/
    public  LiveData getWorkingHours(final String employeeUsername, final String date){
        final DatabaseReference databaseWorkingHours;
        final MutableLiveData<Result> liveDataWorkingHoursList = new MutableLiveData<>();

        try {
            databaseWorkingHours = FirebaseDatabase.getInstance().getReference("clinics");
            databaseWorkingHours.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(employeeUsername) ||
                            !dataSnapshot.child(employeeUsername).hasChild(date)) {
                        liveDataWorkingHoursList.setValue(new Result.Failure(R.string.profile_invalid));
                    }
                    DataSnapshot fromDate = dataSnapshot.child(employeeUsername).child(date);
                    ArrayList<String> workingHoursList = new ArrayList<>();

                    for (DataSnapshot timeslot: fromDate.getChildren()){
                        workingHoursList.add(timeslot.getKey());
                    }

                    liveDataWorkingHoursList.setValue(new Result.Success<ArrayList<String>>(workingHoursList));

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataWorkingHoursList.setValue(new Result.Failure(R.string.profile_updated_failed));
                }
            });
        }
        catch(Exception e)
        {
            liveDataWorkingHoursList.setValue(new Result.Failure(R.string.profile_updated_failed));
        }
        return liveDataWorkingHoursList;

    }


}
