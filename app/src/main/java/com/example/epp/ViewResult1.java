package com.example.epp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ViewResult1 extends AppCompatActivity {

    EditText ename,ereg;

    Button bconf;

    Spinner s;
    String [] sem_array={"Nov-Dec2020","April-May2021","Nov-Dec2021","April-May2022","Nov-Dec2022","April-May2023","Nov-Dec2023","April-May2024","Nov-Dec2024","April-May2025","Nov-Dec2025","April-May2026","Nov-Dec2026","April-May2027","Nov-Dec2027","April-May2028","Nov-Dec2028","April-May2029","Nov-Dec2029","April-May2030","Nov-Dec2030"};

    String name,reg,sem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result1);

        ename= (EditText) findViewById(R.id.editText);
        ereg= (EditText) findViewById(R.id.editText2);

        //esem= (EditText) findViewById(R.id.sem);
        bconf= (Button) findViewById(R.id.button);

        // String [] sem_array={"Nov-Dec2020","April-May2021","Nov-Dec2021","April-May2022","Nov-Dec2022","April-May2023","Nov-Dec2023","April-May2024","Nov-Dec2024","April-May2025","Nov-Dec2025","April-May2026","Nov-Dec2026","April-May2027","Nov-Dec2027","April-May2028","Nov-Dec2028","April-May2029","Nov-Dec2029","April-May2030","Nov-Dec2030"};



        s = findViewById(R.id.sem);
        s.setContentDescription("sem");

        ArrayAdapter adapter3= new
                ArrayAdapter(ViewResult1.this,android.R.layout.simple_spinner_item,sem_array);
        s.setAdapter(adapter3);
        bconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=ename.getText().toString();
                reg=ereg.getText().toString();
                sem=s.getSelectedItem().toString();



              //  Intent For Navigating to Second Activity
                Intent result = new Intent(ViewResult1.this,ResultActivity.class);
              //  For Passing the Values to Second Activity
                result.putExtra("name_key", name);
                result.putExtra("reg_key",reg);
               result.putExtra("sem_key", sem);
                startActivity(result);
            }
        });
    }
}