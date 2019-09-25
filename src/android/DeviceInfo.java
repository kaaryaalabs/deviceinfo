package cordova.plugin.kaaryaa.deviceinfo;

import android.os.Build;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

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

    // private static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    private CallbackContext globalCallback;
    private JSONArray globalArgs;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        globalCallback = callbackContext;
        globalArgs = args;
        if (action.equals("getImei")) {
            this.getImei(args, callbackContext);
            return true;
        }
        if (action.equals("getMac")) {
            this.getMac(args, callbackContext);
            return true;
        }
        if (action.equals("getUuid")) {
            this.getUuid(args, callbackContext);
            return true;
        }
        return false;
    }

    private void getImei(JSONArray args, CallbackContext callbackContext) {
        String deviceUniqueIdentifier = null;
        Context context = cordova.getActivity().getApplicationContext();
        final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephony.getDeviceId();
        callbackContext.success(imei);
    }

    public void getMac(JSONArray args, CallbackContext callbackContext) {
        Context context = cordova.getActivity().getApplicationContext();
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final WifiInfo wInfo = wifiManager.getConnectionInfo();
        String mac = wInfo.getMacAddress();
        callbackContext.success(mac);
    }

    public void getUuid(JSONArray args, CallbackContext callbackContext) {
        Context context = cordova.getActivity().getApplicationContext();
        String uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        callbackContext.success(uuid);
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                android.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                getImei(globalArgs, globalCallback);
            } else {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_PHONE_STATE },
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        } else {
            getImei(globalArgs, globalCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        if (permissionsCallback == null) {
            return;
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImei(globalArgs, globalCallback);
            } else {
                Toast.makeText(this, "ehgehfg", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
