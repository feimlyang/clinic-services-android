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
    public LiveData<Result> getAddressSpinner() {
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataSpinnerValues = new MutableLiveData<>();
        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> addressSpinnerValues = new ArrayList<>();

                    for (DataSnapshot eachClinic : dataSnapshot.getChildren()) {
                        if (eachClinic.hasChild("clinicAddress")
                                && !addressSpinnerValues.contains(eachClinic.child("clinicAddress").getValue())) {
                            addressSpinnerValues.add(eachClinic.child("clinicAddress").getValue(String.class));
                        }
                    }
                    if (null == addressSpinnerValues) {
                        liveDataSpinnerValues.setValue(new Result.Success(R.string.search_result_null));
                    } else {
                        liveDataSpinnerValues.setValue(new Result.Success<ArrayList<String>>(addressSpinnerValues));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataSpinnerValues.setValue(new Result.Failure(R.string.failed));
                }
            });
        } catch (Exception e) {
            liveDataSpinnerValues.setValue(new Result.Failure(R.string.failed));
        }
        return liveDataSpinnerValues;
    }


    /*spinner value for existing serviceOffered in all clinics*/
    public LiveData<Result> getServiceSpinner() {
        final DatabaseReference databaseClinics;
        final MutableLiveData<Result> liveDataSpinnerValues = new MutableLiveData<>();
        try {
            databaseClinics = FirebaseDatabase.getInstance().getReference("clinics");
            databaseClinics.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> serviceSpinnerValues = new ArrayList<>();

                    for (DataSnapshot eachClinic : dataSnapshot.getChildren()) {
                        if (eachClinic.hasChild("servicesOffered")) {
                            for (DataSnapshot eachService : eachClinic.child("servicesOffered").getChildren()) {
                                if (!serviceSpinnerValues.contains(eachService.getKey())) {
                                    serviceSpinnerValues.add(eachService.getKey());
                                }
                            }
                        }
                    }
                    if (null == serviceSpinnerValues) {
                        liveDataSpinnerValues.setValue(new Result.Success(R.string.search_result_null));
                    } else {
                        liveDataSpinnerValues.setValue(new Result.Success<ArrayList<String>>(serviceSpinnerValues));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataSpinnerValues.setValue(new Result.Failure(R.string.failed));
                }
            });
        } catch (Exception e) {
            liveDataSpinnerValues.setValue(new Result.Failure(R.string.failed));
        }
        return liveDataSpinnerValues;
    }


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
                    ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
                    for (DataSnapshot eachClinic : dataSnapshot.getChildren()) {

                        ArrayList<String> filterServiceList = selectedServiceList == null ? new ArrayList<String>() : new ArrayList<String>(selectedServiceList);
                        ArrayList<String> filterTimeSlotList = selectedTimeSlotList == null ? new ArrayList<String>() : new ArrayList<String>(selectedTimeSlotList);

                        String address = eachClinic.child("clinicAddress").getValue(String.class);
                        boolean isValidClinic = false;
                        // condition 1: address matches
                        if ((null == selectedAddressList) || selectedAddressList.contains(address)) {
                            isValidClinic = true;
                        }

                        // condition 2: services offered match
                        if (isValidClinic && eachClinic.hasChild("servicesOffered")) {
                            for (DataSnapshot eachService : eachClinic.child("servicesOffered").getChildren()) {
                                if (null == selectedServiceList){
                                    break;
                                }
                                filterServiceList.remove(eachService.getKey());
                            }
                        }

                        if(null != selectedServiceList && filterServiceList.size() > 0) {
                            isValidClinic = false;
                        }

                        // condition 3 : workinghours match
                        if (isValidClinic && eachClinic.hasChild("workingHours")) {
                            for (DataSnapshot eachDay : eachClinic.child("workingHours").getChildren()) {
                                for (DataSnapshot eachHour : eachDay.getChildren()) {
                                    if (null == selectedTimeSlotList) {
                                        break;
                                    }
                                    filterTimeSlotList.remove(eachHour.getKey());
                                }
                            }
                        }
                        if(null != selectedTimeSlotList && filterTimeSlotList.size() > 0) {
                            isValidClinic = false;
                        }

                        if (isValidClinic) {
                            HashMap<String, String> thisClinic = new HashMap<>();
                            thisClinic.put("employeeName", eachClinic.getKey());
                            if (eachClinic.hasChild("clinicName")) {
                                thisClinic.put("clinicName", eachClinic.child("clinicName").getValue(String.class));
                            } else {
                                thisClinic.put("clinicName", "null");
                            }
                            if (eachClinic.hasChild("clinicAddress")) {
                                thisClinic.put("clinicAddress", eachClinic.child("clinicAddress").getValue(String.class));
                            } else {
                                thisClinic.put("clinicAddress", "");
                            }
                            if (eachClinic.hasChild("rate") && eachClinic.hasChild("rate/score")) {
                                thisClinic.put("clinicRate", eachClinic.child("rate").child("score").getValue(Float.class).toString());
                            } else {
                                thisClinic.put("clinicRate", "null");
                            }
                            resultList.add(thisClinic);
                        }
                    }
                    liveDataClinics.setValue(new Result.Success<ArrayList<HashMap<String, String>>>(resultList));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    liveDataClinics.setValue(new Result.Failure(R.string.failed));
                }
            });
        } catch (
                Exception e) {
            liveDataClinics.setValue(new Result.Failure(R.string.failed));
        }
        return liveDataClinics;
    }


    /*book an appointment, should provide all of the appointment info */
    public LiveData<Result> addAppointment(final String patientUsername, final String dateTime, final String employeeUsername,
                                           final String clinicName, final String clinicAddress,
                                           final String bookedService) {
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
                        databaseAppointments.child(patientUsername).child(dateTime).child("employeeName").setValue(employeeUsername);
                        databaseAppointments.child(patientUsername).child(dateTime).child("clinicName").setValue(clinicName);
                        databaseAppointments.child(patientUsername).child(dateTime).child("clinicAddress").setValue(clinicAddress);
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
                        Map<String, Map<String, String>> appointmentMap = new HashMap<>();
                        for (DataSnapshot eachAppointment : dataSnapshot.child(patientUsername).getChildren()) {
                            String dateTime = eachAppointment.getKey();
                            Map<String, String> elem = new HashMap<>();
                            //employeeName, clinicName, clinicAddress, bookedService, waitingTime, isCheckedIn
                            if (eachAppointment.hasChild("employeeName")) {
                                elem.put("employeeName", eachAppointment.child("employeeName").getValue(String.class));
                            } else {
                                liveDataAppointments.setValue(new Result.Error(new IOException("clinic invalid")));
                            }
                            if (eachAppointment.hasChild("clinicName")) {
                                elem.put("clinicName", eachAppointment.child("clinicName").getValue(String.class));
                            } else {
                                elem.put("clinicName", "");
                            }
                            if (eachAppointment.hasChild("clinicAddress")) {
                                elem.put("clinicAddress", eachAppointment.child("clinicAddress").getValue(String.class));
                            } else {
                                elem.put("clinicAddress", "");
                            }
                            if (eachAppointment.hasChild("bookedService")) {
                                elem.put("bookedService", eachAppointment.child("bookedService").getValue(String.class));
                            } else {
                                elem.put("bookedService", "");
                            }
                            if (eachAppointment.hasChild("waitingTime")) {
                                elem.put("waitingTime", eachAppointment.child("waitingTime").getValue(Long.class).toString());
                            } else {
                                elem.put("waitingTime", "");
                            }
                            if (eachAppointment.hasChild("isCheckedIn")) {
                                elem.put("isCheckedIn", eachAppointment.child("isCheckedIn").getValue(Boolean.class).toString());
                            } else {
                                elem.put("isCheckedIn", "false");
                            }
                            appointmentMap.put(dateTime, elem);
                        }
                        liveDataAppointments.setValue(new Result.Success<Map<String, Map<String, String>>>(appointmentMap));
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
    public LiveData<Result> isCheckedIn(final String patientUsername, final String dateTime) {
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
    public LiveData<Result> calculateWaitingTime(final String employeeUsername, final String dateTime) {
        final DatabaseReference databaseAppointments;
        final MutableLiveData<Result> liveDataAppointments = new MutableLiveData<>();
        try {
            databaseAppointments = FirebaseDatabase.getInstance().getReference("appointments");
            databaseAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int counter = 0;
                    for (DataSnapshot eachPatient : dataSnapshot.getChildren()) {
                        if (eachPatient.hasChild(dateTime) && employeeUsername.equals(eachPatient.child(dateTime).child("employeeName").getValue(String.class)))
                            counter++;
                    }
                    Integer waitingTimeInMins = 15 * counter;
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
                            fromClinic.child("rate").child("counter").setValue(1);
                            fromClinic.child("rate").child(commentNum).setValue(comment);
                            fromClinic.child("rate").child("score").setValue(score);
                            liveDataAppointments.setValue(new Result.Success(R.string.rated_appointment));
                        } else {
                            //this clinic has a score already, need count up and take average score
                            DatabaseReference fromRate = fromClinic.child("rate");
                            Integer counter = dataSnapshot.child(employeeName).child("rate").child("counter").getValue(Integer.class);
                            Float aveScore = dataSnapshot.child(employeeName).child("rate").child("score").getValue(Float.class);
                            aveScore = (Float) (aveScore * counter + score) / (++counter);
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
