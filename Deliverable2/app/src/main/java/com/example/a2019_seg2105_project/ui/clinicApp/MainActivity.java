package com.example.a2019_seg2105_project.ui.clinicApp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a2019_seg2105_project.R;
import com.google.android.material.navigation.NavigationView;

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
    private AppBarConfiguration mAppBarConfiguration;
    private int layoutType;       //Store type of fragment that will be used to form navigation bar
    @Override


    public void onCreate(Bundle savedInstanceState)
    {
        // 1. Initialize: load layout + navigation bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout  drawer = findViewById(R.id.main_drawer);

        // 2. Determine which HOME FRAGMENT will be displayed
        //Get account type & user name
        userName = getIntent().getStringExtra(getString(R.string.loggedIn_userName));
        accountType = getIntent().getStringExtra(getString(R.string.loggedIn_userType));

        //Log.d("MainActivity",accountType);

        // TODO: Determine which type of HOME FRAGMENT(last updated 2019/11/9)
        //  后端暂时不用管这里qwq！

        // 3. Initialize Navigation Bar
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_admin_home,
                R.id.nav_profile, R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }// end of onCreate()

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}//end of MainActivity
