package com.example.a2019_seg2105_project.ui.clinicApp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin.AdminServiceViewModel;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee.EmployeeMainFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.PatientMainFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.RateClinicFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.SelectServiceAndTimeFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.navigation.AdminMainFragment;
import com.example.a2019_seg2105_project.ui.clinicApp.navigation.AdminMainViewModel;


/**
*       MainActivity is the Main interface where most of the user-app interaction occurs.
 *      It will only be displayed if the user has successfully logged-in.
 *
 *      Note:
 *      1. MainActivity will use different HOME FRAGMENT depending on the logged-in user type
 *      2. home fragments stored in navigation package are home pages of different account type.
 *
 * @see com.example.a2019_seg2105_project.ui.clinicApp.navigation
 */
public class MainActivity extends AppCompatActivity {

    static private String userName;
    static private String accountType;
    private int layoutType;       // Determine which fragment should user be directed to
    @Override


    public void onCreate(Bundle savedInstanceState)
    {
        // 1. Initialize: load layout + navigation bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Determine which FRAGMENT to be displayed
        // depending on the user type
        userName = getIntent().getStringExtra(getString(R.string.loggedIn_userName));
        accountType = getIntent().getStringExtra(getString(R.string.loggedIn_userType));

        FragmentTransaction fragmentTransaction  = this.getSupportFragmentManager().beginTransaction();

        // Depending on user type, load different fragment
        if(accountType.equals("Administrator"))
        {
            AdminMainFragment adminHome = new AdminMainFragment();
            adminHome.setArguments(getIntent().getExtras());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.main_fragment, adminHome);
            fragmentTransaction.commit();
        }
        else if(accountType.equals("employee")){
            EmployeeMainFragment employeeHome = new EmployeeMainFragment();
            employeeHome.setArguments(getIntent().getExtras());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.main_fragment, employeeHome);
            fragmentTransaction.commit();
        }
        else
        {
            PatientMainFragment patientHome = new PatientMainFragment();
            patientHome.setArguments(getIntent().getExtras());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.main_fragment, patientHome);
            fragmentTransaction.commit();

        }

    }// end of onCreate()
}
