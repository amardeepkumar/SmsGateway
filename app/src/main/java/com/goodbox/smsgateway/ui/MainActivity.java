package com.goodbox.smsgateway.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;

import com.goodbox.smsgateway.R;
import com.goodbox.smsgateway.databinding.ActivityMainBinding;
import com.goodbox.smsgateway.utility.Constants;
import com.goodbox.smsgateway.utility.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setView();

        mBinding.messageBodySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceManager.getInstance().setBoolean(Constants.PreferencKeys.MESSAGE_BODY_SWITCH,
                        isChecked);
            }
        });

        mBinding.contactNumberSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceManager.getInstance().setBoolean(Constants.PreferencKeys.MESSAGE_FROM_SWITCH,
                        isChecked);
            }
        });

        mBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PreferenceManager preferenceManager = PreferenceManager.getInstance();
                preferenceManager.setString(Constants.PreferencKeys.MESSAGE_FROM,
                        mBinding.contactNumber.getText().toString());
                preferenceManager.setString(Constants.PreferencKeys.MESSAGE_BODY,
                        mBinding.messageBody.getText().toString());
                preferenceManager.setString(Constants.PreferencKeys.USER_URL,
                        mBinding.urlText.getText().toString());
            }
        });
        checkMessagePermission();
    }

    private void checkMessagePermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {

              //Explanation
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void setView() {
        PreferenceManager preferenceManager = PreferenceManager.getInstance();
        mBinding.contactNumber.setText(preferenceManager.getString(Constants.PreferencKeys.MESSAGE_FROM, ""));
        mBinding.messageBody.setText(preferenceManager.getString(Constants.PreferencKeys.MESSAGE_BODY, ""));
        mBinding.urlText.setText(preferenceManager.getString(Constants.PreferencKeys.USER_URL, ""));

        mBinding.contactNumberSwitch.setChecked(preferenceManager.getBoolean(Constants.PreferencKeys.MESSAGE_FROM_SWITCH, false));
        mBinding.messageBodySwitch.setChecked(preferenceManager.getBoolean(Constants.PreferencKeys.MESSAGE_BODY_SWITCH, false));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
