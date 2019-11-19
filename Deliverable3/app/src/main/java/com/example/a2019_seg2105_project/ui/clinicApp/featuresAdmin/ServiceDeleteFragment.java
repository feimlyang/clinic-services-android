package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ServiceDeleteFragment extends Fragment {
    private Button deleteButton;
    private Button returnButton;
    private EditText serviceNameFilling;
    private AdminServiceViewModel adminServiceViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        adminServiceViewModel = ViewModelProviders.of(this, new AdminServiceViewModelFactory()).get(AdminServiceViewModel.class);
        View root = inflater.inflate(R.layout.admin_fragment_deleteservice, container, false);
        return root;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceNameFilling = getActivity().findViewById(R.id.enter_serviceName);
        returnButton = getActivity().findViewById(R.id.btn_adminService_return);
        deleteButton = getActivity().findViewById(R.id.delete_services);
        deleteButton.setEnabled(false);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminServiceViewModel.deleteService(serviceNameFilling.getText().toString());
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.admin_layout_deleteservice, new AdminMainFragment());
                transaction.commit();
            }
        });
        adminServiceViewModel.getServicesDeleteLiveData().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Failure)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "service is deleted successfully",  Toast.LENGTH_SHORT).show();
                    serviceNameFilling.setText("");
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
                    deleteButton.setEnabled(true);
                    return;
                }
                deleteButton.setEnabled(false);
                serviceNameFilling.setError(getString(adminServiceFormState.getError()));
                return;
            }
        });
    }
}