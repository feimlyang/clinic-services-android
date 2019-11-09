package com.example.a2019_seg2105_project.ui.clinicApp.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.widget.Button;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.AdminServiceFragment;

/**
 *       AdminMainFragment is the home fragment of administrator account type.
 *       It displays and implmenets the operations available for administrator.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin
 */


public class AdminMainFragment extends Fragment {
    private AdminMainViewModel adminMainViewModel;

        public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState)
        {
            //Set View Model
            View root = inflater.inflate(R.layout.admin_fragment_home, container, false);
           adminMainViewModel =
                    ViewModelProviders.of(this).get(AdminMainViewModel.class);

            return root;
        }//end of onCreateView()

        public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set Buttons
       final Button serviceEditButton = (Button) getActivity().findViewById(R.id.btn_edit_services);
       final Button accountEditButton = (Button) getActivity().findViewById(R.id.btn_edit_accounts);
        // Set onclick listeners
        serviceEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                toServiceFragment(new AdminServiceFragment());
                }
            });
        accountEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

         }//end of onActivityCreated()

    //Jump to related fragment
    private void toServiceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Replace fragment with current FRAGMENT
        transaction.addToBackStack(null);
        transaction.replace(R.id.admin_layout_home,fragment);
        transaction.commit();

    }

}
