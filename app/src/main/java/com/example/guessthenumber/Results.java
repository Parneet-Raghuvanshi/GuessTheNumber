package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Results extends AppCompatActivity {

    RecyclerView recyclerView;
    Button playagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_results);
        String results = readSavedData();
        String[] strings = results.split("\n");
        recyclerView = findViewById(R.id.recyclerView);
        playagain = findViewById(R.id.bt_playagain);

        if (results.equals("") || results.isEmpty()){
            recyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.errorcase).setVisibility(View.VISIBLE);
        }
        else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            CustomAdapter customAdapter = new CustomAdapter(Results.this, strings);
            recyclerView.setAdapter(customAdapter);
        }

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Results.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String readSavedData() {
        String contents="";

        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),"savedresult.txt");
        if(!myFile.exists())
            return "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(myFile));
            int c;
            while ((c = br.read()) != -1) {
                contents=contents+(char)c;
            }
        }
        catch (IOException e) {
            return "";
        }
        return contents;
    }
}