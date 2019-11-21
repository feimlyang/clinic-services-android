package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import java.util.ArrayList;
import java.util.Map;

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
    String currentUsername = helper.getCurrentUsername();

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

        checkUHIP = getActivity().findViewById(R.id.checkUHIP);
        checkOHIP = getActivity().findViewById(R.id.checkOHIP);
        checkPrivateInsurance = getActivity().findViewById(R.id.checkPrivateInsurance);
        checkNoInsurance = getActivity().findViewById(R.id.checkNoInsurance);

        checkCash = getActivity().findViewById(R.id.checkCash);
        checkDebitCard = getActivity().findViewById(R.id.checkDebitCard);
        checkCreditCard = getActivity().findViewById(R.id.checkCreditCard);


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

        clinicViewModel.getProfileInfoData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Failure)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                }
                else if(result instanceof Result.Success)
                {
                    if(((Result.Success) result).getData() instanceof  Map)
                    {
                        Map attributes = (Map)((Result.Success) result).getData();
                        if(attributes.containsKey("clinicAddress"))
                        {
                            addressNameFilling.setText((String)attributes.get("clinicAddress"));
                            addressNameFilling.setEnabled(true);
                        }
                        else if(attributes.containsKey("clinicPhoneNum"))
                        {
                            phoneNumberFilling.setText((String)attributes.get("clinicPhoneNum"));
                            phoneNumberFilling.setEnabled(true);
                        }
                        else if(attributes.containsKey("clinicName"))
                        {
                            clinicNameFilling.setText((String)attributes.get("clinicName"));
                            clinicNameFilling.setEnabled(true);
                        }
                        else if(attributes.containsKey("insuranceType"))
                        {
                            ArrayList<Object> insuranceTypeUncast = (ArrayList<Object>)attributes.get("insuranceType");
                            for(Object item : insuranceTypeUncast)
                            {
                                String insuranceType = (String)item;
                                switch (insuranceType)
                                {
                                    case "OHIP":
                                        checkOHIP.setChecked(true);
                                        break;

                                    case "UHIP":
                                        checkUHIP.setChecked(true);
                                        break;
                                    case "Private Insurance":
                                        checkPrivateInsurance.setChecked(true);
                                        break;
                                    case "No Insurance":
                                        checkNoInsurance.setChecked(true);
                                        break;
                                    default:
                                }
                            }
                        }
                        else if(attributes.containsKey("paymentType"))
                        {
                            ArrayList<Object> paymentTypeUncast = (ArrayList<Object>)attributes.get("paymentType");
                            for(Object item : paymentTypeUncast)
                            {
                                String paymentType = (String)item;
                                switch (paymentType)
                                {
                                    case "Cash":
                                        checkCash.setChecked(true);
                                        break;
                                    case "Credit":
                                        checkCreditCard.setChecked(true);
                                        break;
                                    case "Debit":
                                        checkDebitCard.setChecked(true);
                                        break;
                                    default:
                                }
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        clinicViewModel.setClinicProfileData.observe(this, new Observer<Result>() {
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
                confirmButton.setEnabled(true);
            }
        });


        checkUHIP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked)
                {
                    selectionInsurance.add("UHIP");
                }
                else
                {
                    selectionInsurance.remove("UHIP");
                }
            }

        });
        checkOHIP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    selectionInsurance.add("OHIP");
                }
                else
                {
                    selectionInsurance.remove("OHIP");
                }

            }

        });
        checkPrivateInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    selectionInsurance.add("Private Insurance");
                }
                else
                {
                    selectionInsurance.remove("Private Insurance");
                }

            }

        });
        checkNoInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    selectionInsurance.add("No Insurance");
                    checkUHIP.setChecked(false);
                    checkOHIP.setChecked(false);
                    checkPrivateInsurance.setChecked(false);

                    checkUHIP.setEnabled(false);
                    checkOHIP.setEnabled(false);
                    checkPrivateInsurance.setEnabled(false);
                }
                else
                {
                    selectionInsurance.remove("No Insurance");

                    checkUHIP.setEnabled(true);
                    checkOHIP.setEnabled(true);
                    checkPrivateInsurance.setEnabled(true);
                }

            }

        });
        checkCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    selectionPayment.add("Cash");
                }
                else
                {
                    selectionPayment.remove("Cash");
                }
            }
        });
        checkDebitCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    selectionPayment.add("Debit");
                }
                else
                {
                    selectionPayment.remove("Debit");
                }

            }

        });
        checkCreditCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked)
                {
                    selectionPayment.add("Credit");
                }
                else
                {
                    selectionPayment.remove("Credit");
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


        phoneNumberFilling.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        clinicNameFilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                clinicViewModel.clinicNameValidator(s.toString());
            }
        });
        clinicViewModel.clinicFormState.observe(this, new Observer<ClinicFormState>() {
            @Override
            public void onChanged(ClinicFormState clinicFormState) {
                if(null == clinicFormState.getError()) return;
                clinicNameFilling.setError("Clinic name is invalid.");
            }
        });
        selectionInsurance.clear();
        selectionPayment.clear();
        clinicViewModel.getProfileInfo(currentUsername, "clinicAddress");
        clinicViewModel.getProfileInfo(currentUsername, "clinicPhoneNum");
        clinicViewModel.getProfileInfo(currentUsername, "clinicName");
        clinicViewModel.getProfileInfo(currentUsername, "insuranceType");
        clinicViewModel.getProfileInfo(currentUsername, "paymentType");
    }
}