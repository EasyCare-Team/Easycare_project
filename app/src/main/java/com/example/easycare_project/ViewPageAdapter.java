package com.example.easycare_project;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.easycare_project.Signin_Fragment;
import com.example.easycare_project.Signup_Fragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentListTitles = new ArrayList<>();
    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new Signup_Fragment();
                break;
            case 1:
                fragment = new Signin_Fragment();
                break;

        }
        return fragment;
    }
    @Override
    public int getCount() {
        return FragmentListTitles.size();
    }
    public CharSequence getPageTitle (int position){
        switch (position){
            case 0:
                return "Signup";
            case 1:
                return "Signin";


        }
        return null;
    }

    public void AddFragment (Fragment fragment, String Title){
        fragmentList.add(fragment);
        FragmentListTitles.add(Title);
    }
}
