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

    /*search for a walk in clinic by address, working hours, type of services
     * returns a arraylist of hashmap*/
    public LiveData<Result> searchService(final ArrayList datetime) {
        return null;

    }

    /*book an appointment, should provide all of the appointment info */
    public LiveData<Result> addAppointment(final String patientUsername, final String dateTime, final String bookedService,
                                           final Boolean isCheckedIn, final int waitingTime
    ) {
        return null;

    }

    /*get all appointmnet information for a paitent*/
    public LiveData<Result> getAppointmentInfo(final String patientUsername) {
        return null;

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
                            liveDataAppointments.setValue(new Result.Failure(R.string._alreayd_CheckedIn));
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
    public LiveData<Result> getWaitingTime(final String patientUsername, final String dateTime) {
        return null;

    }


    /*rate a service after check in, each rating include a score and a comment*/
    public LiveData<Result> rateAppointment(final String employeeName,
                                            final Double socre, final String comment) {
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
                            fromClinic.child("rate").child("score").setValue(socre);
                            liveDataAppointments.setValue(new Result.Success(R.string.rated_appointment));
                        } else {
//this clinic has a score already, need count up and take average score
                            DatabaseReference fromRate = fromClinic.child("rate");
                            Integer counter = dataSnapshot.child(employeeName).child("rate").child("counter").getValue(Integer.class);
                            Double aveScore = dataSnapshot.child(employeeName).child("rate").child("score").getValue(Double.class);
                            aveScore = (aveScore * counter + socre) / (++counter);
                            String commentNum = "comment" + String.valueOf(counter);
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
