package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LabTestDetailsActivity extends AppCompatActivity {
    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnBack ,btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        tvPackageName=findViewById(R.id.textViewBMCartTitle);
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost);
        edDetails=findViewById(R.id.editTextBMDMultiLine);
        btnBack=findViewById(R.id.buttonBMDBack);
        btnAddToCart=findViewById(R.id.buttonBMDAddToCart);

        edDetails.setKeyListener(null);
        Intent intent=getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost "+intent.getStringExtra( "text5") +"/-");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestDetailsActivity.this,LabTestActivity.class));
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat (intent.getStringExtra( "text5"). toString());

                DataBase dataBase=new DataBase(LabTestDetailsActivity.this);

                if(dataBase.checkCart(username,product)==1){
                    Toast.makeText(LabTestDetailsActivity.this, "Product already added", Toast.LENGTH_SHORT).show();
                }else {
                    dataBase.addCart(username,product,price,"lab");
//                    Toast.makeText(LabTestDetailsActivity.this, "Record inserted to cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LabTestDetailsActivity.this,CartLabActivity.class));
                }
            }
        });
    }
}