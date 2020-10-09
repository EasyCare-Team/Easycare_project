package com.example.easycare_project;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.easycare_project.ui.About.AboutFragment;
import com.example.easycare_project.ui.Result.ResultFragment;
import com.example.easycare_project.ui.home.HomeFragment;
import com.example.easycare_project.ui.profile.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.easycare_project.Signin_Fragment.MY_PREFS_NAME;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    protected  DrawerLayout drawer;
    FragmentManager fragmentManager;
    Toolbar toolbar;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        ActionBar actionBar;
//        actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable
//                = new ColorDrawable(Color.parseColor("#ffffff"));
//        actionBar.setBackgroundDrawable(colorDrawable);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Back clicked!",
//                        Toast.LENGTH_SHORT).show();
//            }
      //  });
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn= prefs.getBoolean("isLoggedIn", false);
        String uname = prefs.getString("uname", "No name defined");
//        if(isLoggedIn){
//            Signin_Fragment login  = new Signin_Fragment();
//            FragmentManager fragmentManager1 = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
//            fragmentTransaction1.replace(R.id.nav_host_fragment, login);
//            fragmentTransaction1.commit();
//                    finish();
//            return;
//        }
//
//        HomeFragment home  = new HomeFragment();
//        FragmentManager fragmentManager2 = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
//        fragmentTransaction2.replace(R.id.nav_host_fragment, home);
//        fragmentTransaction2.commit();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_about, R.id.nav_profile, R.id.logout
                )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
     //   NavigationUI.setupWithNavController(navigationView, navController);
        final ImageView imageViewUser =  (ImageView)navigationView.getHeaderView(0).findViewById(R.id.imageView);

        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        txtProfileName.setText(uname);


        }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            //Log.i(TAG, "onOptionsItemSelected: Home Button Clicked");
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                //Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment();
                toolbar.setTitle("Home");


                break;
            case R.id.nav_profile:
               // Toast.makeText(this, "User Profile", Toast.LENGTH_SHORT).show();
                fragment = new ProfileFragment();
                toolbar.setTitle("Profile");
                break;

            case R.id.nav_about:
                // Toast.makeText(this, "User Profile", Toast.LENGTH_SHORT).show();
                fragment  = new AboutFragment();
                toolbar.setTitle("About");

                break;
            case R.id.logout:
                {
                SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
                editor.putString("password", "");
                editor.putString("email", "");
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                fragment = new Signin_Fragment();

                finish();

                break;
            }
            default:
                fragment = new HomeFragment();
        }
        FrameLayout fl = (FrameLayout) findViewById(R.id.nav_host_fragment);
        fl.removeAllViews();
         fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
        fragmentManager.popBackStack();
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
