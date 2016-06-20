package com.knitechs.www.medapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.knitechs.www.medapp.actor.MedicalRecord;
import com.knitechs.www.medapp.actor.Patient;
import com.knitechs.www.medapp.actor.UserDetails;
import com.knitechs.www.medapp.core.JSONParser;
import com.knitechs.www.medapp.core.PDialog;
import com.knitechs.www.medapp.core.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;


public class MedicalRecordEntry extends ActionBarActivity {

    EditText txtPatientCode;
    EditText txtName;
    EditText txtSubmissionDate;
    EditText txtSubmissionTime;
    EditText txtlocalTimeString;
    EditText txtConsultantCode;

    EditText txtWBC;
    EditText txtRBC;
    EditText txtHB;
    EditText txtHTC;
    EditText txtMCV;
    EditText txtMCH;
    EditText txtMCHC;
    EditText txtRDW;
    EditText txtCBC;
    EditText txtReticulocyte;
    EditText txtPlatelet;

    EditText txtheight;
    EditText txtwaight;
    EditText txtBP;
    EditText txtHR;
    EditText txtAC;

    Button cmdSearch;
    Button cmdCancel;
    Button cmdSave;
    ImageButton cmdSelectDate;

    PDialog pDialog;
    HashMap<String,String> parameters;
    Patient patient;
    JSONParser jsonParser= JSONParser.getInstance();
    private static final String TAG_SUCCESS = "success";
    private boolean processCompleted=false;
    JSONObject JSONpatientObject;
    private MedicalRecord medicalRecord;
    TabHost tabHost;
    private String Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record_entry);

        /**
         * crete the tab space
         */

        tabHost = (TabHost)findViewById(R.id.tabHostPatientEnter);
        tabHost.setup();

        TabHost.TabSpec sp_genaral = tabHost.newTabSpec("Genral");
        sp_genaral.setIndicator("Genral");
        sp_genaral.setContent(R.id.layout_genaral);
        tabHost.addTab(sp_genaral);

        TabHost.TabSpec sp_blood = tabHost.newTabSpec("Blood_report");
        sp_blood.setIndicator("Blood Report");
        sp_blood.setContent(R.id.layout_blood_report);
        tabHost.addTab(sp_blood);

        TabHost.TabSpec sp_other= tabHost.newTabSpec("other");
        sp_other.setIndicator("Other");
        sp_other.setContent(R.id.layout_other);
        tabHost.addTab(sp_other);

        /**
         *  Assigns
         */

        txtPatientCode =(EditText)findViewById(R.id.txt_medical_record_patient_record_code);
        txtName =(EditText)findViewById(R.id.txt_medical_record_patient_name);
        txtSubmissionDate =(EditText)findViewById(R.id.txt_medical_record_submission_date);
        txtSubmissionTime =(EditText)findViewById(R.id.txt_medical_record_Submission_time);
        txtlocalTimeString = new EditText(MedicalRecordEntry.this);
        txtConsultantCode =(EditText)findViewById(R.id.txt_medical_record_consultant_code);

        txtWBC =(EditText)findViewById(R.id.txt_medical_record_WBC_count);
        txtRBC =(EditText)findViewById(R.id.txt_medical_record_RBC_count);
        txtHB =(EditText)findViewById(R.id.txt_medical_record_HB_count);
        txtHTC =(EditText)findViewById(R.id.txt_medical_record_HTC_count);
        txtMCV =(EditText)findViewById(R.id.txt_medical_record_MCV_count);
        txtMCH =(EditText)findViewById(R.id.txt_medical_record_MCH_count);
        txtMCHC =(EditText)findViewById(R.id.txt_medical_record_MCHC_count);
        txtRDW =(EditText)findViewById(R.id.txt_medical_record_RDW_count);
        txtCBC =(EditText)findViewById(R.id.txt_medical_record_CBC_count);
        txtReticulocyte =(EditText)findViewById(R.id.txt_medical_record_reticulocyte_count);
        txtPlatelet =(EditText)findViewById(R.id.txt_medical_record_Platelet_count);

        txtheight =(EditText)findViewById(R.id.txt_medical_record_patient_height);
        txtwaight =(EditText)findViewById(R.id.txt_medical_record_patient_waight);
        txtBP =(EditText)findViewById(R.id.txt_medical_record_patient_bp);
        txtHR =(EditText)findViewById(R.id.txt_medical_record_patient_hr);
        txtAC =(EditText)findViewById(R.id.txt_medical_record_patient_alcohol);

        cmdSave = (Button)findViewById(R.id.cmd_medical_record_save);
        cmdCancel=(Button)findViewById(R.id.cmd_medical_record_cancel);
        cmdSearch=(Button)findViewById(R.id.cmd_medical_record_search_patient);
        cmdSelectDate=(ImageButton)findViewById(R.id.cmd_medical_record_select_date);

        txtConsultantCode.setText(UserDetails.getInstance().getUserCode());

        /**
         * serch patient code
         */
        cmdSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patient= new Patient();
                patient.setRecordCode(txtPatientCode.getText().toString());
                new GetPatientDetails().execute();
            }
        });

        /**
         * cancel the process
         */
        cmdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MedicalRecordEntry.this,MainForm.class);
                finish();
                startActivity(i);
            }
        });

        /**
         * search the date
         */

        cmdSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DateTimeDialog(MedicalRecordEntry.this,txtSubmissionDate,txtSubmissionTime,txtlocalTimeString);
            }
        });

        /**
         * select Alcohol consumption
         */

        txtAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MedicalRecordEntry.this);

                builder.setItems(R.array.bool,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] boo=getResources().getStringArray(R.array.bool);
                        txtAC.setText(boo[i]);

                    }
                });

                builder.show();
            }
        });

        /**
         * save button
         */
        cmdSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtPatientCode.getText().toString().isEmpty() || txtName.getText().toString().isEmpty() || txtSubmissionDate.getText().toString().isEmpty() || txtSubmissionTime.getText().toString().isEmpty() || txtConsultantCode.getText().toString().isEmpty()){
                    Toast.makeText(MedicalRecordEntry.this,"Fill Blanks",Toast.LENGTH_SHORT).show();
                }else{

                    medicalRecord = new MedicalRecord();

                    medicalRecord.setPatientRegCode(txtPatientCode.getText().toString());
                    medicalRecord.setName(txtName.getText().toString().trim());
                    try {
                        medicalRecord.setSubmissionDate(txtSubmissionDate.getText().toString());
                        medicalRecord.setSubmissionTime(txtlocalTimeString.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    medicalRecord.setConsultantCode(txtConsultantCode.getText().toString());
                    medicalRecord.setWbc(txtWBC.getText().toString());
                    medicalRecord.setRbc(txtRBC.getText().toString());
                    medicalRecord.setHbc(txtHB.getText().toString());
                    medicalRecord.setHtc(txtHTC.getText().toString());
                    medicalRecord.setMcv(txtMCV.getText().toString());
                    medicalRecord.setMch(txtMCH.getText().toString());
                    medicalRecord.setMchc(txtMCHC.getText().toString());
                    medicalRecord.setRdw(txtRDW.getText().toString());
                    medicalRecord.setCbc(txtCBC.getText().toString());
                    medicalRecord.setReticulocyte(txtReticulocyte.getText().toString());
                    medicalRecord.setPlatelet(txtPlatelet.getText().toString());
                    medicalRecord.setHeight(txtheight.getText().toString());
                    medicalRecord.setWaight(txtwaight.getText().toString());
                    medicalRecord.setBloodpresure(txtBP.getText().toString());
                    medicalRecord.setHeartRate(txtHR.getText().toString());
                    medicalRecord.setAlcoholConsumption(txtAC.getText().toString());

                    parameters = medicalRecord.setHashMap();
                    new SetMedicalReport().execute();

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medical_record_entry, menu);
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

    /**
     *  Get Patient Data
     */

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
            pDialog= new PDialog(MedicalRecordEntry.this,"Fetching...");
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            int success;
            try {

                parameters= new HashMap<>();
                parameters.put("REC_CODE",patient.getRecordCode());

                JSONObject json = jsonParser.makeHttpRequest(URLs.get_patient_url, "GET", parameters);

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
                    patient.setJSONToObject(JSONpatientObject);
                    txtName.setText(patient.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No Patient Found.!", Toast.LENGTH_SHORT).show();
            }
            processCompleted=false;
        }
    }

    /**
     * save medicl report
     */
    class SetMedicalReport extends AsyncTask<String, String, String>{

            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new PDialog(MedicalRecordEntry.this,"Data Saving");
                pDialog.show();
            }

            protected String doInBackground(String... args) {

                JSONObject json = jsonParser.makeHttpRequest(URLs.set_medical_record_url, "POST", parameters);

                Log.d("Create Response", json.toString());

                try {
                    int success = json.getInt(TAG_SUCCESS);
                    Message = json.getString("message");
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
                    Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                    EditText ed[]= new EditText[]{txtPatientCode,txtName,txtSubmissionDate,txtSubmissionTime,txtWBC,txtRBC,txtHB,txtHTC,txtMCV,txtMCH,txtMCHC,txtRDW,txtCBC,txtReticulocyte,txtPlatelet,txtheight,txtwaight,txtBP,txtHR,txtAC};
                    for(EditText e:ed){
                        e.setText("");
                    }
                   tabHost.setTop(0);
                }
                processCompleted=false;
            }

        }


    }
