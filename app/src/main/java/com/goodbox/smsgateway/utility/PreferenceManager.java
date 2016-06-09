package com.goodbox.smsgateway.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author Amardeep
 * Preference Manager to get/set shared preferences
 */
public class PreferenceManager {
	private static final String SHARED_PREFS = "App_Sh_Prefs";
	private static SharedPreferences mSharedPrefs;
	private static PreferenceManager sInstance;
	private static Editor mEditor;

	private PreferenceManager(Context ctx) {
		mSharedPrefs = ctx.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
		mEditor = mSharedPrefs.edit();
	}

    /**
     * Initializes Preference Members
     * @param ctx
     */
    public static void init(Context ctx) {
        sInstance = new PreferenceManager(ctx);
    }

	/**
	 * Returns singleton synchronized instance of PreferenceManager
	 * @return Instance of PreferenceManager
	 */
	public synchronized static PreferenceManager getInstance() {
		return sInstance;
	}

	/**
	 * Returns integer value for given preference key
	 * @param key
	 * @param defValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return mSharedPrefs.getBoolean(key, defValue);
	}

	/**
	 * Adds integer to Preference
	 * @param key
	 * @param value
	 */
	public void setBoolean(String key, boolean value) {
		mEditor.putBoolean(key, value);
		mEditor.commit();
	}

	/**
	 * Returns String value for given preference key
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String getString(String key, String defValue) {
		return mSharedPrefs.getString(key, defValue);
	}

	/**
	 * Adds String to Preference
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		mEditor.putString(key, value);
		mEditor.commit();
	}
}
