package com.example.easycare_project.ui;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare_project.DatabaseHelper;
import com.example.easycare_project.R;

import static android.content.Context.MODE_PRIVATE;
import static com.example.easycare_project.Signin_Fragment.MY_PREFS_NAME;

public class BMIFragment extends Fragment {
    View view;
    private BmiViewModel mViewModel;

    public static BMIFragment newInstance() {
        return new BMIFragment();
    }
    Button btn_save, btn_measure;
    EditText et_height, et_weight;
    TextView et_show;
    String weight, height;
    double bmi;
    String uname;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    DatabaseHelper db;
    String bmi_str;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.measure_bmi, container, false);
        db = new DatabaseHelper(getContext());
        et_height = view.findViewById(R.id.height_et);
        et_weight = view.findViewById(R.id.weight_et);
        btn_save = view.findViewById(R.id.save_bmi);
        btn_measure = view.findViewById(R.id.measure_bmi);
        et_show = view.findViewById(R.id.show_bmi);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        uname = prefs.getString("uname", "No name defined");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addMeasurement(uname, bmi_str, "BMI");
                db.fetchMeasurement(uname);
                et_height.setText(" ");
                et_weight.setText(" ");
                et_show.setText(" ");
                long val =  db.addMeasurement(uname, bmi_str, "BMI");
                if (val >0){
                    Toast.makeText(getContext(), "Measurement successfully saved", Toast.LENGTH_SHORT).show();

                }
            }

        });

        btn_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height = et_height.getText().toString().trim();
                weight = et_weight.getText().toString().trim();
                bmi = Double.parseDouble(weight) / (Double.parseDouble(height) *  Double.parseDouble(height));
                bmi_str= String.format("%.4f", bmi);
                et_show.setText("Your BMI is: "+ bmi_str);
                et_height.setText(" ");
                et_weight.setText(" ");

            }

        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BmiViewModel.class);
        // TODO: Use the ViewModel
    }

}
