package com.knitechs.www.medapp.core;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Koli on
 */
public class PDialog extends ProgressDialog {

    public PDialog(Context context) {
        super(context);
    }

    public PDialog(Context context, String message){
        super(context);
        super.setMessage(message);
        super.setIndeterminate(false);
    }
}
