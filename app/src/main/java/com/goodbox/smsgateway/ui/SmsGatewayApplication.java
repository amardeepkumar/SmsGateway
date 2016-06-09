package com.goodbox.smsgateway.ui;

import android.app.Application;

import com.goodbox.smsgateway.utility.PreferenceManager;


/**
 * Created by Amardeep.
 */
public class SmsGatewayApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(getApplicationContext());
    }
}
