package com.example.cs360app;

import android.content.Context;
import android.content.Intent;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    private Context context;
    private ArrayList weight_id, date_id;
    private String username;

    public MyAdapter(Context context, String username, ArrayList weight_id, ArrayList date_id) {
        this.context = context;
        this.username = username;
        this.weight_id = weight_id;
        this.date_id = date_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        // Hold the weight and date data
        holder.weight_id.setText(String.valueOf(weight_id.get(position)));
        holder.date_id.setText(String.valueOf(date_id.get(position)));

        String weight = String.valueOf(weight_id.get(position));
        String date = String.valueOf(date_id.get(position));
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateWeight.class);
                intent.putExtra("username_data", username);
                intent.putExtra("weight_data", weight);
                intent.putExtra("date_data", date);
                context.startActivity(intent);
            }
        });
        // Delete the entry from the database and the recycle view
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(context);
                boolean check = db.deleteWeight(weight,date);
                if(!check)
                {
                    Toast.makeText(context, "Error deleting", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public interface OnItemClickListener {
        void onUpdateClick(int position, View view);
    }
    @Override
    public int getItemCount() {
        return weight_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView weight_id, date_id;
        Button updateButton, deleteButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            weight_id = itemView.findViewById(R.id.textWeight);
            date_id = itemView.findViewById(R.id.textDate);
            updateButton = itemView.findViewById(R.id.updateBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }
}

