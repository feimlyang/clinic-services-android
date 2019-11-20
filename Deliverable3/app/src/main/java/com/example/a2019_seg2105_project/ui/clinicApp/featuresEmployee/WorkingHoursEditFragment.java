package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
import java.time.Year;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee.EmployeeMainFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.String;

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
    private CheckBox time10;

    GlobalObjectManager helper = GlobalObjectManager.getInstance();

    ArrayList<String> selectionTime = new ArrayList<String>();

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

        time1 = (CheckBox)getActivity().findViewById(R.id.time1);
        time2 = (CheckBox)getActivity().findViewById(R.id.time2);
        time3 = (CheckBox)getActivity().findViewById(R.id.time3);
        time4 = (CheckBox)getActivity().findViewById(R.id.time4);
        time5 = (CheckBox)getActivity().findViewById(R.id.time5);
        time6 = (CheckBox)getActivity().findViewById(R.id.time6);
        time7 = (CheckBox)getActivity().findViewById(R.id.time7);
        time8 = (CheckBox)getActivity().findViewById(R.id.time8);
        time9 = (CheckBox)getActivity().findViewById(R.id.time9);
        //time10 = (CheckBox)getActivity().findViewById(R.id.time10);



        // Set onclick listeners
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                String dateSelected = year + "/" + (month +1) + "/" + day;
                myDate.setText(dateSelected);
//               serviceViewModel.getWorkingHours(helper.getCurrentUsername(), dateSelected);
            }
        });

        editButton.setEnabled(true);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time1.setEnabled(true);//to enable it
                time2.setEnabled(true);//to enable it
                time3.setEnabled(true);//to enable it
                time4.setEnabled(true);//to enable it
                time5.setEnabled(true);//to enable it
                time6.setEnabled(true);//to enable it
                time7.setEnabled(true);//to enable it
                time8.setEnabled(true);//to enable it
                time9.setEnabled(true);//to enable it
                time10.setEnabled(true);//to enable it

                Toast.makeText(getContext(),"Now you can edit!",Toast.LENGTH_SHORT).show();
            }
        });

        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceViewModel.setWorkingHours(helper.getCurrentUsername(), String.valueOf(myDate.getText()),selectionTime);
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

    }
    private void toServiceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.employee_layout_workingHours,fragment);
        transaction.commit();
    }
    public void selectTime(View view){
        boolean checked = ((CheckBox)view).isChecked();
        switch(view.getId()){
            case R.id.time1:
                if(checked){
                    selectionTime.add("08:00-09:00");
                }else{
                    selectionTime.remove("08:00-09:00");
                }
                break;
            case R.id.time2:
                if(checked){
                    selectionTime.add("09:00-10:00");
                }else{
                    selectionTime.remove("09:00-10:00");
                }
                break;
            case R.id.time3:
                if(checked){
                    selectionTime.add("10:00-11:00");
                }else{
                    selectionTime.remove("10:00-11:00");
                }
                break;
            case R.id.time4:
                if(checked){
                    selectionTime.add("11:00-12:00");
                }else{
                    selectionTime.remove("11:00-12:00");
                }
                break;
            case R.id.time5:
                if(checked){
                    selectionTime.add("12:00-13:00");
                }else{
                    selectionTime.remove("12:00-13:00");
                }
                break;
            case R.id.time6:
                if(checked){
                    selectionTime.add("13:00-14:00");
                }else{
                    selectionTime.remove("13:00-14:00");
                }
                break;
            case R.id.time7:
                if(checked){
                    selectionTime.add("14:00-15:00");
                }else{
                    selectionTime.remove("14:00-15:00");
                }
                break;
            case R.id.time8:
                if(checked){
                    selectionTime.add("15:00-16:00");
                }else{
                    selectionTime.remove("15:00-16:00");
                }
                break;
            case R.id.time9:
                if(checked){
                    selectionTime.add("16:00-17:00");
                }else{
                    selectionTime.remove("16:00-17:00");
                }
                break;
            /*
                case R.id.time10:
                if(checked){
                    selectionTime.add("17:00-18:00");
                }else{
                    selectionTime.remove("17:00-18:00");
                }
                break;

             */
        }
    }


}