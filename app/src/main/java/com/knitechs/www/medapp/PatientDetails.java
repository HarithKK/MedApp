package com.knitechs.www.medapp;

import android.app.Activity;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.knitechs.www.medapp.actor.Patient;
import com.knitechs.www.medapp.core.JSONParser;
import com.knitechs.www.medapp.core.PDialog;
import com.knitechs.www.medapp.core.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class PatientDetails extends ActionBarActivity {

    Button cmdSave,cmdCancel;

    EditText rec_code;
    EditText fullname;
    EditText address;
    EditText telephone;
    EditText gardian_name;
    EditText gardian_tele;
    Spinner gender;
    DatePicker dob;

    private String sender_url ;              // sender URL
    private String reciver_url;
    private ProgressDialog pDialog;                 // process dialog for when executing
    private Activity currentActivity;               // current activity
    private String success_message;                 // success message when the dat inserted
    private HashMap<String,String> parameters;      // map for data for json object
    JSONParser jsonParser = JSONParser.getInstance();       // json parser
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OPERATION  = "operation";
    private JSONObject JSONpatientObject;
    Patient pt;
    private boolean processCompleted=false;
    private static final String reco_code="rec_code";
    private static String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        rec_code =(EditText) findViewById(R.id.txtPatientRecordCode);
        fullname=(EditText) findViewById(R.id.txtFullName);
        address = (EditText) findViewById(R.id.txtAddress);
        telephone = (EditText) findViewById(R.id.txtTelepphoneNumber);
        gardian_name=(EditText) findViewById(R.id.txtGardianName);
        gardian_tele = (EditText) findViewById(R.id.txtGardianContactNumber);
        gender = (Spinner) findViewById(R.id.spnGender);
        dob = (DatePicker)findViewById(R.id.dateDob);

        Intent i= getIntent();
        operation = i.getStringExtra(TAG_OPERATION);
        Log.d("-------------",operation);


        if(operation.contains("update")){
            pt= new Patient();
            pt.setRecordCode(i.getStringExtra(reco_code).trim());
            reciver_url = URLs.get_patient_url;
            new GetPatientDetails().execute();
        }


        cmdSave = (Button)findViewById(R.id.cmd_patient_details_save);
        cmdSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pt == null){
                    pt = new Patient();
                }

                pt.setRecordCode(rec_code.getText().toString());
                pt.setName(fullname.getText().toString());
                pt.setAddress(address.getText().toString());
                pt.setTelephone(telephone.getText().toString());
                pt.setGardianName(gardian_name.getText().toString());
                pt.setGardianTelephone(gardian_tele.getText().toString());
                pt.setGender(gender.getSelectedItem().toString());
                Calendar cal = Calendar.getInstance();
                cal.set(dob.getYear(),dob.getMonth(),dob.getDayOfMonth());
                pt.setBirthdate(cal.getTime());

                parameters =pt.setPatientDataToHashMap();
                parameters.put("ops",operation);
                sender_url=URLs.set_patient_url;
                success_message="Data Saved";
                String p_message="Data Saving";
                new InsertPatient().execute();

            }
        });

        cmdCancel=(Button)findViewById(R.id.cmd_patient_details_cancel);
        cmdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(PatientDetails.this,PatientActivity.class);
                PatientDetails.this.finish();
                PatientDetails.this.startActivity(i);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_details, menu);
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

    class InsertPatient extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog= new PDialog(PatientDetails.this,"Data Saving");
            pDialog.show();
        }

        /**
         * Creating patient
         * */
        protected String doInBackground(String... args) {

            JSONObject json = jsonParser.makeHttpRequest(sender_url, "POST", parameters);

            Log.d("Create Response", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Data Saved"," OK");        // data saved indatabase
                    processCompleted=true;
                } else {
                    Log.d("Data Failed", " NO");       // Data failed
                    processCompleted=false;
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
            if(!processCompleted){
                Toast.makeText(getApplicationContext(),"Error On Saving",Toast.LENGTH_SHORT);
            }
            if(!operation.contains("update")){
                rec_code.setText("");
                fullname.setText("");
                address.setText("");
                telephone.setText("");
                gardian_name.setText("");
                gardian_tele.setText("");
                gender.setSelection(0);

            }
            processCompleted=false;
        }

    }


    /**
     * get patient Details
     *
     */

    class GetPatientDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog= new PDialog(PatientDetails.this,"Fetching...");
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            int success;
            try {

                parameters= new HashMap<>();
                parameters.put("REC_CODE",pt.getRecordCode());

                JSONObject json = jsonParser.makeHttpRequest(reciver_url, "GET", parameters);

                // check your log for json response
                Log.d("Single Object", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray jsonArray = json.getJSONArray("patient"); // JSON Array

                    JSONpatientObject = jsonArray.getJSONObject(0);
                    processCompleted=true;
                }else{
                    processCompleted=false;
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
            // dismiss the dialog once got all details
            pDialog.dismiss();
            if(processCompleted){
                try {
                    pt.setJSONToObject(JSONpatientObject);
                    rec_code.setText(pt.getRecordCode());
                    fullname.setText(pt.getName());
                    address.setText(pt.getAddress());
                    telephone.setText(pt.getTelephone());
                    gardian_name.setText(pt.getGardianName());
                    gardian_tele.setText(pt.getGardianTelephone());
                    String g=pt.getGender();
                    int pos;
                    if(g.contains("Male"))
                        pos=0;
                    else if(g.contains("Female"))
                        pos=1;
                    else
                        pos=2;
                    gender.setSelection(pos);

                    Date d= pt.getBirthdate();
                    Calendar c=Calendar.getInstance();
                    c.setTime(d);
                    dob.updateDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(),"No Patient Found.!",Toast.LENGTH_SHORT).show();
            }
            processCompleted=false;
        }
    }
}
