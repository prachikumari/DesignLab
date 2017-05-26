package com.example.android.whitecaps;

import android.app.Application;

/**
 * Created by Paras Seth on 15-05-2017.
 */

public class MyApplication extends Application{
    public static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance(){
        return mInstance;
    }

    public void setConnectivityReceiver(NetworkStateChangeReceiver.ConnectivityReceiverListener listener){
        NetworkStateChangeReceiver.crl = listener;
    }

}
