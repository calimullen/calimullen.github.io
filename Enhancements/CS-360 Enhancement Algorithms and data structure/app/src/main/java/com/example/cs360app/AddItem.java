package com.example.cs360app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddItem extends AppCompatActivity {

    String username;
    EditText date, weight;
    Button addButton;
    DBHelper db;

    TextWatcher watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String dateT = date.getText().toString().trim();
            String weightT = weight.getText().toString().trim();
            if (!dateT.isEmpty() && !dateT.matches("") &&
                    !weightT.isEmpty() && !weightT.matches((""))) {
                addButton.setEnabled(true);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent passedIntent = getIntent();
        if (passedIntent.hasExtra("text_data")) {
            username = passedIntent.getStringExtra("text_data");
        }
        db = new DBHelper(this);

        date = findViewById(R.id.dateEditText);
        date.addTextChangedListener(watcher);

        weight = findViewById(R.id.weightEditText);
        weight.addTextChangedListener(watcher);

        addButton = findViewById(R.id.addDbBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightT = weight.getText().toString();
                String dateT = date.getText().toString();

                boolean check = db.insertWeight(username, weightT, dateT);
                if(check) {
                    Intent intent = new Intent(AddItem.this, WeightList.class);
                    intent.putExtra("text_data", username);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddItem.this, "Error Adding Weight", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}