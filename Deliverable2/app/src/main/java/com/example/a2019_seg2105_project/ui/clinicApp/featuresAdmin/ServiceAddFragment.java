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
import android.widget.ListAdapter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *       AdminServiceFragment is the fragment of service editting page.
*        It uses a RecycleView to view / update(create/delete) existing services.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin
 */

public class ServiceAddFragment extends Fragment {

    private Button addButton;
    private Button returnButton;
    private Spinner categorySpinner;
    private Spinner subCategorySpinner;
    private Spinner roleSpinner;

    private EditText serviceNameFilling;
    private AdminServiceViewModel addServiceViewModel;
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
        addServiceViewModel = ViewModelProviders.of(this, new AdminServiceViewModelFactory()).get(AdminServiceViewModel.class);
        View root = inflater.inflate(R.layout.admin_fragment_addservice, container, false);
        return root;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceNameFilling = getActivity().findViewById(R.id.enter_serviceName);
        categorySpinner = getActivity().findViewById(R.id.select_category);
        subCategorySpinner = getActivity().findViewById(R.id.select_subcategory);
        roleSpinner = getActivity().findViewById(R.id.select_rolePerforming);
        returnButton = getActivity().findViewById(R.id.btn_adminService_return);
        addButton = getActivity().findViewById(R.id.add_service_button);
        addButton.setEnabled(false);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addServiceViewModel.addService(serviceNameFilling.getText().toString(),
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
                transaction.replace(R.id.layout_admin_addService, new AdminMainFragment());
                transaction.commit();
            }
        });
        addServiceViewModel.getServicesAddLiveData().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Failure)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "service is added successfully",  Toast.LENGTH_SHORT).show();
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
                addServiceViewModel.validateServiceInfo(serviceNameFilling.getText().toString());
            }
        });
        addServiceViewModel.getServiceFormState().observe(this, new Observer<AdminServiceFormState>() {
            @Override
            public void onChanged(AdminServiceFormState adminServiceFormState) {
                if(null == adminServiceFormState.getError())
                {
                    addButton.setEnabled(true);
                    return;
                }
                if(R.string.service_name_invalid == adminServiceFormState.getError())
                {
                    addButton.setEnabled(false);
                    serviceNameFilling.setError(getString(adminServiceFormState.getError()));
                }
            }
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subCategory_name = "subCategory_" + categorySpinner.getItemAtPosition(position).toString();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
                int family = getResId(subCategory_name, R.array.class);
                if(family != -1) {
                    List<String> Lines = Arrays.asList(getResources().getStringArray(family));
                    arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, Lines);
                }
                subCategorySpinner.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
