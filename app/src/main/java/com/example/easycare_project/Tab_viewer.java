package com.example.easycare_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.example.easycare_project.DatabaseHelper;
import com.google.android.material.tabs.TabLayout;

public class Tab_viewer extends AppCompatActivity {

    DatabaseHelper db;
    TabLayout tablayout;
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        tablayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewpager = (ViewPager) findViewById(R.id.viewpager_id);
        com.example.easycare_project.ViewPageAdapter adapter = new com.example.easycare_project.ViewPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Signup_Fragment(), "Sign up");
        adapter.AddFragment(new Signin_Fragment(), "Sign in");
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
//        adapter.AddFragment(new Measure_Fragment(), "Tab_viewer2");


    }
}
