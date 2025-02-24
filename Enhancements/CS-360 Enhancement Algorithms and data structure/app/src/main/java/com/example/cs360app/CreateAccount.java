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

public class CreateAccount extends AppCompatActivity {

    EditText user;
    EditText pass;
    Button createAccountButton;
    Button backButton;
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
            String name = user.getText().toString().trim();
            String password = pass.getText().toString().trim();
            if (!name.isEmpty() && !name.matches("") &&
                    !password.isEmpty() && !password.matches((""))) {
                createAccountButton.setEnabled(true);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBHelper(this);
        user = findViewById(R.id.usernameText);
        user.addTextChangedListener(watcher);
        pass = findViewById(R.id.passwordText);
        pass.addTextChangedListener(watcher);

        createAccountButton = findViewById(R.id.createAccountBtn);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameT = user.getText().toString();
                String passwordT = user.getText().toString();

                boolean checkUser = db.getUser(usernameT, passwordT);
                if(checkUser) {
                    Toast.makeText(CreateAccount.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
                else {

                    boolean insert = db.insertUser(usernameT, passwordT);
                    if(!insert)
                    {
                        Toast.makeText(CreateAccount.this, "Account not created", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                        Toast.makeText(CreateAccount.this, "Account created", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            }
        });
        backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
