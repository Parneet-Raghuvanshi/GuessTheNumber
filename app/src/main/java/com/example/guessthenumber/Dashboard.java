package com.example.guessthenumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Dashboard extends AppCompatActivity {

    EditText input;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        next = findViewById(R.id.bt_start);
        input = findViewById(R.id.et_input);

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