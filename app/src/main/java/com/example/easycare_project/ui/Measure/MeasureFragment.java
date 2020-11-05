package com.example.easycare_project.ui.Measure;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.easycare_project.R;
import com.example.easycare_project.Result_page;
import com.example.easycare_project.ui.BMIFragment;
import com.example.easycare_project.ui.Result.ResultFragment;

import androidx.cardview.widget.CardView;
public class MeasureFragment extends Fragment {

    private MeasureViewModel toolsViewModel;
    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    CardView cardView4;
    public MeasureFragment()
    {
        setRetainInstance(true);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(MeasureViewModel.class);
        View root = inflater.inflate(R.layout.measure, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cardView1 = root.findViewById(R.id.card_view1);
        cardView2 = root.findViewById(R.id.card_view2);
        cardView3 = root.findViewById(R.id.card_view3);
        cardView4 = root.findViewById(R.id.card_view4);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultFragment result  = new ResultFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Measurement Result");

                Bundle args = new Bundle();
                args.putString("type", "HEART");
                result.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, result).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultFragment result  = new ResultFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Measurement Result");
                Bundle args = new Bundle();
                args.putString("type", "TEMP");
                result.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, result).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultFragment result  = new ResultFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Measurement Result");
                Bundle args = new Bundle();
                args.putString("type", "BP");
                result.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, result).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BMIFragment result  = new BMIFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Measurement Result");
                Bundle args = new Bundle();
                args.putString("type", "BMI");
                result.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, result).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
//            }
//        });
        return root;
    }
}