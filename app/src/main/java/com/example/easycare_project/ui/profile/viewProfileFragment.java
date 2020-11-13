package com.example.easycare_project.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.easycare_project.BuildConfig;
import com.example.easycare_project.DatabaseHelper;
import com.example.easycare_project.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.easycare_project.Signin_Fragment.MY_PREFS_NAME;

public class viewProfileFragment extends Fragment {
    private CircleImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    private ViewProfileViewModel mViewModel;
    ListView lv;
    ArrayList list = new ArrayList();
    ArrayAdapter adapter;
    private ProfileViewModel profileViewModel;
    String uname;
    Uri imageUri;
    Bitmap image;
    byte[] dataa;
    DatabaseHelper db;
    Button show;
    String profilearr[];


    public static viewProfileFragment newInstance() {
        return new viewProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.view_profile_fragment, container, false);
        db = new DatabaseHelper(getContext());

        lv = view.findViewById(R.id.Listview);
        show =view.findViewById(R.id.show);

        ProfileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        uname = prefs.getString("uname", "No name defined");

        image= db.getImage(uname);
        if(image != null)
            ProfileImage.setImageBitmap(image);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), PICK_IMAGE);
            }
        });

        showlist();

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( ((AppCompatActivity)getActivity()).getContentResolver(), imageUri);
                ProfileImage.setImageBitmap(bitmap);
                dataa = getBitmapAsByteArray(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //final TextView textView = root.findViewById(R.id.profile);
//        profileViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    public void showlist()
    {
        // list.clear();
        Cursor cursor = db.fetchProfile(uname);
        if(cursor.getCount() == 0)
        {
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        }
      //  profilearr = new String[cursor.getCount()];

        while(cursor.moveToNext())
        {
            
            list.add("User name         " + cursor.getString(1));
            list.add("Email                 " + cursor.getString(2));
           // list.add(cursor.getString(3));
        }
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