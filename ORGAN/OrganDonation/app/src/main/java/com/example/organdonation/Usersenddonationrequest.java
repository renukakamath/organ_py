package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Usersenddonationrequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1,e2;
    Button b1;
    ListView l1;
    String title,organname;
    String[] titles,organ,value,date,statuss,did;
    SharedPreferences sh;
    public static String dids,stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_usersenddonationrequest);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.etcomplaint);
        e2=(EditText)findViewById(R.id.organ);
        b1=(Button)findViewById(R.id.button);
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Usersenddonationrequest.this;
        String q="/userviewdonationrequest?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=e1.getText().toString();
                organname=e2.getText().toString();
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Usersenddonationrequest.this;
                String q = "/usersenddonationrequest?title=" + title + "&lid=" + sh.getString("log_id", "")+"&organ="+organname;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("usersenddonationrequest")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "SENDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Usersenddonationrequest.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("userviewdonationrequest"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    did=new String[ja1.length()];
                    titles=new String[ja1.length()];
                    organ=new String[ja1.length()];
                    date=new String[ja1.length()];
                    statuss=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        titles[i]=ja1.getJSONObject(i).getString("title");
                        did[i]=ja1.getJSONObject(i).getString("donation_id");
                        organ[i]=ja1.getJSONObject(i).getString("organ_name");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        statuss[i]=ja1.getJSONObject(i).getString("status");
                        value[i]="Title: "+titles[i]+"\nOrgan Name: "+organ[i]+"\nDate: "+date[i]+"\nStatus: "+statuss[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);
                }
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dids=did[position];
        stat=statuss[position];
        if(stat.equalsIgnoreCase("Accepted")) {
            final CharSequence[] items = {"Check Status", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Usersenddonationrequest.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Check Status")) {

                        startActivity(new Intent(getApplicationContext(), Userviewcheckdonationstatus.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }
    }
}