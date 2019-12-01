package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.model.Clinic;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee.ClinicViewModel;


import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectServiceAndTimeFragment extends Fragment {
    private AppointmentViewModel appointmentViewModel;
    private ClinicViewModel clinicViewModel;
    private ArrayList<String> serviceSelection = new ArrayList<>();
    private Spinner spinnerService;
    private Button confirmButton;
    private Button viewAppointment;
    private CalendarView calendarView;

    private TextView viewClinicName;
    private TextView myDate;
    private TextView waitingTimeInfo;

    private CheckBox time1;
    private CheckBox time2;
    private CheckBox time3;
    private CheckBox time4;
    private CheckBox time5;
    private CheckBox time6;
    private CheckBox time7;
    private CheckBox time8;
    private CheckBox time9;

    private GlobalObjectManager helper = GlobalObjectManager.getInstance();
    private Map<String, String> selectionTimeSlots = new HashMap<String, String>();
    private Map<String, Set<String>> avaliableTimeSlots = new HashMap<String, Set<String>>();

    private Map<String, CheckBox> checkBoxMap;
    private Map<String, String> attributes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        //Set View Model
        View root = inflater.inflate(R.layout.patient_fragment_select_serviceandtime, container, false);
        appointmentViewModel =
                ViewModelProviders.of(this, new AppointmentModelFactory()).get(AppointmentViewModel.class);

        return root;
    }//end of onCreateView()

    public SelectServiceAndTimeFragment(Map<String, String> myAttributesMap) {
        attributes = myAttributesMap;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set Buttons
        confirmButton = (Button) getActivity().findViewById(R.id.btn_Confirm);
        viewAppointment = (Button) getActivity().findViewById(R.id.btn_ViewAppointment);
        calendarView = (CalendarView) getActivity().findViewById(R.id.calendarView);
        viewClinicName = (TextView) getActivity().findViewById(R.id.textViewClinicNameInfo);
        waitingTimeInfo = getActivity().findViewById(R.id.textViewWaitingTimeInfo);

        myDate = (TextView) getActivity().findViewById(R.id.textViewDate);
        spinnerService = (Spinner) getActivity().findViewById(R.id.spinnerService);

        //get info from Intent for selected clinic
        final String employeeName = attributes.get("employeeName");
        final String clinicName = attributes.get("clinicName");
        final String clinicAddress = attributes.get("clinicAddress");
        final String clinicRate = attributes.get("clinicRate");

        viewClinicName.setText(clinicName);

        checkBoxMap = new HashMap<>();
        time1 = (CheckBox) getActivity().findViewById(R.id.time1);
        checkBoxMap.put("08:00-09:00", time1);
        time2 = (CheckBox) getActivity().findViewById(R.id.time2);
        checkBoxMap.put("09:00-10:00", time2);
        time3 = (CheckBox) getActivity().findViewById(R.id.time3);
        checkBoxMap.put("10:00-11:00", time3);
        time4 = (CheckBox) getActivity().findViewById(R.id.time4);
        checkBoxMap.put("11:00-12:00", time4);
        time5 = (CheckBox) getActivity().findViewById(R.id.time5);
        checkBoxMap.put("12:00-13:00", time5);
        time6 = (CheckBox) getActivity().findViewById(R.id.time6);
        checkBoxMap.put("13:00-14:00", time6);
        time7 = (CheckBox) getActivity().findViewById(R.id.time7);
        checkBoxMap.put("14:00-15:00", time7);
        time8 = (CheckBox) getActivity().findViewById(R.id.time8);
        checkBoxMap.put("15:00-16:00", time8);
        time9 = (CheckBox) getActivity().findViewById(R.id.time9);
        checkBoxMap.put("16:00-17:00", time9);
        calendarView.setMinDate(calendarView.getDate());
        myDate.setText(new SimpleDateFormat("yyyyMMdd").format(new Date(calendarView.getDate())));

        // Set onclick listeners
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                selectionTimeSlots.clear();
                String dateSelected = String.format("%04d%02d%02d", year, month + 1, day);
                myDate.setText(dateSelected);
                cleanCheckBoxes();
                appointmentViewModel.getWorkingHours(employeeName, dateSelected);
            }
        });

        appointmentViewModel.getWorkingHoursData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (null == result) return;
                if (result instanceof Result.Success) {
                    ArrayList<String> workingHours = ((Result.Success<ArrayList<String>>) result).getData();
                    String date = workingHours.get(0);
                    workingHours.remove(0);
                    Set<String> hourSet = new HashSet<>(workingHours);
                    avaliableTimeSlots.put(date, hourSet);
                    for (String hour : checkBoxMap.keySet()) {
                        checkBoxMap.get(hour).setEnabled(false);
                    }
                    disableUnavaliableHours(date);
                }
            }
        });


        for (final String hours : checkBoxMap.keySet()) {
            final CheckBox checkBox = checkBoxMap.get(hours);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String currentDate = String.valueOf(myDate.getText());
                    if (isChecked) {
                        cleanCheckBoxes();
                        for (String time : checkBoxMap.keySet()) {
                            if (hours.equals(time)) {
                                checkBoxMap.get(time).setChecked(true);
                                selectionTimeSlots.put(currentDate, time);
                                appointmentViewModel.calculateWaitingTime(employeeName, currentDate + time);
                                confirmButton.setEnabled(true);
                            }
                        }
                    } else {
                        confirmButton.setEnabled(false);
                        selectionTimeSlots.clear();
                        waitingTimeInfo.setText("Please select a time slot.");
                    }
                }
            });
        }

        appointmentViewModel.calculateWaitingTimeData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (null == result) return;
                if (result instanceof Result.Failure || result instanceof Result.Error) {
                    Toast.makeText(getContext(), "Failed to calculate waiting time", Toast.LENGTH_SHORT).show();
                } else {
                    Result.Success<Integer> waitingTimeResult = (Result.Success<Integer>) (result);
                    Integer waitingTime = waitingTimeResult.getData();
                    waitingTimeInfo.setText(waitingTime.toString());

                }
            }
        });

        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = String.valueOf(myDate.getText());
                String dateTime = date + selectionTimeSlots.get(date);
                appointmentViewModel.addAppointment(helper.getCurrentUsername(), dateTime, employeeName,
                        clinicName, clinicAddress, String.valueOf(spinnerService.getSelectedItem()));
            }
        });

        appointmentViewModel.addAppointmentData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (null == result) return;
                if (result instanceof Result.Failure) {
                    Toast.makeText(getContext(), (Integer) ((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Appointment is booked successfully", Toast.LENGTH_SHORT).show();
                    spinnerService.setSelection(0);
                }
            }
        });

        viewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.patient_layout_select_serviceAndTime, new PatientMainFragment());
                transaction.commit();
            }
        });
        appointmentViewModel.getServicesListLiveData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                ArrayList<String> availableServices = new ArrayList<String>();
                if (result instanceof Result.Failure || result instanceof Result.Error) {
                    Toast.makeText(getContext(), "Failed to list all services.", Toast.LENGTH_SHORT).show();
                } else {
                    Result.Success<ArrayList<String>> services;
                    try {
                        services = (Result.Success<ArrayList<String>>) (result);

                    } catch (Exception e) {
                        services = new Result.Success<ArrayList<String>>(availableServices);
                    }
                    availableServices = services.getData();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, availableServices);
                spinnerService.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        appointmentViewModel.getServicesOfClinic(employeeName);
    }

    private void cleanCheckBoxes() {
        for (String timeslot : checkBoxMap.keySet()) {
            checkBoxMap.get(timeslot).setChecked(false);
        }
    }

    private void disableUnavaliableHours(String date) {
        if (!avaliableTimeSlots.containsKey(date)) return;
        for (String hour : avaliableTimeSlots.get(date)) {
            if (checkBoxMap.containsKey(hour)) {
                checkBoxMap.get(hour).setEnabled(true);
            }
        }
    }
}
