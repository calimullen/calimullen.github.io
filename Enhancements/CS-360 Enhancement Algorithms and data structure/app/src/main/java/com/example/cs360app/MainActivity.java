package com.example.cs360app;

import android.content.Intent;
import android.opengl.GLDebugHelper;
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

public class MainActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    Button loginButton;
    Button createButton;
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
                loginButton.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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

        loginButton = findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameT = user.getText().toString();
                String passwordT = user.getText().toString();

                boolean checkUser = db.getUser(usernameT, passwordT);
                if(checkUser) {
                    Intent intent = new Intent(MainActivity.this, WeightList.class);
                    intent.putExtra("text_data", usernameT);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createButton = findViewById(R.id.createBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });
    }
}