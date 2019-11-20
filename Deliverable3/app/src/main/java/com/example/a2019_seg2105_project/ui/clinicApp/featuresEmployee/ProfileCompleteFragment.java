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
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.view.View.OnClickListener;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee.EmployeeMainFragment;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.Toast;

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

    private CheckBox checkCash;
    private CheckBox checkDebitCard;
    private CheckBox checkCreditCard;
    ArrayList<String> selectionInsurance = new ArrayList<String>();
    ArrayList<String> selectionPayment = new ArrayList<String>();
    GlobalObjectManager helper = GlobalObjectManager.getInstance();

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

        checkUHIP = (CheckBox)getActivity().findViewById(R.id.checkUHIP);
        checkOHIP = (CheckBox)getActivity().findViewById(R.id.checkOHIP);
        checkPrivateInsurance = (CheckBox)getActivity().findViewById(R.id.checkPrivateInsurance);
        checkNoInsurance = (CheckBox)getActivity().findViewById(R.id.checkNoInsurance);

        checkCash = (CheckBox)getActivity().findViewById(R.id.checkCash);
        checkDebitCard = (CheckBox)getActivity().findViewById(R.id.checkDebitCard);
        checkCreditCard = (CheckBox)getActivity().findViewById(R.id.checkCreditCard);


        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinicViewModel.setClinicProfile(helper.getCurrentUsername(),clinicNameFilling.getText().toString(),
                        addressNameFilling.getText().toString(),
                        phoneNumberFilling.getText().toString(),
                        selectionInsurance,selectionPayment);
            }
        });

        clinicViewModel.getProfileInfoLiveData().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Failure)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Profile is added successfully",  Toast.LENGTH_SHORT).show();
                    clinicNameFilling.setText("");
                    addressNameFilling.setText("");
                    phoneNumberFilling.setText("");
//                    selectionInsurance.setSelection(0);

                }
            }
        });


        updateButton.setEnabled(true);
        updateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                addressNameFilling.setFocusableInTouchMode(true); //to enable it
                phoneNumberFilling.setFocusableInTouchMode(true); //to enable it
                clinicNameFilling.setFocusableInTouchMode(true); //to enable it
                checkUHIP.setEnabled(true);//to enable it
                checkOHIP.setEnabled(true);//to enable it
                checkPrivateInsurance.setEnabled(true);//to enable it
                checkNoInsurance.setEnabled(true);//to enable it
                checkCash.setEnabled(true);//to enable it
                checkDebitCard.setEnabled(true);//to enable it
                checkCreditCard.setEnabled(true);//to enable it

                Toast.makeText(getContext(),"Now you can edit!",Toast.LENGTH_SHORT).show();
            }
        });


        checkUHIP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    Toast.makeText(getContext(), "Android Checked", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Android Un-Checked", Toast.LENGTH_LONG).show();
                }

            }

        });


        checkOHIP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    Toast.makeText(getContext(), "Android Checked", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Android Un-Checked", Toast.LENGTH_LONG).show();
                }

            }

        });
        checkPrivateInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    Toast.makeText(getContext(), "Android Checked", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Android Un-Checked", Toast.LENGTH_LONG).show();
                }

            }

        });
        checkNoInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    Toast.makeText(getContext(), "Android Checked", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Android Un-Checked", Toast.LENGTH_LONG).show();
                }

            }

        });
        checkCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    Toast.makeText(getContext(), "Android Checked", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Android Un-Checked", Toast.LENGTH_LONG).show();
                }

            }

        });
        checkDebitCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    Toast.makeText(getContext(), "Android Checked", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Android Un-Checked", Toast.LENGTH_LONG).show();
                }

            }

        });
        checkCreditCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    Toast.makeText(getContext(), "Android Checked", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Android Un-Checked", Toast.LENGTH_LONG).show();
                }

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
                clinicViewModel.getProfileInfo(helper.getCurrentUsername(),addressNameFilling.getText().toString());
                if(clinicViewModel.getProfileInfoLiveData() != null){
                    addressNameFilling.setError("The address name already exists.");

                }
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
                clinicViewModel.getProfileInfo(helper.getCurrentUsername(),phoneNumberFilling.getText().toString());
                if(clinicViewModel.getProfileInfoLiveData() != null){
                    phoneNumberFilling.setError("The phone number already exists.");

                }

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
                clinicViewModel.getProfileInfo(helper.getCurrentUsername(),clinicNameFilling.getText().toString());
                if(clinicViewModel.getProfileInfoLiveData() != null){
                    clinicNameFilling.setError("The clinic name already exists.");

                }
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