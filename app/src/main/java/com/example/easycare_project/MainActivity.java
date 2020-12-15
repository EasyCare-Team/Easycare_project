package com.example.easycare_project;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.easycare_project.ui.About.AboutFragment;
import com.example.easycare_project.ui.Result.ResultFragment;
import com.example.easycare_project.ui.home.HomeFragment;
import com.example.easycare_project.ui.profile.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
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

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.easycare_project.Signin_Fragment.MY_PREFS_NAME;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    protected  DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    FragmentManager fragmentManager;
    Bitmap image;
    DatabaseHelper db;

    //ActionBarDrawerToggle mActionBarDrawerToggle;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        db = new DatabaseHelper(getApplicationContext());

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer = findViewById(R.id.drawer_layout);
        drawer.setDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        final CircleImageView imageViewUser =  (CircleImageView)navigationView.getHeaderView(0).findViewById(R.id.imageView);
        //imageViewUser.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        image= db.getImage(uname);
        if(image != null)
            imageViewUser.setImageBitmap(image);


        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        txtProfileName.setText(uname);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.i("MainActivity", "onOptionsItemSelected: Home Button Clicked");
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
                // fragment = new HomeFragment();
                changeFragment(new HomeFragment(), true);
                toolbar.setTitle("Home");


                return true;
            case R.id.nav_profile:
                // Toast.makeText(this, "User Profile", Toast.LENGTH_SHORT).show();
                //fragment = new ProfileFragment();
                changeFragment(new ProfileFragment(), true);

                toolbar.setTitle("Profile");
                return true;

            case R.id.nav_about:
                // Toast.makeText(this, "User Profile", Toast.LENGTH_SHORT).show();
                changeFragment(new AboutFragment(), true);
                toolbar.setTitle("About");

                return true;
            case R.id.logout:
            {
                SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
                editor.putString("password", "");
                editor.putString("email", "");
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                changeFragment(new Signin_Fragment(), true);

                finish();

                return true;
            }
            default:
                changeFragment(new HomeFragment(), true);
        }
//        FrameLayout fl = (FrameLayout) findViewById(R.id.nav_host_fragment);
//        fl.removeAllViews();

//        //fragmentManager.popBackStack();
//        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
//        fragmentManager.popBackStack();
//        item.setChecked(true);
//        // Set action bar title
//        setTitle(item.getTitle());
//        drawer.closeDrawer(GravityCompat.START);
//
        return false;
    }
    private void changeFragment(Fragment fragment, boolean needToAddBackstack) {
        FrameLayout fl = (FrameLayout) findViewById(R.id.nav_host_fragment);
        fl.removeAllViews();
        fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
        //fragmentManager.popBackStack();

        drawer.closeDrawer(GravityCompat.START);

        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (needToAddBackstack)
            mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        fragmentManager = getSupportFragmentManager();
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    // onBackPressed();


}
