package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.ui.clinicApp.navigation.AdminMainFragment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ServiceEditFragment extends Fragment {
    private Button editButton;
    private Button returnButton;
    private Spinner categorySpinner;
    private Spinner subCategorySpinner;
    private Spinner roleSpinner;

    private EditText serviceNameFilling;
    private AdminServiceViewModel adminServiceViewModel;
    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            return -1;
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        adminServiceViewModel = ViewModelProviders.of(this, new AdminServiceViewModelFactory()).get(AdminServiceViewModel.class);
        View root = inflater.inflate(R.layout.admin_fragment_editservice, container, false);
        return root;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceNameFilling = getActivity().findViewById(R.id.enter_serviceName);
        categorySpinner = getActivity().findViewById(R.id.select_category);
        subCategorySpinner = getActivity().findViewById(R.id.select_subcategory);
        roleSpinner = getActivity().findViewById(R.id.select_rolePerforming);
        returnButton = getActivity().findViewById(R.id.btn_adminService_return);
        editButton = getActivity().findViewById(R.id.edit_service_button);
        editButton.setEnabled(false);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminServiceViewModel.editService(serviceNameFilling.getText().toString(),
                        String.valueOf(categorySpinner.getSelectedItem()),
                        String.valueOf(subCategorySpinner.getSelectedItem()),
                        String.valueOf(roleSpinner.getSelectedItem()));
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.layout_admin_editService, new AdminMainFragment());
                transaction.commit();
            }
        });
        adminServiceViewModel.getServicesEditLiveData().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Failure)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "service is updated successfully",  Toast.LENGTH_SHORT).show();
                    serviceNameFilling.setText("");
                    categorySpinner.setSelection(0);
                    subCategorySpinner.setSelection(0);
                    roleSpinner.setSelection(0);
                }
            }
        });
        serviceNameFilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                adminServiceViewModel.validateServiceInfo(serviceNameFilling.getText().toString());
            }
        });
        adminServiceViewModel.getServiceFormState().observe(this, new Observer<AdminServiceFormState>() {
            @Override
            public void onChanged(AdminServiceFormState adminServiceFormState) {
                if(null == adminServiceFormState.getError())
                {
                    editButton.setEnabled(true);
                    return;
                }
                if(R.string.service_name_invalid == adminServiceFormState.getError())
                {
                    editButton.setEnabled(false);
                    serviceNameFilling.setError(getString(adminServiceFormState.getError()));
                }
            }
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subCategory_name = "subCategory_" + categorySpinner.getItemAtPosition(position).toString();
                int family = getResId(subCategory_name, R.array.class);
                if(family != -1) {
                    List<String> Lines = Arrays.asList(getResources().getStringArray(family));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, Lines);
                    subCategorySpinner.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
