package com.knitechs.www.medapp.buttonhandler;

import android.content.Context;
import android.widget.ImageButton;

import com.knitechs.www.medapp.MedicalRecordEntry;
import com.knitechs.www.medapp.PatientActivity;
import com.knitechs.www.medapp.PatientMedicalHistory;
import com.knitechs.www.medapp.R;
import com.knitechs.www.medapp.buttonhandler.Buttons.MainFormButtonHandler;

/**
 * Created by Koli on 6/14/2016.
 */
public class ButtonFactory {
    public static void getButton(ImageButton im,Context currentcontext){
        switch (im.getId()){
            case R.id.cmd_add_medical_record: new MainFormButtonHandler(im,currentcontext, MedicalRecordEntry.class);break;
            case R.id.cmd_patient_infor: new MainFormButtonHandler(im,currentcontext, PatientActivity.class);break;
            case R.id.cmd_patient_medical_history: new MainFormButtonHandler(im,currentcontext, PatientMedicalHistory.class);break;
        }
    }
}
