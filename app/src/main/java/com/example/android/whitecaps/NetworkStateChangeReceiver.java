package com.example.android.whitecaps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Paras Seth on 15-05-2017.
 */

public class NetworkStateChangeReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener crl;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();

        if(crl != null){
            crl.onNetworkChange(isConnected);
        }
    }

   public static boolean isConnected(){
       ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance().getApplicationContext()
               .getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();

   }

    public interface ConnectivityReceiverListener{
        public void onNetworkChange(boolean inConnected);
    }
}