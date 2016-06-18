package com.knitechs.www.medapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.knitechs.www.medapp.actor.Patient;
import com.knitechs.www.medapp.core.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    private static String url_create_product = "set_patient_details.php";
    private ProgressDialog pDialog;
    JSONParser jsonParser =JSONParser.getInstance();
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button login = (Button)findViewById(R.id.cmdLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainForm.class));
            }
        });

        Button cmdsend = (Button)findViewById(R.id.cmdSend);
        cmdsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new CreateNewProduct().execute();

            }
        });

        Button cmdsendPatient = (Button)findViewById(R.id.cmdSendPatient);
        cmdsendPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Patient pt = new Patient();
                pt.setRecordCode("H1");
                pt.setName("H2");
                pt.setAddress("KOLI");
                pt.setTelephone("87236298");
                pt.setGardianTelephone("3275692");
                pt.setGardianName("ygfuyew");
                pt.setGender("MALE");
                pt.setBirthdate(new Date(2001,10,10));


                new CreateNewProduct().execute();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String name = "kolithasdfs";
            String price = "100";
            String description = "disc";



            HashMap<String,String> par = new HashMap<>();
            par.put("name", name);
            par.put("price", price);
            par.put("description", description);

            HashMap<String,String>params = new HashMap<>();

            params.put("PID","");
            params.put("REC_CODE","1111");
            params.put("NAME","kolitha");
            params.put("ADDRESS","THAL");
            params.put("GENDER","Male");
            params.put("GUARDIAN_NAME","G1");
            params.put("GUARDIAN_TP","G2");
            params.put("B_DATE","04021990");
            params.put("AGE","");
            params.put("TELEPHONE","G5");
            params.put("OP","new");

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Log.d("--------------------"," OK");
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}
