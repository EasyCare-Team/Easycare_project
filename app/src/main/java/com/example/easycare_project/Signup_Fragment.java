package com.example.easycare_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Signup_Fragment extends Fragment {
    View view;
    public Signup_Fragment() {

    }
    EditText e1, e2, e3, e4;
    Button b1;
    DatabaseHelper db;
    TabLayout tablayout;
    ViewPager viewpager;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup, container,false);
        db = new DatabaseHelper(getContext());
        e1 =  view.findViewById(R.id.email);
        e2 =  view.findViewById(R.id.username);
        e3 =  view.findViewById(R.id.pass);
        e4 =  view.findViewById(R.id.cpassword);
        b1 =  view.findViewById(R.id.register);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString().trim();
                String user = e2.getText().toString().trim();
                String pwd =  e3.getText().toString().trim();
                String cnf_pwd = e4.getText().toString().trim();

                if (pwd.equals(cnf_pwd)) {
                    long val = db.addUser(email, user, pwd);
                    if (val > 0){
                        Toast.makeText(getContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                        e1.setText("");
                        e2.setText("");
                        e3.setText("");
                        e4.setText("");

                    }
                    else {
                        Toast.makeText(getContext(), "Registration error", Toast.LENGTH_SHORT).show();
                        e1.setText("");
                        e2.setText("");
                        e3.setText("");
                        e4.setText("");
                    }
                }
                else {
                    Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                }
            }
        });

        return view;
    }

}

