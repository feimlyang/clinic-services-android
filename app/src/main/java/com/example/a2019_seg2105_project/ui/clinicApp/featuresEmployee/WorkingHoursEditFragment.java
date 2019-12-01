package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.String;
import java.util.Map;
import java.util.Set;

public class WorkingHoursEditFragment extends Fragment {

    private Button editButton;
    private Button confirmButton;
    private Button returnButton;
    private CalendarView calendarView;
    private TextView myDate;
    private ClinicViewModel serviceViewModel;

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
    private boolean editMode = false;
    private Map<String, Set<String>> selectionTimeSlots = new HashMap<String, Set<String>>();
    private Map<String, CheckBox> checkBoxMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        serviceViewModel = ViewModelProviders.of(this, new ClinicViewModelFactory()).get(ClinicViewModel.class);
        View root = inflater.inflate(R.layout.employee_fragment_editworkinghours, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        // Set Buttons
        editButton = (Button) getActivity().findViewById(R.id.btn_Complete);
        confirmButton = (Button) getActivity().findViewById(R.id.btn_confirm_workingHours);
        returnButton = (Button) getActivity().findViewById(R.id.btn_Return);
        calendarView = (CalendarView) getActivity().findViewById(R.id.calendarView);
        myDate = (TextView) getActivity().findViewById(R.id.textViewSelectedDate);
        checkBoxMap = new HashMap<>();

        time1 = (CheckBox)getActivity().findViewById(R.id.time1);
        checkBoxMap.put("08:00-09:00", time1);
        time2 = (CheckBox)getActivity().findViewById(R.id.time2);
        checkBoxMap.put("09:00-10:00", time2);
        time3 = (CheckBox)getActivity().findViewById(R.id.time3);
        checkBoxMap.put("10:00-11:00", time3);
        time4 = (CheckBox)getActivity().findViewById(R.id.time4);
        checkBoxMap.put("11:00-12:00", time4);
        time5 = (CheckBox)getActivity().findViewById(R.id.time5);
        checkBoxMap.put("12:00-13:00", time5);
        time6 = (CheckBox)getActivity().findViewById(R.id.time6);
        checkBoxMap.put("13:00-14:00", time6);
        time7 = (CheckBox)getActivity().findViewById(R.id.time7);
        checkBoxMap.put("14:00-15:00", time7);
        time8 = (CheckBox)getActivity().findViewById(R.id.time8);
        checkBoxMap.put("15:00-16:00", time8);
        time9 = (CheckBox)getActivity().findViewById(R.id.time9);
        checkBoxMap.put("16:00-17:00", time9);
        //time10 = (CheckBox)getActivity().findViewById(R.id.time10);
        myDate.setText(new SimpleDateFormat("yyyyMMdd").format(new Date(calendarView.getDate())));
        calendarView.setMinDate(calendarView.getDate());

        // Set onclick listeners
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                String dateSelected = String.format("%04d%02d%02d", year, month + 1,day);
                myDate.setText(dateSelected);
                cleanCheckBoxes();

                serviceViewModel.getWorkingHours(helper.getCurrentUsername(), dateSelected);
            }
        });
        serviceViewModel.getWorkingHoursData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Success)
                {
                    ArrayList<String> workingHours = ((Result.Success<ArrayList<String>>)result).getData();
                    String date = workingHours.get(0);
                    workingHours.remove(0);
                    Set<String> hourSet = new HashSet<>(workingHours);
                    selectionTimeSlots.put(date, hourSet);
                    populateHoursToCheckBoxes(date);
                }
            }
        });



        editButton.setEnabled(true);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode = true;
                for(String hours: checkBoxMap.keySet())
                {
                    final CheckBox checkBox = checkBoxMap.get(hours);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String currentDate = String.valueOf(myDate.getText());
                            if(isChecked) {
                                if (selectionTimeSlots.containsKey(currentDate)) {
                                    selectionTimeSlots.get(currentDate).add(String.valueOf(checkBox.getText()));
                                }
                                else
                                {
                                    Set<String> hourSet = new HashSet<>();
                                    hourSet.add(String.valueOf(checkBox.getText()));
                                    selectionTimeSlots.put(currentDate, hourSet);
                                }
                            }
                            else
                            {
                                if (selectionTimeSlots.containsKey(currentDate)) {
                                    selectionTimeSlots.get(currentDate).remove(String.valueOf(checkBox.getText()));
                                    if (selectionTimeSlots.get(currentDate).isEmpty())
                                        selectionTimeSlots.remove(currentDate);
                            }
                            }
                        }
                    });
                    checkBox.setEnabled(true);
                }
                confirmButton.setEnabled(true);
                Toast.makeText(getContext(),"Now you can edit!",Toast.LENGTH_SHORT).show();
            }
        });

        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = String.valueOf(myDate.getText());
                Set<String> hourSlots = selectionTimeSlots.get(date) == null ?  new HashSet<String>() : selectionTimeSlots.get(date);
                ArrayList<String> hoursArray = new ArrayList<>(hourSlots);
                serviceViewModel.setWorkingHours(helper.getCurrentUsername(), date, hoursArray);
            }
        });

        serviceViewModel.setWorkingHoursData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Success)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Success) result).getData(), Toast.LENGTH_SHORT).show();
                }
                else if(result instanceof Result.Failure)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.employee_layout_workingHours,new EmployeeMainFragment());
                transaction.commit();
            }
        });
        serviceViewModel.getWorkingHours(helper.getCurrentUsername(), String.valueOf(myDate.getText()));
    }

    private void cleanCheckBoxes()
    {
        for(String timeslot : checkBoxMap.keySet())
        {
            checkBoxMap.get(timeslot).setChecked(false);
        }
    }

    private void populateHoursToCheckBoxes(String date)
    {
        if(!selectionTimeSlots.containsKey(date)) return;
        for(String hour : selectionTimeSlots.get(date))
        {
            if(checkBoxMap.containsKey(hour))
            {
                checkBoxMap.get(hour).setChecked(true);
            }
        }
    }
}