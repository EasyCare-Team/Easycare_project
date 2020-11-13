package com.example.easycare_project.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.easycare_project.DatabaseHelper;
import com.example.easycare_project.R;
import com.example.easycare_project.Result_page;
import com.example.easycare_project.ui.Report.ReportFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.easycare_project.Signin_Fragment.MY_PREFS_NAME;

public class ProfileFragment extends Fragment {
    private CircleImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    EditText e1, e2, e3, e4;
    private ProfileViewModel profileViewModel;
    String gender;
    String email;
    String weight;
    String height;
    String dob;
    String uname;
    DatabaseHelper db;
    Button save, show;
    RadioButton male, female;
    Bitmap image;
    byte[] dataa;
    ListView lv;
    ArrayList list = new ArrayList();
    ArrayAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        db = new DatabaseHelper(getContext());
        save =view.findViewById(R.id.save);
        show =view.findViewById(R.id.show);

        e1 =  view.findViewById(R.id.emailp);
        e2 =  view.findViewById(R.id.height);
        e3 =  view.findViewById(R.id.weight);
        e4 =  view.findViewById(R.id.dob);
         email = e1.getText().toString().trim();
         height =e2.getText().toString().trim();
         dob = e4.getText().toString().trim();
         weight= e3.getText().toString().trim();
         male = view.findViewById(R.id.radiomale);
        lv = view.findViewById(R.id.Listview);

        male.setChecked(true);
       // String gender = "";
        ProfileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         uname = prefs.getString("uname", "No name defined");

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Check which radio button was clicked
                switch(checkedId) {
                    case R.id.radiomale:
                        Log.d("chk", "id" + checkedId);
                            gender = "male";
                        break;
                    case R.id.radiofemale:

                            gender = "female";
                        break;
                }

            }
        });

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int res=  db.updateProfile(uname, dob, gender, height, weight, dataa);
              if(res >0){
                  Toast.makeText(getContext(), "Profile successfully updated", Toast.LENGTH_SHORT).show();
                  e1.setText("");
                  e2.setText("");
                  e3.setText("");
                  e4.setText("");
                  male.setChecked(true);

              }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfileFragment report  = new viewProfileFragment();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("View Profile");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, report).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


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
                while(cursor.moveToNext())
                {
                    list.add(cursor.getString(0));
                    list.add(cursor.getString(1));
                    list.add(cursor.getString(2));
                }
                adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,list);
                lv.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lv);


    }
}



