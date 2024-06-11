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

public class Userviewotherspost extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] title,organ,description,date,value,username,lid,post_id,statat;
    SharedPreferences sh;
    public static String receiver_id,post_ids,statuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_userviewotherspost);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvview);



        l1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Userviewotherspost.this;
        String q="/userviewotherspostrequest?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("userviewotherspostrequest")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    lid = new String[ja1.length()];
                    username = new String[ja1.length()];
                    title = new String[ja1.length()];
                    organ = new String[ja1.length()];
                    date = new String[ja1.length()];
                    description = new String[ja1.length()];
                    value = new String[ja1.length()];
                    post_id = new String[ja1.length()];
                    statat = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        lid[i] = ja1.getJSONObject(i).getString("login_id");
                        username[i] = ja1.getJSONObject(i).getString("first_name") + " " + ja1.getJSONObject(i).getString("last_name");
                        title[i] = ja1.getJSONObject(i).getString("title");
                        organ[i] = ja1.getJSONObject(i).getString("organ_name");
                        post_id[i] = ja1.getJSONObject(i).getString("post_id");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        statat[i] = ja1.getJSONObject(i).getString("status");
                        description[i] = ja1.getJSONObject(i).getString("description");
                        value[i] = "Username: " + username[i] + "\nTitle: " + title[i] + "\nOrgan Name: " + organ[i] + "\nDescription: " + description[i] + "\nDate: " + date[i] + "\nStatus: " + statat[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }

            }
            else if(method.equalsIgnoreCase("accept")){

                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Userviewotherspost.class));

            }
            else if(method.equalsIgnoreCase("reject")){

                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Userviewotherspost.class));

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
        receiver_id = lid[position];
        SharedPreferences.Editor e = sh.edit();
        e.putString("receiver_id", receiver_id);

        e.commit();
        post_ids = post_id[position];
        statuss = statat[position];

        if (statuss.equalsIgnoreCase("pending")) {
            final CharSequence[] items = {"Accept", "Rejects", "Cancel",};
            AlertDialog.Builder builder = new AlertDialog.Builder(Userviewotherspost.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

//                    if (items[item].equals("View Map")) {
//                        String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "" + "," + LocationService.logi + "" + "&&daddr=" + Ambulance_view_request.lts + "," + Ambulance_view_request.lgs;
//                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        startActivity(in);
//                    }

                    if (items[item].equals("Rejects")) {
                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Userviewotherspost.this;
                        String q = "/Rejects?post_ids=" + post_ids;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
//                        startActivity(new Intent(getApplicationContext(), Userviewotherspost.class));


                    }
                    if (items[item].equals("Accept")) {
                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Userviewotherspost.this;
                        String q = "/accept?post_ids=" + post_ids;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
//                        startActivity(new Intent(getApplicationContext(), Userviewotherspost.class));

                    }
                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }


            });
            builder.show();
        }
//        else  if (sts.equalsIgnoreCase("reject")) {
//            Toast.makeText(getApplicationContext(), "This request is already rejected!", Toast.LENGTH_LONG).show();
//
//        }
//
        else if (statuss.equalsIgnoreCase("accepted")) {
            {
                final CharSequence[] items = {"Chat", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Userviewotherspost.this);
                // builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

//                    if (items[item].equals("View Map"))
//                    {
//                        String url = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+Ambulance_view_request.lts+","+Ambulance_view_request.lgs;
//                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        startActivity(in);
//                    }

                        if (items[item].equals("Chat")) {

//                            JsonReq JR = new JsonReq();
//                            JR.json_response = (JsonResponse) Ambulance_view_request.this;
//                            String q = "/accept?req_id=" + req_id;
//                            q = q.replace(" ", "%20");
//                            JR.execute(q);
                            startActivity(new Intent(getApplicationContext(), ChatHere.class));
                        }
//                        if (items[item].equals("Reject")) {
//                            JsonReq JR = new JsonReq();
//                            JR.json_response = (JsonResponse) Ambulance_view_request.this;
////					String q = "/user_add_interest?loginid="+Login.logid+"&type_id="+User_add_interests.type_ids;
//                            String q = "/reject?req_id=" + req_id;
//                            q = q.replace(" ", "%20");
//                            JR.execute(q);
////                    startActivity(new Intent(getApplicationContext(), Review.class));
//                        }


//                    if (items[item].equals(""))
//                    {
////                        startActivity(new Intent(getApplicationContext(), Ambulance_view_image.class));
//                    }
//                    if (items[item].equals(""))
//                    {
////                        startActivity(new Intent(getApplicationContext(), Ambulance_view_payment.class));
//                    }
//
                        else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }

                });
                builder.show();

//        final CharSequence[] items = {"Chat","Accept","Reject","Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewotherspost.this);
//        // builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//
//                if (items[item].equals("Chat")) {
//
//                    startActivity(new Intent(getApplicationContext(), ChatHere.class));
//
//                }
//                else if (items[item].equals("Accept")) {
//                    JsonReq JR = new JsonReq();
//                            JR.json_response = (JsonResponse) Userviewotherspost.this;
//                            String q = "/accept?post_ids=" + post_ids;
//                            q = q.replace(" ", "%20");
//                            JR.execute(q);
//
//
//
//                }
//
//                else if (items[item].equals("Reject")) {
//                    JsonReq JR = new JsonReq();
//                            JR.json_response = (JsonResponse) Userviewotherspost.this;
//                            String q = "/reject?post_ids=" + post_ids;
//                            q = q.replace(" ", "%20");
//                            JR.execute(q);
//
//                }
//
//                else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//
//            }
//
//        });
//        builder.show();
            }
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}
