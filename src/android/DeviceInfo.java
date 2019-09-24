package cordova.plugin.kaaryaa.deviceinfo;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class DeviceInfo extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getImei")) {
            this.getImei(args, callbackContext);
            return true;
        }
        if (action.equals("getImsi")) {
            this.getImsi(args, callbackContext);
            return true;
        }
        if (action.equals("getIccid")) {
            this.getIccid(args, callbackContext);
            return true;
        }
        if (action.equals("getMac")) {
            this.getMac(args, callbackContext);
            return true;
        }
        return false;
    }

    private void getImei(JSONArray args, CallbackContext callbackContext) {
        Context context = cordova.getActivity().getApplicationContext();
        final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephony.getDeviceId();
        callbackContext.success(imei);
    }

	public String getImsi(JSONArray args, CallbackContext callbackContext)  {
        Context context = cordova.getActivity().getApplicationContext();
		final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephony.getSubscriberId();
        callbackContext.success(imsi);
	}

	public String getIccid(JSONArray args, CallbackContext callbackContext)  {
        Context context = cordova.getActivity().getApplicationContext();
		final TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String iccid = mTelephony.getSimSerialNumber();
        callbackContext.success(iccid);
	}

	public String getMac(JSONArray args, CallbackContext callbackContext)  {
        Context context = cordova.getActivity().getApplicationContext();
		final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		final WifiInfo wInfo = wifiManager.getConnectionInfo();
		String mac = wInfo.getMacAddress();
        callbackContext.success(mac);
	}
}
