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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Userviewrcheckpoststatus extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] hospitalname,place,statuss,value,lid;
    public static String receiver_id;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_userviewrcheckpoststatus);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView)findViewById(R.id.lvview);
      l1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Userviewrcheckpoststatus.this;
        String q="/userviewpoststatus?pid="+Usersendpost.pids;
        q=q.replace(" ","%20");
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){
                JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                lid=new String[ja1.length()];
                hospitalname=new String[ja1.length()];
                statuss=new String[ja1.length()];
                place=new String[ja1.length()];
                statuss=new String[ja1.length()];
                value=new String[ja1.length()];

                for(int i = 0;i<ja1.length();i++)
                {
                    lid[i]=ja1.getJSONObject(i).getString("login_id");

                    hospitalname[i]=ja1.getJSONObject(i).getString("hospital_name");
                    statuss[i]=ja1.getJSONObject(i).getString("status");
                    place[i]=ja1.getJSONObject(i).getString("place");
                    statuss[i]=ja1.getJSONObject(i).getString("status");
                    value[i]="Hospital Name: "+hospitalname[i]+"\nPlace: "+place[i]+"\nStatus: "+statuss[i];

                }
                ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                l1.setAdapter(ar);
            }


        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        receiver_id=lid[position];
        SharedPreferences.Editor e=sh.edit();
        e.putString("receiver_id",receiver_id);

        e.commit();

        final CharSequence[] items = {"Chat","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewrcheckpoststatus.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Chat")) {

                    startActivity(new Intent(getApplicationContext(), ChatHere.class));

                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}