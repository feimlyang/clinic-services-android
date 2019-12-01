package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.ui.clinicApp.navigation.AdminMainFragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AdminAccountDeleteFragment extends Fragment {

    private Button deleteButton;
    private Button returnButton;

    private EditText usernNameFilling;
    private AdminAccountViewModel adminAccountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        adminAccountViewModel = ViewModelProviders.of(this, new AdminAccountViewModelFactory()).get(AdminAccountViewModel.class);
        View root = inflater.inflate(R.layout.admin_fragment_deletesaccount, container, false);
        return root;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        usernNameFilling = getActivity().findViewById(R.id.enter_username);
        returnButton = getActivity().findViewById(R.id.btn_adminAccount_return);
        deleteButton = getActivity().findViewById(R.id.delete_user);
        deleteButton.setEnabled(false);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminAccountViewModel.deleteAccount(usernNameFilling.getText().toString());
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.admin_layout_deleteaccount, new AdminMainFragment());
                transaction.commit();
            }
        });
        adminAccountViewModel.getDeleteAccountLiveData().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Failure)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "account is deleted successfully",  Toast.LENGTH_SHORT).show();
                    usernNameFilling.setText("");
                }
            }
        });
        usernNameFilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                boolean isValid = adminAccountViewModel.isUserNameValid(usernNameFilling.getText().toString());
                if(!isValid)
                {
                    deleteButton.setEnabled(false);
                    usernNameFilling.setError(getString(R.string.invalid_usernameFormat));
                }
                else
                {
                    deleteButton.setEnabled(true);
                }
            }
        });
    }
}
