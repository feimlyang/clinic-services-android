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
    private TextView viewAddress;
    private TextView viewRating;
    private TextView viewWaitingTime;
    private TextView myDate;

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
    private Map<String, CheckBox> checkBoxMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        //Set View Model
        View root = inflater.inflate(R.layout.patient_fragment_select_serviceandtime, container, false);
        appointmentViewModel =
                ViewModelProviders.of(this).get(AppointmentViewModel.class);

        return root;
    }//end of onCreateView()


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set Buttons
        confirmButton = (Button) getActivity().findViewById(R.id.btn_Confirm);
        viewAppointment = (Button) getActivity().findViewById(R.id.btn_ViewAppointment);
        calendarView = (CalendarView) getActivity().findViewById(R.id.calendarView);
        viewClinicName = (TextView) getActivity().findViewById(R.id.textViewClinicNameInfo);
        viewAddress = (TextView) getActivity().findViewById(R.id.textViewAddressInfo);
        viewRating = (TextView) getActivity().findViewById(R.id.textViewRatingInfo);
        viewWaitingTime = (TextView) getActivity().findViewById(R.id.textViewWaitingTimeInfo);
        myDate = (TextView) getActivity().findViewById(R.id.textViewDate);
        spinnerService = (Spinner) getActivity().findViewById(R.id.spinnerService);

        //get info from Intent for selected clinic
        final String employeeName = getActivity().getIntent().getExtras().getString("employeeName");
        final String clinicName = getActivity().getIntent().getExtras().getString("clinicName");
        final String clinicAddress = getActivity().getIntent().getExtras().getString("clinicAddress");
        final String clinicRate = getActivity().getIntent().getExtras().getString(("clinicRate"));

        viewClinicName.setText(clinicName);
        viewAddress.setText(clinicAddress);
        viewRating.setText(clinicRate);

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
            }
        });

        for (final String hours : checkBoxMap.keySet()) {
            final CheckBox checkBox = checkBoxMap.get(hours);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String currentDate = String.valueOf(myDate.getText());
                    if (isChecked) {
                        for (String time : checkBoxMap.keySet()) {
                            if (!hours.equals(time)) {
                                checkBoxMap.get(time).setChecked(false);
                            } else {
                                checkBoxMap.get(time).setChecked(true);
                                selectionTimeSlots.put(currentDate, hours);
                            }
                        }
                    } else {
                        selectionTimeSlots.clear();
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
                    viewWaitingTime.setText(waitingTime.toString());
                }
            }
        });


        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addAppointment( String patientUsername, String dateTime,
                //                                            String employeeUsername, String clinicName, String clinicAddress,
                //                                String bookedService, int waitingTime
                String date = String.valueOf(myDate.getText());

                String dateTime = date + selectionTimeSlots.get(myDate);
                appointmentViewModel.addAppointment(helper.getCurrentUsername(), dateTime, employeeName,
                        clinicName, clinicAddress, String.valueOf(spinnerService.getSelectedItem()) );
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

        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subCategory_name = "subCategory_" + spinnerService.getItemAtPosition(position).toString();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
                int service = getResId(subCategory_name, R.array.class);
                if (service != -1) {
                    List<String> Lines = Arrays.asList(getResources().getStringArray(appointmentViewModel.getServiceSpinner()));
                    arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, Lines);
                }
                spinnerService.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//need to select a time slot
        appointmentViewModel.calculateWaitingTime(helper.getCurrentUsername(), String.valueOf(myDate.getText())+selectionTimeSlots.get(), employeeName);

    }


    private void cleanCheckBoxes() {
        for (String timeslot : checkBoxMap.keySet()) {
            checkBoxMap.get(timeslot).setChecked(false);
        }
    }

    private void populateHoursToCheckBoxes(String date) {
        if (!selectionTimeSlots.containsKey(date)) return;
        for (String hour : selectionTimeSlots.get(date)) {
            if (checkBoxMap.containsKey(hour)) {
                checkBoxMap.get(hour).setChecked(false);
            } else {
                checkBoxMap.get(hour).setEnabled(false);
            }
        }
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            return -1;
        }
    }
}