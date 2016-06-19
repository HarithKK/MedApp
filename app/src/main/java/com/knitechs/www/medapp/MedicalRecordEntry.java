package com.knitechs.www.medapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TabHost;


public class MedicalRecordEntry extends ActionBarActivity {

    EditText txtPatientCode;
    EditText txtName;
    EditText txtSubmissionDate;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record_entry);

        /**
         * crete the tab space
         */

        TabHost tabHost = (TabHost)findViewById(R.id.tabHostPatientEnter);
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
}
