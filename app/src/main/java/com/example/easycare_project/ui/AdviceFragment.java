package com.example.easycare_project.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easycare_project.AdviceListItem;
import com.example.easycare_project.Advice_Adapter;
import com.example.easycare_project.R;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdviceFragment extends Fragment {

    private AdviceViewModel mViewModel;
private RecyclerView recyclerView;
private RecyclerView.Adapter adapter;
private List<AdviceListItem> listItems;

    public static AdviceFragment newInstance() {
        return new AdviceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.advice_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       listItems = new ArrayList<>();
//       for(int i=0; i<=4; i++){
//           AdviceListItem listItem= new AdviceListItem("BP", "Hello BP");
//           listItems.add(listItem);
//        }

       adapter =new Advice_Adapter(listItems, getContext());
       recyclerView.setAdapter(adapter);
        addItemsFromJSON();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdviceViewModel.class);
        // TODO: Use the ViewModel
    }


    private void addItemsFromJSON() {
        try {

            String jsonDataString = readJSONDataFromFile();
            JSONArray jsonArray = new JSONArray(jsonDataString);

            for (int i=0; i<jsonArray.length(); ++i) {

                JSONObject itemObj = jsonArray.getJSONObject(i);

                String header = itemObj.getString("header");
                String desc = itemObj.getString("desc");

                AdviceListItem items = new AdviceListItem(header, desc);
                listItems.add(items);
            }

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
}