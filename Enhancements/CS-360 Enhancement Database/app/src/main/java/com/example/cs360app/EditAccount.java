package com.example.cs360app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import java.util.Objects;

public class EditAccount extends AppCompatActivity {

    EditText first;
    EditText last;
    EditText email;
    EditText user;
    EditText newPass;
    EditText oldPass;
    String emailOld;
    String userOld;
    String passOld;
    Button saveButton;
    Button backButton;
    DBHelper db;

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String firstName = first.getText().toString().trim();
            String lastName = last.getText().toString().trim();
            String emailT = email.getText().toString().trim();
            String name = user.getText().toString().trim();
            String password = oldPass.getText().toString().trim();
            if (!firstName.isEmpty() && !firstName.matches("") &&
                    !lastName.isEmpty() && !lastName.matches(("")) &&
                    !emailT.isEmpty() && !emailT.matches("") &&
                    !name.isEmpty() && !name.matches("") &&
                    !password.isEmpty() && !password.matches("")) {
                saveButton.setEnabled(true);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent passedIntent = getIntent();
        if (passedIntent.hasExtra("text_data")) {
            userOld = passedIntent.getStringExtra("text_data");
        }
        db = new DBHelper(this);
        first = findViewById(R.id.firstNameText);
        first.addTextChangedListener(watcher);
        last = findViewById(R.id.lastNameText);
        last.addTextChangedListener(watcher);
        email = findViewById(R.id.emailAddressText);
        email.addTextChangedListener(watcher);
        user = findViewById(R.id.usernameText);
        user.addTextChangedListener(watcher);
        oldPass = findViewById(R.id.oldPasswordText);
        oldPass.addTextChangedListener(watcher);
        newPass = findViewById(R.id.newPasswordText);
        newPass.addTextChangedListener(watcher);

        displayUserInfo();

        saveButton = findViewById(R.id.saveAccountBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameT = first.getText().toString();
                String lastNameT = last.getText().toString();
                String emailT = email.getText().toString();
                String usernameT = user.getText().toString();
                String oldPasswordT = oldPass.getText().toString();
                String newPasswordT = newPass.getText().toString();

                if (!Objects.equals(userOld, usernameT) || !Objects.equals(emailOld, emailT)) {
                    boolean checkEmail = db.getEmail(emailT);
                    boolean checkUsername = db.getUsername(usernameT);
                    if (checkEmail || checkUsername) {
                        Toast.makeText(EditAccount.this, "Username/Email already in use", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                boolean checkUser = db.getUser(userOld, oldPasswordT);
                if (checkUser) {
                    boolean update = false;
                    if (!newPasswordT.isEmpty() && !newPasswordT.matches("")) {
                        update = db.updateUserInfo(userOld, oldPasswordT,
                                firstNameT, lastNameT, emailT, usernameT, newPasswordT);
                    } else {
                        update = db.updateUserInfo(userOld, oldPasswordT,
                                firstNameT, lastNameT, emailT, usernameT, oldPasswordT);
                    }
                    if (!update) {
                        Toast.makeText(EditAccount.this, "Account not updated", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("Userprefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("new_username", usernameT);
                        editor.apply();
                        finish();                   }
                } else {
                    Toast.makeText(EditAccount.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("Userprefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("new_username", userOld);
                editor.apply();
                finish();
            }
        });
    }

    private void displayUserInfo() {
        Cursor cursor = db.getUserInfo(userOld);
        if(cursor != null && cursor.moveToFirst()) {
            String firstOld = cursor.getString(0);
            String lastOld = cursor.getString(1);
            emailOld = cursor.getString(2);
            userOld = cursor.getString(3);
            passOld = cursor.getString(4);

            first.setText(firstOld);
            last.setText(lastOld);
            email.setText(emailOld);
            user.setText(userOld);

            cursor.close();
        }
    }
}