package com.knitechs.www.medapp.buttonhandler.Buttons;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * Created by Koli on 6/14/2016.
 */
public class MainFormButtonHandler implements View.OnClickListener,View.OnTouchListener {

    ImageButton button;
    Context packagecontext;
    Class nextviewclass;
    int height=10;

    public MainFormButtonHandler(ImageButton button,Context packagecontext,Class nextviewclass){
        this.button =button;
        this.nextviewclass = nextviewclass;
        this.packagecontext=packagecontext;

        button.setOnClickListener(this);
        button.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i= new Intent();
        i.setClass(packagecontext,nextviewclass);
        packagecontext.startActivity(i);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewGroup.LayoutParams params = button.getLayoutParams();

       if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            params.height -= height;
       }else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
            params.height += height;
        }

        button.setLayoutParams(params);
        return false;
    }


}
