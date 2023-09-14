package com.example.epp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button bt;
    Button b2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//Action listener for navigating to View result activity

        bt= (Button) findViewById(R.id.viewResult);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //Intent For Navigating to Viewresult Activity
                Intent i = new Intent(HomeActivity.this,ViewResult1.class);

                startActivity(i);
            }
        });

//Action listener for navigating to upload activity

        b2= (Button) findViewById(R.id.button3);

//b2.setVisibility(View.INVISIBLE);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                // Intent For Navigating to upload Activity
               Intent i2 = new Intent(HomeActivity.this,UploadActivity.class);


               startActivity(i2);
            }
        });

    }
}