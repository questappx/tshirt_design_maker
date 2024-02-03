package com.questappx.tshirtmaker.AdsWorking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyReceiver extends BroadcastReceiver {

    public static boolean connectionStatusConnected=false;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = NetworkUtil.getConnectivityStatusString(context);
        if(status) {
            connectionStatusConnected = true;
//            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
        }
        else
        {
            connectionStatusConnected = false;
//            Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, statu, Toast.LENGTH_LONG).show();
        }

    }
}
