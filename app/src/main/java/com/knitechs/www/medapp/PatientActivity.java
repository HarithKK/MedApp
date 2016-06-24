package com.knitechs.www.medapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


public class PatientActivity extends ActionBarActivity {

    ImageButton cmdAdd,cmdSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        cmdAdd = (ImageButton)findViewById(R.id.cmd_add_patient);
        cmdAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(PatientActivity.this,PatientDetails.class);
                i.putExtra("operation","new");
                startActivity(i);
            }
        });

        cmdSearch=(ImageButton)findViewById(R.id.cmd_search_patient);
        cmdSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * the input box for get patient
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientActivity.this);
                builder.setTitle("Enter Patient Recognition Code");
                final EditText input = new EditText(PatientActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=input.getText().toString();
                        if(!s.isEmpty()) {
                            Intent i = new Intent(PatientActivity.this, PatientDetails.class);
                            i.putExtra("operation", "update");
                            i.putExtra("rec_code",s );
                            startActivity(i);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient, menu);
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
