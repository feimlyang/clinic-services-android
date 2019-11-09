package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;

/**
 *      AdminAccountFragment is the fragment taking charge of account editing.
 *      It displays current accounts and enables user to delete them.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin
 */
public class AdminAccountFragment extends AppCompatActivity {
    private AdminAccountViewModel adminAccountViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        //Set View Model
        View root = inflater.inflate(R.layout.admin_fragment_account, container, false);
        adminAccountViewModel =
                ViewModelProviders.of(this).get(AdminAccountViewModel.class);

       // final TextView textView = root.findViewById(R.id.test_adminAccount);



        //Set Account List
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,
        )
        */
        return root;
    }// end of onCreateView
}
