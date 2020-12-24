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
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    TextView tv_timer,tv_timeout,tv_completed,tv_toptext;
    public long time = 90000;
    public CountDownTimer countDownTimer;
    Button guess_btn,result_btn;
    EditText input,result;
    LottieAnimationView lottieAnimationView1,lottieAnimationView2,lottieAnimationView3;
    boolean timebool = true;
    public int try_count = 0;
    TextView tv_tryc;
    String end;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        name = getIntent().getStringExtra("name");

        end = ((MyApplication) getApplication()).getEnd_lim();
        tv_timer = findViewById(R.id.tv_timer);
        tv_toptext = findViewById(R.id.tv_toptext);
        tv_toptext.setText(name);
        input = findViewById(R.id.et_input);
        guess_btn = findViewById(R.id.bt_guess);
        result = findViewById(R.id.et_result);
        result_btn = findViewById(R.id.bt_results);
        tv_completed = findViewById(R.id.completed_text);
        tv_timeout = findViewById(R.id.timeout_text);
        lottieAnimationView1 = findViewById(R.id.lottie_animation_one);
        lottieAnimationView2 = findViewById(R.id.lottie_animation_two);
        lottieAnimationView3 = findViewById(R.id.lottie_animation_three);
        tv_tryc = findViewById(R.id.try_count_tv);


        Random r = new Random();
        int low = 1;
        int high = Integer.parseInt(end);
        final int resultcode = r.nextInt(high-low) + low;
        result.setText("Number  -  "+resultcode);
        Log.d("CODE ---- "," here it is   ---    "+resultcode);

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished;
                updatetimer();
            }

            @Override
            public void onFinish() {
                if (timebool){
                    tv_timer.setVisibility(View.INVISIBLE);
                    lottieAnimationView1.setVisibility(View.INVISIBLE);
                    lottieAnimationView2.setVisibility(View.VISIBLE);
                    guess_btn.setVisibility(View.INVISIBLE);
                    result_btn.setVisibility(View.VISIBLE);
                    result.setVisibility(View.VISIBLE);
                    input.setVisibility(View.INVISIBLE);
                    tv_timeout.setVisibility(View.VISIBLE);
                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    String older = readSavedData();
                    saveData(name,formattedDate,String.valueOf(try_count),"F",older);
                }
            }
        }.start();

        guess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = validate();
                if(valid){
                    String str = input.getText().toString().trim();
                    int input2 = Integer.parseInt(str);
                    if (input2 == resultcode){
                        try_count++;
                        tv_tryc.setText("Attempts - "+try_count);
                        Toasty.success(MainActivity.this,"Right Guess" , Toast.LENGTH_SHORT).show();
                        tv_timer.setVisibility(View.INVISIBLE);
                        lottieAnimationView1.setVisibility(View.INVISIBLE);
                        lottieAnimationView3.setVisibility(View.VISIBLE);
                        guess_btn.setVisibility(View.INVISIBLE);
                        result_btn.setVisibility(View.VISIBLE);
                        result.setVisibility(View.VISIBLE);
                        tv_completed.setVisibility(View.VISIBLE);
                        input.setVisibility(View.INVISIBLE);
                        timebool = false;
                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);
                        String older = readSavedData();
                        saveData(name,formattedDate,String.valueOf(try_count),"T",older);
                    }
                    else {
                        try_count++;
                        tv_tryc.setText("Attempts - "+try_count);
                        if (input2<resultcode){
                            Toasty.error(MainActivity.this,"Wrong Guess\nThink Bigger" , Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toasty.error(MainActivity.this,"Wrong Guess\nThink Smaller" , Toast.LENGTH_SHORT).show();
                        }
                        input.setText("");
                    }
                }
            }
        });

        result_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Results.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validate() {
        String in = input.getText().toString().trim();
        if (in.isEmpty()) {
            input.setError("Could not be empty");
            input.requestFocus();
            input.setText("");
            try_count++;
            return false;
        }
        if (in.length()>5){
            input.setError("Number is too large");
            input.requestFocus();
            input.setText("");
            try_count++;
            return false;
        }
        else if (Integer.parseInt(in)<1) {
            input.setError("Must be atleast 1");
            input.requestFocus();
            input.setText("");
            try_count++;
            return false;
        }
        else if (Integer.parseInt(in)>Integer.parseInt(end)){
            input.setError("Must be less then 101");
            input.requestFocus();
            input.setText("");
            try_count++;
            return false;
        }
        else {
            return true;
        }
    }

    private void updatetimer() {
        int minutes = (int) time / 60000;
        int seconds = (int) time % 60000 / 1000;

        String timelefttext;
        timelefttext = "" + minutes;
        timelefttext += ":";
        if (seconds < 10) timelefttext += "0";
        timelefttext += seconds;

        tv_timer.setText(timelefttext);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

    public void saveData(String name,String time,String attempts,String res,String older){
        try {
            File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),"savedresult.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            if (older.equals(""))
                myOutWriter.write(name +"#"+ time +"#"+ attempts +"#"+ res +"\n");
            else
                myOutWriter.write(name +"#"+ time +"#"+ attempts +"#"+ res +"\n"+older);
            myOutWriter.close();
            fOut.close();
            Log.d("Helper","Completed .......... ");
        } catch (Exception e) {
            e.printStackTrace();
        }
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