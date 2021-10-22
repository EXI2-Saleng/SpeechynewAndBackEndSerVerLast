package com.example.speechynew.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.speechynew.MainActivity;
import com.example.speechynew.R;
import com.example.speechynew.ui.dashboard.DashboardFragment;
import com.example.speechynew.ui.home.HomeFragment;
import com.example.speechynew.ui.home.HomeFragmentAll;
import com.example.speechynew.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewReportAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_report_all);
        Toast.makeText(ViewReportAllActivity.this, "WHAT", Toast.LENGTH_LONG).show();

        BottomNavigationView navViewall = findViewById(R.id.nav_view_All);
        navViewall.setOnNavigationItemSelectedListener(navListenerAll);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home1, R.id.navigation_dashboard1, R.id.navigation_notifications)
                .build();
        NavController navControllerall = Navigation.findNavController(this, R.id.nav_host_fragment_All);
        NavigationUI.setupActionBarWithNavController(this, navControllerall, appBarConfiguration);
        NavigationUI.setupWithNavController(navViewall, navControllerall);


    }




    private BottomNavigationView.OnNavigationItemSelectedListener navListenerAll =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedfragment = null;

                    switch (item.getItemId()){

                        case R.id.navigation_home: //day
                            selectedfragment = new HomeFragmentAll();
                        case R.id.navigation_dashboard: //week
                            selectedfragment = new DashboardFragment();
                        case R.id.navigation_notifications: //month
                            selectedfragment = new NotificationsFragment();

                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_All,selectedfragment).commit();

                    return true;
                }
            };


}
