package com.example.easycare_project.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easycare_project.AdviceListItem;
import com.example.easycare_project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BMIAdvice extends Fragment {

    private BmiadviceViewModel mViewModel;

    View view;
    TextView link_et, desc_et, header_et;
    public static BMIAdvice newInstance() {


        return new BMIAdvice();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        view= inflater.inflate(R.layout.measure_bmi, container, false);
        header_et = view.findViewById(R.id.header);
        link_et= view.findViewById(R.id.link);
        desc_et= view.findViewById(R.id.desc);
        addItemsFromJSON();
        return view;
    }

    private void addItemsFromJSON() {
        try {

            String jsonDataString = readJSONDataFromFile();
            JSONArray jsonArray = new JSONArray(jsonDataString);

           // for (int i=0; i<jsonArray.length(); ++i) {

                JSONObject itemObj = jsonArray.getJSONObject(0);

                String header = itemObj.getString("header");
                String desc = itemObj.getString("desc");
                String link = itemObj.getString("link");
                header_et.setText(header);
                link_et.setText(link);
                desc_et.setText(desc);


           // }

        } catch (JSONException | IOException e) {
            Log.d(TAG, "addItemsFromJSON: ", e);
        }
    }

    private String readJSONDataFromFile() throws IOException{

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {

            String jsonString = null;
            inputStream = getResources().openRawResource(R.raw.data);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));

            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);

}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BmiadviceViewModel.class);
        // TODO: Use the ViewModel
    }

}
