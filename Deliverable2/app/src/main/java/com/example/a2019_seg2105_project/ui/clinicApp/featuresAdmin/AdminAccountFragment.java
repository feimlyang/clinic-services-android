package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.UserRepository;
import com.example.a2019_seg2105_project.data.model.Service;

import java.util.ArrayList;

/**
 *      AdminAccountFragment is the fragment taking charge of account editing.
 *      It displays current accounts and enables user to delete them.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin
 */
public class AdminAccountFragment extends Fragment {
    private AdminAccountViewModel adminAccountViewModel;
    private ArrayList<String> existingUserName;

    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        // Set View Model
        View root = inflater.inflate(R.layout.admin_fragment_service, container, false);
        adminAccountViewModel =
                ViewModelProviders.of(this).get(AdminAccountViewModel.class);

        // Initialize Username
        UserRepository userRepo = UserRepository.getInstance();
        LiveData<Result> currentUserNames = userRepo.getUsername();

        existingUserName = null; // TODO qwq还是找字符串
        return root;
    }// end of onCreateView

    // Note: Put RecycleView in here to avoid getting NULL LayoutManager
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 1. Create RecycleView
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.admin_account_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // Set Adapter
        AdminAccountAdapter adapter = new AdminAccountAdapter(existingUserName);
        recyclerView.setAdapter(adapter);

        //2. Initialize Other Components
        final Button returnButton = (Button) getActivity().findViewById(R.id.btn_adminAccount_return);

        //2.1 Add Button Listeners
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to previous fragment
                getFragmentManager().popBackStack();
            }
        });
    }//end of onActivityCreated()

}
