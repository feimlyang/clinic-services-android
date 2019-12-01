package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.widget.Button;

import com.example.a2019_seg2105_project.R;


public class EmployeeMainFragment extends Fragment {

    private ClinicViewModel clinicViewModel;
    private EmployeeMainViewModel employeeMainViewModel;
    private Button serviceDeleteButton;
    private Button profileCompleteButton;
    private Button serviceAddButton;
    private Button workingHoursEditButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        //Set View Model
        View root = inflater.inflate(R.layout.employee_fragment_home, container, false);
        employeeMainViewModel =
                ViewModelProviders.of(this).get(EmployeeMainViewModel.class);

        return root;
    }//end of onCreateView()

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        // Set Buttons
        serviceAddButton = (Button) getActivity().findViewById(R.id.btn_add_services);
        serviceDeleteButton = (Button) getActivity().findViewById(R.id.btn_delete_services);
        profileCompleteButton = (Button) getActivity().findViewById(R.id.btn_edit_profile);
        workingHoursEditButton = (Button) getActivity().findViewById(R.id.btn_addedit_workingHours);
        // Set onclick listeners
        serviceAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.employee_layout_home,new ServiceAddFragment());
                transaction.commit();
            }
        });
        workingHoursEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.employee_layout_home,new WorkingHoursEditFragment());
                transaction.commit();
            }
        });

        serviceDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.employee_layout_home,new ServiceDeleteFragment());
                transaction.commit();
            }
        });
        profileCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.employee_layout_home,new ProfileCompleteFragment());
                transaction.commit();
            }
        });

    }
}
