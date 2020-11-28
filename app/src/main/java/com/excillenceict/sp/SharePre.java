package com.excillenceict.sp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharePre {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int mode=0;
    String Filename ="sdfile";
    String Data="b";

    public SharePre( Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Filename, mode);
        editor =sharedPreferences.edit();
    }
    public void  secoundItem(){
        editor.putBoolean(Data,true);
        editor.commit();
    }
    public void firsTime(){
        if(!this.login()){
            Intent intent =new Intent(context,Registraion_OTP_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);

        }

    }
    private boolean login(){
        return sharedPreferences.getBoolean(Data,false);
    }
}
