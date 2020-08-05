package com.example.easycare_project.ui.Prediction;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easycare_project.R;

public class PredictionFragment extends Fragment {

    private PredictionViewModel mViewModel;

    public static PredictionFragment newInstance() {
        return new PredictionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.prediction_fragment, container, false);
    }


}
