package com.knitechs.www.medapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.knitechs.www.medapp.actor.UserDetails;
import com.knitechs.www.medapp.core.JSONParser;
import com.knitechs.www.medapp.core.PDialog;
import com.knitechs.www.medapp.core.SQLLiteData;
import com.knitechs.www.medapp.core.URLs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * coded by Kolitha warnalulasooriya
 * knitech softsolution
 */

public class MainActivity extends Activity {

    EditText txtUserName;
    EditText txtPassword;
    Button cmdLogin;
    Button cmdCancel;

    private PDialog pDialog;
    private HashMap<String,String> parameters;
    private String Message;
    JSONObject JSONUserObject;
    private boolean isAuthorised;
    SQLLiteData db=new SQLLiteData(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserName=(EditText)findViewById(R.id.txt_username);
        txtPassword=(EditText)findViewById(R.id.txt_password);
        cmdLogin=(Button)findViewById(R.id.cmd_login);
        cmdCancel=(Button)findViewById(R.id.cmd_login_cancel);

        if(db.isLogged()){
           UserDetails.getInstance().setUserCode(db.getLoggerCode());
           UserDetails.getInstance().setUserName(db.getLoggerUserName());
           UserDetails.getInstance().setName(db.getLoggerName());
           Log.d("------------------------------ Code",UserDetails.getInstance().getUserCode());
           Log.d("------------------------------ user name",UserDetails.getInstance().getUserName());
           Log.d("------------------------------ name",UserDetails.getInstance().getName());

            Intent intent = new Intent(MainActivity.this,MainForm.class);
            finish();
            startActivity(intent);
        }

        cmdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cmdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDetails.getInstance().setUserName(txtUserName.getText().toString());
                UserDetails.getInstance().setPassword(txtPassword.getText().toString());

                parameters= new HashMap<>();
                parameters.put("USER_NAME",UserDetails.getInstance().getUserName());
                parameters.put("PASSWORD",UserDetails.getInstance().getPassword());

                new Login().execute();
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

    /**
     * login to the server
     */

    class Login extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog= new PDialog(MainActivity.this,"Login...");
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            int success;
            try {
                JSONObject json = JSONParser.getInstance().makeHttpRequest(URLs.get_user_account_details, "GET", parameters);

                // check your log for json response
                Log.d("List Loaded", json.toString());

                success = json.getInt("success");
                Message=json.getString("message");
                if (success == 1) {
                    JSONArray jsonArray = json.getJSONArray("userdetails"); // JSON Array
                    JSONUserObject = jsonArray.getJSONObject(0);
                    isAuthorised=true;
                }else{
                    isAuthorised=false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                isAuthorised=false;
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            try {
                if(isAuthorised){
                    UserDetails.getInstance().setUserCode(JSONUserObject.getString("USER_CODE"));
                    UserDetails.getInstance().setName(JSONUserObject.getString("NAME"));
                    UserDetails.getInstance().setLoggedIn(true);

                    db.loggedin(UserDetails.getInstance().getUserCode(),UserDetails.getInstance().getUserName(),UserDetails.getInstance().getName());

                    Intent intent = new Intent(MainActivity.this,MainForm.class);
                    finish();
                    startActivity(intent);
                }

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, Message, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                Log.d("------------ERROR",e.getMessage());
            }
        }
    }


}
