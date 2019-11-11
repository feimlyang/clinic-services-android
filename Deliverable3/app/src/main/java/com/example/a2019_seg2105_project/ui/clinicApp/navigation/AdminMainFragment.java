package com.example.a2019_seg2105_project.ui.clinicApp.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.widget.Button;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.AdminAccountDeleteFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.AdminListServicesFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.ServiceAddFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.ServiceDeleteFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.ServiceEditFragment;

/**
 *       AdminMainFragment is the home fragment of administrator account type.
 *       It displays and implmenets the operations available for administrator.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin
 */


public class AdminMainFragment extends Fragment {
    private AdminMainViewModel adminMainViewModel;
    private Button serviceListButton;
    private Button serviceAddButton;
    private Button serviceEditButton;
    private Button serviceDeleteButton;
    private Button accountDeleteButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        //Set View Model
        View root = inflater.inflate(R.layout.admin_fragment_home, container, false);
       adminMainViewModel =
                ViewModelProviders.of(this).get(AdminMainViewModel.class);

        return root;
    }//end of onCreateView()

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        // Set Buttons
        serviceAddButton = (Button) getActivity().findViewById(R.id.btn_add_services);
        serviceEditButton = (Button) getActivity().findViewById(R.id.btn_edit_services);
        serviceDeleteButton = (Button) getActivity().findViewById(R.id.btn_delete_services);
        accountDeleteButton = (Button) getActivity().findViewById(R.id.btn_delete_accounts);
        serviceListButton = (Button) getActivity().findViewById(R.id.btn_list_services);
        // Set onclick listeners
        serviceAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toServiceFragment(new ServiceAddFragment());
                }
            });
        serviceEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.admin_layout_home,new ServiceEditFragment());
                transaction.commit();
            }
        });

        serviceDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.admin_layout_home,new ServiceDeleteFragment());
                    transaction.commit();
                }
            });
        accountDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.admin_layout_home,new AdminAccountDeleteFragment());
                transaction.commit();
            }
        });
        serviceListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.admin_layout_home,new AdminListServicesFragment());
                transaction.commit();
            }
        });
     }
    //Jump to related fragment
    private void toServiceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.admin_layout_home,fragment);
        transaction.commit();
    }
}
