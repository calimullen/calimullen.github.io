package com.example.cs360app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeightList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> weights, dates;
    MyAdapter adapter;
    DBHelper db;

    String usernameT;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent passedIntent = getIntent();
        if (passedIntent.hasExtra("text_data")) {
            usernameT = passedIntent.getStringExtra("text_data");
        }
        db = new DBHelper(this);
        weights = new ArrayList<>();
        dates = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, usernameT, weights, dates);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

        addButton = findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeightList.this, AddItem.class);
                intent.putExtra("text_data", usernameT);
                startActivity(intent);
            }
        });
    }

    private void displayData() {
        Cursor cursor = db.getWeights(usernameT);
        if (cursor.getCount() <= 0) {
            Toast.makeText(this, "No Entry", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                weights.add(cursor.getString(2));
                dates.add(cursor.getString(3));
            }
        }
    }
}