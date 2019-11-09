package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.AdminServiceViewModel;
import com.example.a2019_seg2105_project.data.model.Service;

import java.util.ArrayList;
/**
 *       AdminServiceFragment is the fragment of service editting page.
*        It uses a RecycleView to view / update(create/delete) existing services.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin
 */

public class AdminServiceFragment extends Fragment {

    private AdminServiceViewModel adminServiceViewModel;
    private ArrayList<String> existingCategory;
    private RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        // Set View Model
        View root = inflater.inflate(R.layout.admin_fragment_service, container, false);
        adminServiceViewModel =
                ViewModelProviders.of(this).get(AdminServiceViewModel.class);

        // Initialize Service related data
        Service currentService = new Service(); // Initialize Data
        existingCategory = new ArrayList<String>(currentService.getCategoryList());


        return root;
    }// end of onCreateView

    // Note: Put RecycleView in here to avoid getting NULL LayoutManager
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 1. Create RecycleView
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.admin_service_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // Set Adapter
        AdminServiceAdapter adapter = new AdminServiceAdapter(existingCategory);
        recyclerView.setAdapter(adapter);

        //2. Initialize Other Components
        final Button serviceAddButton = (Button) getActivity().findViewById(R.id.btn_admin_add_service);
        final Button returnButton = (Button) getActivity().findViewById(R.id.btn_adminService_return);

        //2.1 Add Button Listeners
        serviceAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On clicking add service, display dialog.

            }
        });
       returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to previous fragment
                getFragmentManager().popBackStack();
            }
        });

    }
}
