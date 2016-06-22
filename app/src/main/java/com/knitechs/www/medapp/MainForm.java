package com.knitechs.www.medapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.knitechs.www.medapp.buttonhandler.ButtonFactory;


public class MainForm extends Activity {

    ImageButton cmdAddMedicalRecord,cmdPatientInfor,cmdMedicalRecordHistory,cmdMapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        cmdAddMedicalRecord = (ImageButton)findViewById(R.id.cmd_add_medical_record);
        ButtonFactory.getButton(cmdAddMedicalRecord, MainForm.this);

        cmdPatientInfor =(ImageButton)findViewById(R.id.cmd_patient_infor);
        ButtonFactory.getButton(cmdPatientInfor,MainForm.this);

        cmdMedicalRecordHistory =(ImageButton)findViewById(R.id.cmd_patient_medical_history);
        ButtonFactory.getButton(cmdMedicalRecordHistory,MainForm.this);

        cmdMapView =(ImageButton)findViewById(R.id.cmd_map_view);
        ButtonFactory.getButton(cmdMapView,MainForm.this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_form, menu);
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
