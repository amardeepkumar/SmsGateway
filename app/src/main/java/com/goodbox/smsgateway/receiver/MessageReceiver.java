package com.goodbox.smsgateway.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.goodbox.smsgateway.R;
import com.goodbox.smsgateway.network.NetworkManager;
import com.goodbox.smsgateway.utility.Constants;
import com.goodbox.smsgateway.utility.DialogUtils;
import com.goodbox.smsgateway.utility.NetworkUtil;
import com.goodbox.smsgateway.utility.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();

    public MessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "OnReceive");
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Log.d(TAG, "OnReceive condition true");
            final Bundle bundle = intent.getExtras();
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];

                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                if (messages.length > 0) {
                    final String msgFrom = messages[0].getOriginatingAddress();
                    final String msgBody = messages[0].getMessageBody();
                    final Map<String, String> map = new HashMap<>();
                    final PreferenceManager preferenceManager = PreferenceManager.getInstance();

                    final String messageFromCriteria = preferenceManager.getString(Constants.PreferencKeys.MESSAGE_FROM, "");
                    final String messageBodyCriteria = preferenceManager.getString(Constants.PreferencKeys.MESSAGE_BODY, "");

                    Log.d(TAG, msgFrom);
                    Log.d(TAG, msgBody);
                    if (preferenceManager.getBoolean(Constants.PreferencKeys.MESSAGE_FROM_SWITCH, false)
                            && msgFrom != null && msgFrom.contains(messageFromCriteria)) {
                        map.put(Constants.PreferencKeys.MESSAGE_FROM, msgFrom);
                    }

                    if (preferenceManager.getBoolean(Constants.PreferencKeys.MESSAGE_BODY_SWITCH, false)
                            && msgBody!= null&& msgBody.contains(messageBodyCriteria)) {
                        map.put(Constants.PreferencKeys.MESSAGE_BODY, msgBody);
                    }

                    requestServer(map, context);
                }
            } catch (Exception e) {
                Log.d("Exception caught", e.getMessage());
            }
        }
    }

    private void requestServer(Map<String, String> map, final Context context) {

        if (!NetworkUtil.isConnectionAvailable(context)) {
            DialogUtils.showToast(R.string.no_network, context);
            return;
        }
        String url = PreferenceManager.getInstance().getString(Constants.PreferencKeys.USER_URL, "");
        NetworkManager.requestServer(url, map, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccess()) {
                    DialogUtils.showToast(R.string.response_successful, context);
                } else {
                    DialogUtils.showToast(R.string.response_failed, context);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                DialogUtils.showToast(R.string.response_failed, context);
            }
        });
    }
}
