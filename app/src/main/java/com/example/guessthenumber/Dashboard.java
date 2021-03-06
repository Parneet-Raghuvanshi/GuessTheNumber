package com.example.guessthenumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Dashboard extends AppCompatActivity {

    private static final int WRITE_CODE = 100;
    EditText input;
    Button next;
    TextView tv_chnage_limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        
        if(ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Dashboard.this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    WRITE_CODE);
        }

        ((MyApplication) getApplication()).setStart_lim("1");
        ((MyApplication) getApplication()).setEnd_lim("100");

        next = findViewById(R.id.bt_start);
        input = findViewById(R.id.et_input);
        tv_chnage_limit = findViewById(R.id.change_limit_tv);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = validate();
                if (valid){
                    Intent intent = new Intent(Dashboard.this,MainActivity.class);
                    intent.putExtra("name",input.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }
                else {
                    Toasty.error(Dashboard.this, "Please try Again !" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_chnage_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,SetLimits.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String start = ((MyApplication) getApplication()).getStart_lim();
        String end = ((MyApplication) getApplication()).getEnd_lim();
        tv_chnage_limit.setText("Limits ( "+start+" - "+end+" )");
    }

    private boolean validate() {
        String str = input.getText().toString().trim();
        if (str.isEmpty()){
            input.setError("Could not be empty");
            input.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("Exit");
        builder.setIcon(R.drawable.ic_baseline_account_circle_24);
        builder.setMessage("Do You Want to Exit")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}