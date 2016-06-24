package com.knitechs.www.medapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.knitechs.www.medapp.buttonhandler.ButtonFactory;
import com.knitechs.www.medapp.core.SQLLiteData;


public class MainForm extends Activity {

    ImageButton cmdAddMedicalRecord,cmdPatientInfor,cmdMedicalRecordHistory,cmdMapView,cmdChatBox,cmdLoggedout;
    private BroadcastReceiver broadcastReceiver;
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

        cmdChatBox =(ImageButton)findViewById(R.id.cmd_chatbox);
        ButtonFactory.getButton(cmdChatBox,MainForm.this);

        cmdLoggedout =(ImageButton)findViewById(R.id.cmd_loggedout);
        cmdLoggedout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SQLLiteData(MainForm.this).loggedout();
                Intent intent = new Intent(MainForm.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        try {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                        String toekn = intent.getStringExtra("token");
                        //Toast.makeText(getApplicationContext(), toekn, Toast.LENGTH_SHORT).show();
                    }else if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRTION_ERROR)){
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                    }else{

                    }
                }
            };

            // check connection in installed
            int requestCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
            if(ConnectionResult.SUCCESS != requestCode){
                if(GooglePlayServicesUtil.isUserRecoverableError(requestCode)){
                    Toast.makeText(getApplicationContext(),"GCM Not Installed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"GCM Not Working",Toast.LENGTH_SHORT).show();
                }
            }else{
                Log.d("---------------GCM Strtted","");
                Intent intent =new Intent(this,GCMRegistrationIntentService.class);
                startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("-----------------", "On resume");

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(GCMRegistrationIntentService.REGISTRTION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("-----------------","on paused");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
