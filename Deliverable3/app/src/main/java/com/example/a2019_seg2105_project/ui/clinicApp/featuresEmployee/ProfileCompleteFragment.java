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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee.EmployeeMainFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileCompleteFragment extends Fragment {


    private Button updateButton;
    private Button confirmButton;
    private Button returnButton;

    private EditText addressNameFilling;
    private EditText phoneNumberFilling;
    private EditText clinicNameFilling;

    private CheckBox checkUHIP;
    private CheckBox checkOHIP;
    private CheckBox checkPrivateInsurance;
    private CheckBox checkNoInsurance;
    ArrayList<String> selectionInsurance = new ArrayList<String>();
    ArrayList<String> selectionPayment = new ArrayList<String>();

    private ClinicViewModel clinicViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        clinicViewModel = ViewModelProviders.of(this, new ClinicViewModelFactory()).get(ClinicViewModel.class);
        View root = inflater.inflate(R.layout.employee_fragment_editprofile, container, false);
        return root;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addressNameFilling = getActivity().findViewById(R.id.editTextAddress);
        phoneNumberFilling = getActivity().findViewById(R.id.editTextPhoneNum);
        clinicNameFilling = getActivity().findViewById(R.id.editTextClinicName);

        returnButton = getActivity().findViewById(R.id.btn_Return);
        confirmButton = getActivity().findViewById(R.id.btn_confirmprofile);
        updateButton = getActivity().findViewById(R.id.btn_updateprofile);

//need to be modified!!!
        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinicViewModel.setClinicProfile(,clinicNameFilling.getText().toString(),
                        addressNameFilling.getText().toString(),
                        phoneNumberFilling.getText().toString(),
                        selectionInsurance,selectionPayment);
            }
        });
//need to be modified!!!
        updateButton.setEnabled(false);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinicViewModel.setClinicProfile(,clinicNameFilling.getText().toString(),
                        addressNameFilling.getText().toString(),
                        phoneNumberFilling.getText().toString(),
                        selectionInsurance,selectionPayment);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.layout_employee_editProfile, new EmployeeMainFragment());
                transaction.commit();
            }
        });

        addressNameFilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        phoneNumberFilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        clinicNameFilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
    public void selectInsurance(View view){
        boolean checked = ((CheckBox)view).isChecked();
        switch(view.getId()){
            case R.id.checkUHIP:
                if(checked){
                    selectionInsurance.add("UHIP");
                }else{
                    selectionInsurance.remove("UHIP");
                }
                break;
            case R.id.checkOHIP:
                if(checked){
                    selectionInsurance.add("OHIP");
                }else{
                    selectionInsurance.remove("OHIP");
                }
                break;
            case R.id.checkPrivateInsurance:
                if(checked){
                    selectionInsurance.add("Private Insurance");
                }else{
                    selectionInsurance.remove("Private Insurance");
                }
                break;
            case R.id.checkNoInsurance:
                if(checked){
                    selectionInsurance.add("No Insurance");
                }else{
                    selectionInsurance.remove("No Insurance");
                }
                break;
        }
    }
    public void selectPayment(View view){
        boolean checked = ((CheckBox)view).isChecked();
        switch(view.getId()){
            case R.id.checkCash:
                if(checked){
                    selectionPayment.add("Cash");
                }else{
                    selectionPayment.remove("Cash");
                }
                break;
            case R.id.checkDebitCard:
                if(checked){
                    selectionPayment.add("Debit Card");
                }else{
                    selectionPayment.remove("Debit Card");
                }
                break;
            case R.id.checkCreditCard:
                if(checked){
                    selectionPayment.add("Credit Card");
                }else{
                    selectionPayment.remove("Credit Card");
                }
                break;

        }
    }


}
