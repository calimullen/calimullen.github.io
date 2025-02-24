package com.example.cs360app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateWeight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_weight);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent passedIntent = getIntent();
        String username = passedIntent.getStringExtra("username_data");
        String weight = passedIntent.getStringExtra("weight_data");
        String date = passedIntent.getStringExtra("date_data");

        EditText weightE = findViewById(R.id.weightEditText);
        EditText dateE = findViewById(R.id.dateEditText);

        weightE.setText(weight);
        dateE.setText(date);

        DBHelper db = new DBHelper(this);

        Button updateButton = findViewById(R.id.updateDbBtn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = db.updateWeight(username, weight, date, weightE.getText().toString(), dateE.getText().toString());
                if(!check)
                {
                    Toast.makeText(UpdateWeight.this, "Error", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(UpdateWeight.this, "Success", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(UpdateWeight.this, WeightList.class);
                intent.putExtra("text_data", username);
                startActivity(intent);
            }
        });
    }

}