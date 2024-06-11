package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Userregisteration extends AppCompatActivity implements JsonResponse{
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    String fname,lname,phone,email,place,user,pass,gender,pincode,house,dob;
    Button b1;
    RadioButton r1,r2;
//    private DatePickerDialog fromDatePickerDialog;
//
//    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_userregisteration);

//        startService(new Intent(getApplicationContext(),LocationService.class));
        e1=(EditText)findViewById(R.id.etfname);
        e2=(EditText)findViewById(R.id.etlname);
        e3=(EditText)findViewById(R.id.etphone);
        e4=(EditText)findViewById(R.id.etemail);
        e5=(EditText)findViewById(R.id.etplace);
        e6=(EditText)findViewById(R.id.etuser);
        e7=(EditText)findViewById(R.id.etpass);
        b1=(Button)findViewById(R.id.button);
//        e8=(EditText) findViewById(R.id.ethouse);
//        e9=(EditText)findViewById(R.id.etpin);
//        e10=(EditText)findViewById(R.id.etdob);
//        r1=(RadioButton)findViewById(R.id.radioButton);
//        r2=(RadioButton)findViewById(R.id.radioButton2);


//        e10.setInputType(InputType.TYPE_NULL);
//        e10.requestFocus();
//        setDateTimeField();
//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                phone=e3.getText().toString();
                email=e4.getText().toString();
                place=e5.getText().toString();
                user=e6.getText().toString();
                pass=e7.getText().toString();
//                dob=e10.getText().toString();
//                pincode=e9.getText().toString();
//                house=e8.getText().toString();
//                if(r1.isChecked())
//                {
//                    gender="Female";
//                }
//                else{
//                    gender="Male";
//                }
                if(fname.equalsIgnoreCase(""))
                {
                    e1.setError("Enter Your First Name");
                    e1.setFocusable(true);
                }
                else if(lname.equalsIgnoreCase(""))
                {
                    e2.setError("Enter Your Last Name");
                    e2.setFocusable(true);
                }
                else if(phone.equalsIgnoreCase(""))
                {
                    e3.setError("Enter your phone number");
                    e3.setFocusable(true);
                }
                else if(email.equalsIgnoreCase(""))
                {
                    e4.setError("Enter your email");
                    e4.setFocusable(true);
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e5.setError("Enter your place");
                    e5.setFocusable(true);
                }
                else if(user.equalsIgnoreCase("")) {
                    e6.setError("Enter Username");
                    e6.setFocusable(true);
                }
                else if(pass.equalsIgnoreCase(""))
                {
                    e7.setError("Enter password");
                    e7.setFocusable(true);
                }
                else{
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Userregisteration.this;
                    String q ="/userregister?fname="+fname+"&lname="+lname+"&phone="+phone+"&email="+email+"&place="+place+"&username="+user+"&password="+pass;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }

        });
    }


    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), Userregisteration.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            } else {
                startActivity(new Intent(getApplicationContext(), Userregisteration.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
}