package com.example.speechynew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.speechynew.ui.home.HomeFragmentAll;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.speechynew.ui.dashboard.DashboardFragment;
import com.example.speechynew.ui.home.HomeFragment;
import com.example.speechynew.ui.notifications.NotificationsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ViewReportActivity extends AppCompatActivity {
int P;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Intent i = getIntent();
        P = i.getIntExtra("CH",1);
        Log.d("TEST_SHOW_DAY","wordminday: "+P);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        if (P==10){
            setContentView(R.layout.activity_view_report_all);

            BottomNavigationView navView = findViewById(R.id.nav_view_All);
            navView.setOnNavigationItemSelectedListener(navListener);

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home1, R.id.navigation_dashboard1, R.id.navigation_notifications)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_All);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
            Log.d("TEST_SHOW_DAY","wordminday: "+P+" เข้าขจริง");
        }

        if(P==1){
            setContentView(R.layout.activity_view_report);

            BottomNavigationView navView = findViewById(R.id.nav_view);
            navView.setOnNavigationItemSelectedListener(navListener);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
            Log.d("TEST_SHOW_DAY","wordminday: "+P+" เข้าขจริง");
        }



    }




    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedfragment = null;

                        switch (item.getItemId()){

                        case R.id.navigation_home: //day
                            selectedfragment = new HomeFragment();
                        case R.id.navigation_dashboard: //week
                            selectedfragment = new DashboardFragment();
                        case R.id.navigation_notifications: //month
                            selectedfragment = new NotificationsFragment();

                            break;

                    }


                    if(P==1){
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,selectedfragment).commit();
                    }
                    if (P==10) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_All, selectedfragment).commit();
                    }

                    return true;
                }
            };


}
