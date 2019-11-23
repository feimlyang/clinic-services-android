package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.AppointmentViewModel;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.PatientMainFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookAppointmentFragment extends Fragment {

    private AppointmentViewModel appointmentViewModel;
    private Button returnButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        appointmentViewModel = ViewModelProviders.of(this, new AppointmentModelFactory()).get(AppointmentViewModel.class);
        View root = inflater.inflate(R.layout.patient_fragment_bookappointment, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        returnButton = (Button) getActivity().findViewById(R.id.btn_Return);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.patient_layout_bookAppointment, new PatientMainFragment());
                transaction.commit();
            }
        });






    }
}
