package com.example.easycare_project.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.easycare_project.AdviceListItem;
import com.example.easycare_project.R;
import com.example.easycare_project.ui.AdviceFragment;
import com.example.easycare_project.ui.Measure.MeasureFragment;
import com.example.easycare_project.ui.Prediction.PredictionFragment;
import com.example.easycare_project.ui.Report.ReportFragment;

public class HomeFragment extends Fragment {
    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    public HomeFragment()
    {
        setRetainInstance(true);
    }
    private HomeViewModel homeViewModel;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
         root = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cardView1 = root.findViewById(R.id.card_view1);
        cardView2 = root.findViewById(R.id.card_view2);
        cardView3 = root.findViewById(R.id.card_view3);


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getContext(), Measure_page.class);
//                startActivity(i);
                MeasureFragment tool  = new MeasureFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Measure Vitals");

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, tool).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent i = new Intent(getContext(), Measure_page.class);
                startActivity(i);
*/
                ReportFragment report  = new ReportFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Report");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, report).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getContext(), Measure_page.class);
//                startActivity(i);
                AdviceFragment advice  = new AdviceFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Advice");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, advice).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }
}