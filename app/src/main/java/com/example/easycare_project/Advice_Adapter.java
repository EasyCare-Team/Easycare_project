package com.example.easycare_project;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycare_project.ui.BMIAdvice;
import com.example.easycare_project.ui.BPAdvice;
import com.example.easycare_project.ui.HeartRateAdvice;
import com.example.easycare_project.ui.Result.ResultFragment;
import com.example.easycare_project.ui.TempAdvice;

import java.util.List;

public class Advice_Adapter  extends RecyclerView.Adapter<Advice_Adapter.ViewHolder>{
    private List<AdviceListItem> listItems;
    private Context context;
    private FragmentManager fm;
    String title;
    public Advice_Adapter(List<AdviceListItem> listItems, Context context){
        this.listItems=listItems;
        this.context=context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
   return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final AdviceListItem listItem=listItems.get(position);
        holder.head.setText(listItem.getHead());
        holder.desc.setText(listItem.getDesc());
        holder.linearL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = listItem.getHead();
                //Toast.makeText(context, "you clicked " + listItem.getHead(), Toast.LENGTH_SHORT).show();

                if (position ==0) {
                    String url = "https://www.belmarrahealth.com/resting-heart-rate-chart-factors-influence-heart-rate-elderly/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);

//                    HeartRateAdvice result = new HeartRateAdvice();
//                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                    ((AppCompatActivity) context).getSupportActionBar().setTitle("Heart rate Advice");
//
//                    //Bundle args = new Bundle();
//                  //  args.putString("type", "HEART");
//                   // result.setArguments(args);
//                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.nav_host_fragment, result).addToBackStack(null);
//                    fragmentTransaction.commit();
                } else if (position ==1) {
                    String url = "https://www.healthline.com/health/fever-symptoms";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
//                    BPAdvice result = new BPAdvice();
//                    ((AppCompatActivity) context).getSupportActionBar().setTitle("Hypertension Advice");
//
//                    Bundle args = new Bundle();
//                    //args.putString("type", "HEART");
//                    result.setArguments(args);
//                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.nav_host_fragment, result).addToBackStack(null);
//                    fragmentTransaction.commit();


                }
                else if (position ==2) {
                    String url = "https://www.healthline.com/health/high-blood-pressure-hypertension/blood-pressure-reading-explained";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
//                    BMIAdvice result = new BMIAdvice();
//                    ((AppCompatActivity) context).getSupportActionBar().setTitle("BMI Advice");
//
//                    Bundle args = new Bundle();
//                   // args.putString("type", "HEART");
//                    result.setArguments(args);
//                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.nav_host_fragment, result).addToBackStack(null);
//                    fragmentTransaction.commit();


                }
                else if  (position ==3) {
                    String url = "https://www.medicalnewstoday.com/articles/323586";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
//                    TempAdvice result = new TempAdvice();
//                    ((AppCompatActivity) context).getSupportActionBar().setTitle("Temprature Advice");

//                    Bundle args = new Bundle();
//                   // args.putString("type", "HEART");
//                    result.setArguments(args);
//                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.nav_host_fragment, result).addToBackStack(null);
//                    fragmentTransaction.commit();


                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
public TextView head;
public TextView desc;
public LinearLayout linearL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head = (TextView)itemView.findViewById((R.id.heading));
            desc = (TextView)itemView.findViewById((R.id.description));
            linearL =(LinearLayout)itemView.findViewById((R.id.linearL));
        }
    }
}
