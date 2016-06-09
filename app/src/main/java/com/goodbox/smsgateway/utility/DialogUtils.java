package com.goodbox.smsgateway.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Toast;

import com.goodbox.smsgateway.R;


/**
 * Created by Amardeep.
 */
public class DialogUtils {

    private static ProgressDialog sProgressDialog;

    /**
     * Creates and displays Toast with given message.
     *
     * @param stringResourceId
     * @param ctx
     */
    public static void showToast(int stringResourceId, Context ctx) {
        Toast.makeText(ctx, stringResourceId, Toast.LENGTH_SHORT).show();
    }


    public static void displayProgressDialog(Context context) {
        displayProgressDialog(context, null);
    }

    /**
     * Creates and shows progress dialog with given message and sets your
     * OnCancelListener
     *
     * @param context
     * @param msg
     */
    public static void displayProgressDialog(Context context, String msg) {
        if (sProgressDialog != null && sProgressDialog.isShowing())
            return;

        if (context != null) {
            sProgressDialog = ProgressDialog.show(context, null, msg);
            try {
                sProgressDialog.show();
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Dismisses progress dialog.
     */
    public static void hideProgressDialog() {
        if (sProgressDialog != null && sProgressDialog.isShowing()) {
            try {
                sProgressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            sProgressDialog = null;
        }
    }
}
