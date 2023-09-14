package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticlesActivity extends AppCompatActivity {

    private String[][] health_details=
            {
                    {"Walking Daily", "", "", "", "CLick More Details"},
                    {"Home care of COVID-19", "", "", "", "CLick More Details"},
                    {"Stop Smoking", "", "", "", "Click More Details"},
                    {"Menstrual Cramps", "", "", "", "CLick More Details"},
                    {"Healthy Gut", "", "", "", "Click More Details"}
            };

    private int[] images = {
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5
    };

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles);

        btnBack=findViewById(R.id.buttonHABack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthArticlesActivity.this,HomeActivity.class));
            }
        });

        lst=findViewById(R.id.listViewHA);
        list = new ArrayList();
        for (int i=0;i<health_details. length;i++) {
            item = new HashMap<String, String>();
            item.put( "Line1",health_details[i][0]);
            item.put( "Line2",health_details[i][1]);
            item.put( "Line3",health_details[i][2]);
            item.put( "Line4",health_details[i][3]);
            item.put( "Line5","Cons Fees: "+health_details[i][4]+"/-");
            list.add( item );
        }

        sa = new SimpleAdapter ( this, list,
                R.layout.multi_lines,
                new String[]{"Line1", "Line2", "Line3", "Line4", "Line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent it =new Intent(HealthArticlesActivity.this,HealthArticlesDetailsActivity.class);
                it.putExtra( "text1", health_details[i][0]);
                it. putExtra( "text2", images[i]);
                startActivity(it);

            }
        });
    }
}