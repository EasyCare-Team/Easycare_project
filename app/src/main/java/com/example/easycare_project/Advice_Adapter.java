package com.example.easycare_project;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Advice_Adapter  extends RecyclerView.Adapter<Advice_Adapter.ViewHolder>{
    private List<AdviceListItem> listItems;
    private Context context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AdviceListItem listItem=listItems.get(position);
        holder.head.setText(listItem.getHead());
        holder.desc.setText(listItem.getDesc());
        holder.linearL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked " + listItem.getHead(), Toast.LENGTH_SHORT).show();
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
