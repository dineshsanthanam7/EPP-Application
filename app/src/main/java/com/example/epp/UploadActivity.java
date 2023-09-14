package com.example.epp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UploadActivity extends AppCompatActivity {
    Button bt;
    Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        bt= (Button) findViewById(R.id.button5);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //Intent For Navigating to Viewresult Activity
                Intent i = new Intent(UploadActivity.this,ThiruunivActivity.class);

                startActivity(i);
            }
        });

//Action listener for navigating to upload activity

        b2= (Button) findViewById(R.id.button7);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                //Intent For Navigating to upload Activity
                Intent i2 = new Intent(UploadActivity.this,AnnaunivActivity.class);


                startActivity(i2);
            }
        });

    }
}