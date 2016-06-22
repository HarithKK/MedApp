package com.knitechs.www.medapp.actor;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Koli on 6/17/2016.
 */
public class Patient {

    private int PID;
    private String recordCode;
    private String Name;
    private String Address;
    private String hometown;
    private String telephone;
    private String gender;
    private String GardianName;
    private String GardianTelephone;
    private int age;
    private Date birthdate;

    public int getPID() {
        return PID;
    }

    private void setPID(int PID) {
        this.PID = PID;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGardianName() {
        return GardianName;
    }

    public void setGardianName(String gardianName) {
        GardianName = gardianName;
    }

    public String getGardianTelephone() {
        return GardianTelephone;
    }

    public void setGardianTelephone(String gardianTelephone) {
        GardianTelephone = gardianTelephone;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public String getAgeString() {
        return String.valueOf(age);
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getBirthdateString(){
        try {
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            return df.format(birthdate);
        }catch(Exception e){
            return null;
        }
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public HashMap<String,String> setPatientDataToHashMap(){

        HashMap<String,String>params = new HashMap<>();

        params.put("PID",String.valueOf(getPID()));
        params.put("REC_CODE",getRecordCode());
        params.put("NAME",getName());
        params.put("ADDRESS",getAddress());
        params.put("HOMETOWN",getHometown());
        params.put("GENDER",getGender());
        params.put("GUARDIAN_NAME",getGardianName());
        params.put("GUARDIAN_TP",getGardianTelephone());
        params.put("B_DATE",getBirthdateString());
        params.put("AGE",getAgeString());
        params.put("TELEPHONE",getTelephone());

        return params;

    }

    public void setJSONToObject(JSONObject obj){
        try {
            setPID(obj.getInt("PID"));
            setRecordCode(obj.getString("REC_CODE"));
            setName(obj.getString("NAME"));
            setAddress(obj.getString("ADDRESS"));
            setHometown(obj.getString("HOMETOWN"));
            setTelephone(obj.getString("TELEPHONE"));
            setGender(obj.getString("GENDER"));
            Log.d("GENDEr : ",getGender());
            setGardianName(obj.getString("GUARDIAN_NAME"));
            setGardianTelephone(obj.getString("GUARDIAN_TP"));
            setBirthdate(new SimpleDateFormat("yyyy/MM/dd").parse(obj.getString("B_DATE")));
            //setAge(obj.getInt("AGE"));
            Log.d("BDATE : ",getBirthdateString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }
}
