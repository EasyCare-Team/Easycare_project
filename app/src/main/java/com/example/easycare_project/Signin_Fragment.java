package com.example.easycare_project;


import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

public class Signin_Fragment extends Fragment {
    View view;
    public Signin_Fragment() {
    }

    Button btn_lregister, btn_llogin;
    EditText et_lusername, et_lpassword;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    DatabaseHelper databaseHelper;
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signin, container,false);
        databaseHelper = new DatabaseHelper(getContext());

        et_lusername = view.findViewById(R.id.username);
        et_lpassword = view.findViewById(R.id.password);

        btn_llogin = view.findViewById(R.id.login);
        btn_lregister = view.findViewById(R.id.register);


        btn_llogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_lusername.getText().toString();
                String password = et_lpassword.getText().toString();

                boolean checklogin = databaseHelper.checkUser(username, password);
                if(checklogin){
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("uname", username);
                    //editor.putString("password", password);
                    editor.apply();
                    Intent i = new Intent(getContext(), com.example.easycare_project.MainActivity.class);
                    //  i.putExtra(username, currentUsername);

                    startActivity(i);
                    et_lpassword.setText("");
                    et_lusername.setText("");
                }else{
                    Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
}
