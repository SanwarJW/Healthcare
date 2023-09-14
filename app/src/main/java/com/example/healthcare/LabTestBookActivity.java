package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LabTestBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact, edpincode;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

        edname = findViewById(R.id.editTextBMBFullname) ;
        edaddress=findViewById(R.id.editTextNumber);
        edcontact = findViewById(R.id.editTextBMBContact);
        edpincode=findViewById(R.id.editTextBMBPincode);
        btnBooking = findViewById(R.id.buttonBMBBooking) ;

        Intent intent=getIntent();
        String[] price = intent.getStringExtra( "price").toString().split(java.util.regex.Pattern.quote(": "));
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra( "time");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edname.length() == 0 || edaddress.length() == 0 || edcontact.length() == 0 || edpincode.length() == 0) {
                    Toast.makeText(LabTestBookActivity.this, "Please fill ALL details", Toast.LENGTH_SHORT).show();
                } else {

                    if(isValidPinCode(edpincode.getText().toString().trim())){
                    if(isValidMobileNo(edcontact.getText().toString().trim())) {
                        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        String username = sharedpreferences.getString("username", "").toString();

                        DataBase db = new DataBase(LabTestBookActivity.this);
                        db.addOrder(username, edname.getText().toString(), edaddress.getText().toString(), edcontact.getText().toString(), Integer.parseInt(edpincode.getText().toString()), date.toString(), time.toString(), Float.parseFloat(price[1].toString()), "Lab");
                        db.removeCart(username, "lab");
                        Toast.makeText(LabTestBookActivity.this, "Your booking is done Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
                    }else {
                        Toast.makeText(LabTestBookActivity.this, "Invalid number", Toast.LENGTH_SHORT).show();
                    }
                    }else {
                        Toast.makeText(LabTestBookActivity.this, "Invalid pin code", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public static boolean isValidMobileNo(String str)
    {
        Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher match = ptrn.matcher(str);
        return (match.find() && match.group().equals(str));
    }

    public static boolean isValidPinCode(String pinCode)
    {
        String regex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
        Pattern p = Pattern.compile(regex);
        if (pinCode == null) {
            return false;
        }
        Matcher m = p.matcher(pinCode);
        return m.matches();
    }


}