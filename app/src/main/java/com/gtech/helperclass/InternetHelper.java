package com.gtech.helperclass;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetHelper {
    private static Context context;
    public InternetHelper(Context context){
        this.context = context;
    }

    public Boolean isConnectingToInternet(){

        ConnectivityManager cv = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cv.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                cv.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                cv.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                cv.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED){
            return true;
        }
        else if (
                cv.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                        cv.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(context, "Please Check Internet", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}
