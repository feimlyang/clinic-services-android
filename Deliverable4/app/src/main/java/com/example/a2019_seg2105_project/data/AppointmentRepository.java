package com.example.a2019_seg2105_project.data;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.model.Clinic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AppointmentRepository {


    private static volatile AppointmentRepository instance;

    private AppointmentRepository() {
    }

    public static AppointmentRepository getInstance() {
        if (instance == null) {
            instance = new AppointmentRepository();
        }
        return instance;
    }



    /*spinner value for existing address in all clinics*/


    /*spinner value for existing serviceOffered in all clinics*/





    /*search for a walk in clinic by address, working hours, type of services
     * returns ArrayList of hashmap
     * List( employeeName[i]( List of clinicInfo[i]: Map(elemKey, elemValue))
     * clinicInfo[i] includes: employeeName, clinicName, clinicAddress, rate->score(String)
     *
     * Input: ArraysList(address), ArrayList(serviceOffered), ArrayList(timeslot)*/
    public LiveData<Result> searchClinic(final ArrayList<String> selectedAddressList,
                                         final ArrayList<String> selectedServiceList,
                                         final ArrayList<String> selectedTimeSlotList) {
        //final DatabaseReference databaseAppointments;
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataClinics = new MutableLiveData<>();
        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<ArrayList<HashMap<String, String>>> resultList = new ArrayList<>();
                    ArrayList<String> checkClinic = new ArrayList<>();

                    search1:
                    for (String addressElem : selectedAddressList) {
                        for (DataSnapshot eachClinic : dataSnapshot.getChildren()) {
                            if (eachClinic.hasChild("clinicAddress")
                                    && addressElem.equals(eachClinic.child("clinicAddress").getValue())) {
                                if (!checkClinic.contains(eachClinic.getKey())) {
                                    checkClinic.add(eachClinic.getKey());
                                    HashMap<String, String> employeeName = new HashMap<>();
                                    HashMap<String, String> clinicName = new HashMap<>();
                                    HashMap<String, String> clinicAddress = new HashMap<>();
                                    HashMap<String, String> clinicRate = new HashMap<>();
                                    employeeName.put("employeeName", eachClinic.getKey());
                                    clinicName.put("clinicName", eachClinic.child("clinicName").getValue(String.class));
                                    clinicAddress.put("clinicAddress", eachClinic.child("clinicAddress").getValue(String.class));
                                    clinicRate.put("clinicRate", eachClinic.child("rate").child("aveScore").getValue(String.class));
                                    ArrayList<HashMap<String, String>> clinicInfo = new ArrayList<>();
                                    clinicInfo.add(employeeName);
                                    clinicInfo.add(clinicName);
                                    clinicInfo.add(clinicAddress);
                                    clinicInfo.add(clinicRate);
                                    resultList.add(clinicInfo);
                                } else break search1;
                            }
                        }
                    }
                    search2:
                    for (String serviceElem : selectedServiceList) {
                        for (DataSnapshot eachClinic : dataSnapshot.getChildren()) {
                            if (eachClinic.hasChild("servicesOffered")
                                    && eachClinic.child("servicesOffered").hasChild(serviceElem)) {
                                if (!checkClinic.contains(eachClinic.getKey())) {
                                    checkClinic.add(eachClinic.getKey());
                                    HashMap<String, String> employeeName = new HashMap<>();
                                    HashMap<String, String> clinicName = new HashMap<>();
                                    HashMap<String, String> clinicAddress = new HashMap<>();
                                    HashMap<String, String> clinicRate = new HashMap<>();
                                    employeeName.put("employeeName", eachClinic.getKey());
                                    clinicName.put("clinicName", eachClinic.child("clinicName").getValue(String.class));
                                    clinicAddress.put("clinicAddress", eachClinic.child("clinicAddress").getValue(String.class));
                                    clinicRate.put("clinicRate", eachClinic.child("rate").child("aveScore").getValue(String.class));
                                    ArrayList<HashMap<String, String>> clinicInfo = new ArrayList<>();
                                    clinicInfo.add(employeeName);
                                    clinicInfo.add(clinicName);
                                    clinicInfo.add(clinicAddress);
                                    clinicInfo.add(clinicRate);
                                    resultList.add(clinicInfo);
                                }
                                else break search2;
                            }
                        }

                    }
                    search3:
                    for (String timeSlotElem : selectedTimeSlotList) {
                        for (DataSnapshot eachClinic : dataSnapshot.getChildren()) {
                            if (eachClinic.hasChild("workingHours")) {
                                for (DataSnapshot eachWorkingDate : eachClinic.child("workingHours").getChildren()) {
                                    if (eachWorkingDate.hasChild(timeSlotElem)) {
                                        if (!checkClinic.contains(eachClinic.getKey())) {
                                            checkClinic.add(eachClinic.getKey());
                                            HashMap<String, String> employeeName = new HashMap<>();
                                            HashMap<String, String> clinicName = new HashMap<>();
                                            HashMap<String, String> clinicAddress = new HashMap<>();
                                            HashMap<String, String> clinicRate = new HashMap<>();
                                            employeeName.put("employeeName", eachClinic.getKey());
                                            clinicName.put("clinicName", eachClinic.child("clinicName").getValue(String.class));
                                            clinicAddress.put("clinicAddress", eachClinic.child("clinicAddress").getValue(String.class));
                                            clinicRate.put("clinicRate", eachClinic.child("rate").child("aveScore").getValue(String.class));
                                            ArrayList<HashMap<String, String>> clinicInfo = new ArrayList<>();
                                            clinicInfo.add(employeeName);
                                            clinicInfo.add(clinicName);
                                            clinicInfo.add(clinicAddress);
                                            clinicInfo.add(clinicRate);
                                            resultList.add(clinicInfo);
                                        }
                                        else break search3;
                                    }
                                }
                            }
                        }
                    }
                    if (null == resultList){
                        liveDataClinics.setValue(new Result.Success(R.string.search_result_null));
                    }
                    else{
                        liveDataClinics.setValue(new Result.Success<ArrayList<ArrayList<HashMap<String, String>>>>(resultList));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataClinics.setValue(new Result.Failure(R.string.failed));
                }
            });
        } catch (Exception e) {
            liveDataClinics.setValue(new Result.Failure(R.string.failed));
        }
        return liveDataClinics;
    }


    /*book an appointment, should provide all of the appointment info */
    public LiveData<Result> addAppointment(final String patientUsername, final String dateTime, final String employeeUsername,
                                           final String bookedService, final int waitingTime) {
        final DatabaseReference databaseAppointments;
        final MutableLiveData<Result> liveDataAppointments = new MutableLiveData<>();
        try {
            databaseAppointments = FirebaseDatabase.getInstance().getReference("appointments");
            databaseAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(patientUsername) && dataSnapshot.child(patientUsername).hasChild(dateTime)) {
                        liveDataAppointments.setValue(new Result.Failure(R.string.already_booked));
                    } else {
                        databaseAppointments.child(patientUsername).child(dateTime).child("bookedService").setValue(bookedService);
                        databaseAppointments.child(patientUsername).child(dateTime).child("isCheckedIn").setValue(false);
                        databaseAppointments.child(patientUsername).child(dateTime).child("waitingTime").setValue(waitingTime);
                        databaseAppointments.child(patientUsername).child(dateTime).child("employeeName").setValue(employeeUsername);
                        liveDataAppointments.setValue(new Result.Success(R.string.success_booked));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataAppointments.setValue(new Result.Failure(R.string.failed));
                }
            });
        } catch (Exception e) {
            liveDataAppointments.setValue(new Result.Failure(R.string.failed));
        }
        return liveDataAppointments;
    }

    /*get all appointmnet information for a paitent*/
    public LiveData<Result> getAllAppointments(final String patientUsername) {
        final DatabaseReference databaseAppointments;
        final MutableLiveData<Result> liveDataAppointments = new MutableLiveData<>();
        try {
            databaseAppointments = FirebaseDatabase.getInstance().getReference("appointments");
            databaseAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(patientUsername)) {
                        liveDataAppointments.setValue(new Result.Error(new IOException("patient invalid")));
                    } else {
                        ArrayList<Map<String, Map<String, String>>> appointmentList = new ArrayList<>();
                        for (DataSnapshot appointment : dataSnapshot.child(patientUsername).getChildren()) {
                            String dateTime = appointment.getKey();
                            Map<String, Map<String, String>> eachAppointment = new HashMap<>();

                            for (DataSnapshot appointmentElem : dataSnapshot.child(patientUsername).child(dateTime).getChildren()) {
                                String elemKey = appointmentElem.getKey();
                                String elemValue = appointmentElem.child(elemKey).getValue(String.class);
                                Map<String, String> elem = new HashMap<>();
                                elem.put(elemKey, elemValue);
                                eachAppointment.put(dateTime, elem);
                            }
                            appointmentList.add(eachAppointment);
                        }
                        liveDataAppointments.setValue(new Result.Success<ArrayList<Map<String, Map<String, String>>>>(appointmentList));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataAppointments.setValue(new Result.Failure(R.string.failed));
                }
            });
        } catch (Exception e) {
            liveDataAppointments.setValue(new Result.Failure(R.string.failed));
        }
        return liveDataAppointments;
    }


    /*checkin a appointment if it is booked*/
    public LiveData<Result> isCheckedIn(final String patientUsername, final String dateTime, final Boolean checkedIn) {
        final DatabaseReference databaseAppointments;
        final MutableLiveData<Result> liveDataAppointments = new MutableLiveData<>();
        try {
            databaseAppointments = FirebaseDatabase.getInstance().getReference("appointments");
            databaseAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(patientUsername) || !dataSnapshot.child(patientUsername).hasChild(dateTime)) {
                        liveDataAppointments.setValue(new Result.Error(new IOException("invalid input")));
                    } else {
                        if (dataSnapshot.child(patientUsername).child(dateTime).hasChild("isCheckedIn")
                                && dataSnapshot.child(patientUsername).child(dateTime).child("isCheckedIn").getValue().equals(true)) {
                            liveDataAppointments.setValue(new Result.Failure(R.string._already_CheckedIn));
                        } else {
                            databaseAppointments.child(patientUsername).child(dateTime).child("isCheckedIn").setValue(true);
                            liveDataAppointments.setValue(new Result.Success(R.string.isCheckedIn));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataAppointments.setValue(new Result.Failure(R.string.fail_checkedIn));
                }
            });
        } catch (Exception e) {
            liveDataAppointments.setValue(new Result.Failure(R.string.fail_checkedIn));
        }
        return liveDataAppointments;
    }


    /*calculate the waiting hours on a certain clinic and a certain datetime
     * 15mins for each person in line*/
    public LiveData<Result> calculateWaitingTime(final String patientUsername, final String dateTime, final String employeeUsername) {
        final DatabaseReference databaseAppointments;
        final MutableLiveData<Result> liveDataAppointments = new MutableLiveData<>();
        try {
            databaseAppointments = FirebaseDatabase.getInstance().getReference("appointments");
            databaseAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int counter = 0;
                    for (DataSnapshot eachPatient : dataSnapshot.getChildren()) {
                        if (eachPatient.hasChild(dateTime) && patientUsername.equals(eachPatient.child(dateTime).child("employeeName").getValue()))
                            counter++;
                    }
                    Integer waitingTimeInMins = new Integer(15 * counter);
                    liveDataAppointments.setValue(new Result.Success<Integer>(waitingTimeInMins));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataAppointments.setValue(new Result.Failure(R.string.failed));
                }
            });
        } catch (Exception e) {
            liveDataAppointments.setValue(new Result.Failure(R.string.failed));
        }
        return liveDataAppointments;
    }

    /*rate a service after check in, each rating include a score and a comment*/
    public LiveData<Result> rateAppointment(final String employeeName,
                                            final Float score, final String comment) {
        final DatabaseReference databaseAppointments;
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataAppointments = new MutableLiveData<>();
        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(employeeName)) {
                        liveDataAppointments.setValue(new Result.Error(new IOException("invalid input")));
                    } else {
                        DatabaseReference fromClinic = databaseClinics.child(employeeName);
                        if (!dataSnapshot.child(employeeName).hasChild("rate")) {
                         //this clinic has never received a rate
                            String commentNum = "comment" + String.valueOf(0);
                            fromClinic.child("rate").child("counter").setValue(0);
                            fromClinic.child("rate").child(commentNum).setValue(comment);
                            fromClinic.child("rate").child("score").setValue(score);
                            liveDataAppointments.setValue(new Result.Success(R.string.rated_appointment));
                        } else {
                         //this clinic has a score already, need count up and take average score
                            DatabaseReference fromRate = fromClinic.child("rate");
                            Integer counter = dataSnapshot.child(employeeName).child("rate").child("counter").getValue(Integer.class);
                            Float aveScore = dataSnapshot.child(employeeName).child("rate").child("score").getValue(Float.class);
                            aveScore = (aveScore * counter + score) / (++counter);
                            String commentNum = "comment" + counter;
                            fromRate.child("counter").setValue(counter);
                            fromRate.child(commentNum).setValue(comment);
                            fromRate.child("score").setValue(aveScore);
                            liveDataAppointments.setValue(new Result.Success(R.string.rated_appointment));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataAppointments.setValue(new Result.Failure(R.string.fail_rated_appointment));
                }
            });
        } catch (Exception e) {
            liveDataAppointments.setValue(new Result.Failure(R.string.fail_rated_appointment));
        }
        return liveDataAppointments;
    }

}
