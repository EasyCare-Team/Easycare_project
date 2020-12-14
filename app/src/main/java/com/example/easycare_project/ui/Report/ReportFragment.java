package com.example.easycare_project.ui.Report;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.easycare_project.DatabaseHelper;
import com.example.easycare_project.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.easycare_project.Signin_Fragment.MY_PREFS_NAME;

public class ReportFragment extends Fragment {

    private ReportViewModel mViewModel;
    ListView lv;
    ArrayList list = new ArrayList();
    ArrayAdapter adapter;
    DatabaseHelper db;
    String uname;
    String type;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.report_fragment, container, false);
        lv = view.findViewById(R.id.Listview);
        db = new DatabaseHelper(getContext());

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        uname = prefs.getString("uname", "No name defined");
        if(getArguments() != null) {
            type = getArguments().getString("type");
        }
        showlist();
        return view;
    }
    public void showlist()
    {
        // list.clear();
        Cursor cursor = db.fetchMeasurement(uname);
        if(cursor.getCount() == 0)
        {
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        }
        //  profilearr = new String[cursor.getCount()];
        cursor.moveToFirst();
        while(cursor.moveToNext())
        {


            list.add(cursor.getString(1)+ " \t \t \t \t\t \t " + cursor.getString(2) + " \t \t\t \t\t \t" + cursor.getString(3));
             //list.add("hello" + cursor.getString(1));
        }
        cursor.close();
        adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lv);


    }
}

class Utility {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}





