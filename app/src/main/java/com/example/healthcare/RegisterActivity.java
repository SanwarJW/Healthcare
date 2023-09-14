package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText edUsername, edPassword, edConfirm,edNumber;
    Button btn;
    TextView tv;
    public  DataBase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextBMBFullname);
        edPassword = findViewById(R.id.editTextBMBPincode);
        edNumber = findViewById(R.id.editTextNumber);
        edConfirm = findViewById(R.id.editTextBMBContact);
        btn = findViewById(R.id.buttonBMBBooking);
        tv = findViewById(R.id.textViewExistingUser);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String number = edNumber.getText().toString().trim();
                String password = edPassword.getText().toString();
                String confirm = edConfirm.getText().toString();

                try {
                    db=new DataBase(RegisterActivity.this);
                }catch (Exception e){
                    Toast.makeText((Context) RegisterActivity.this, (CharSequence) e, Toast.LENGTH_SHORT).show();
                }

                if (username.length() == 0 || number.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please fill ALL details", Toast.LENGTH_SHORT).show();
                } else {
                    if (isValidMobileNo(number)) {
                        if (password.compareTo(confirm) == 0) {
                            if (isValid(password)) {//isValid
                                if (isUserExistOrNot(number)){
                                db.register(username, number, password);
                                Toast.makeText(RegisterActivity.this, "Record Inserted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }else {
                                    Toast.makeText(RegisterActivity.this, "User is already exist", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(RegisterActivity.this, "Password must contain at Least 8 characters, having Letter, digit and special symbol", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Password and Confirm password didn't match", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(RegisterActivity.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean isUserExistOrNot(String num){
       String number=num.toString().trim();
        if (db.chickUserExistOrNot(number) == 1){
            return false;
        }else {
            return true;
        }

    }

    public static boolean isValid(String passwordhere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordhere.length() < 8) {
            return false;
        } else {



            for (int p = 0; p < passwordhere.length(); p++)
            {
                if (Character.isLetter(passwordhere.charAt(p)))
                {
                    f1 = 1;
                }
            }
            for (int r = 0; r < passwordhere.length(); r++)
            {
                if (Character.isDigit(passwordhere.charAt(r)))
                {
                    f2 = 1;
                }
            }
            for (int s = 0; s < passwordhere.length(); s++)
            {
                char c = passwordhere.charAt(s);
                if (c >= 33 && c <= 46 || c == 64)
                {
                    f3 = 1;
                }
                if (f1 == 1 && f2 == 1 && f3 == 1)
                {
                    return true;
                }

            }

        }
        return false;
    }

    public static boolean isValidMobileNo(String str) {
        Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher match = ptrn.matcher(str);
        return (match.find() && match.group().equals(str));
    }
}