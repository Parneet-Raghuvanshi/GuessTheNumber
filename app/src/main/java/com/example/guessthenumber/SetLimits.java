package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class SetLimits extends AppCompatActivity {

    Spinner start_spinner,end_spinner;
    String startfin,endfin;
    Button start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limits);

        start_spinner = findViewById(R.id.spinner_start);
        end_spinner = findViewById(R.id.spinner_end);
        start_btn = findViewById(R.id.bt_start);

        final ArrayList<String> start = new ArrayList<String>();
        start.add("1");
        ArrayAdapter<String> adapter_s = new ArrayAdapter<String>(this,R.layout.spinner_item, start);

        start_spinner.setAdapter(adapter_s);
        start_spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startfin = start_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        final ArrayList<String> end = new ArrayList<String>();
        end.add("100");end.add("200");end.add("500");end.add("1000");end.add("2000");end.add("5000");
        ArrayAdapter<String> adapter_e = new ArrayAdapter<String>(this,R.layout.spinner_item, end);

        end_spinner.setAdapter(adapter_e);
        end_spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endfin = end_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication) getApplication()).setStart_lim(startfin);
                ((MyApplication) getApplication()).setEnd_lim(endfin);
                Intent openMainActivity = new Intent(SetLimits.this, Dashboard.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMainActivity, 0);
            }
        });
    }
}