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

    private static final String ACTION_CHECK_PERMISSION = "checkPermission";
    private static final String ACTION_REQUEST_PERMISSION = "requestPermission";
    private static final String ACTION_REQUEST_PERMISSIONS = "requestPermissions";

    private static final int REQUEST_CODE_ENABLE_PERMISSION = 55433;

    private static final String KEY_ERROR = "error";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_RESULT_PERMISSION = "hasPermission";

    private static final String permission = "android.permission.READ_PHONE_STATE";

    private CallbackContext globalCallback;
    private JSONArray globalArgs;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        globalCallback = callbackContext;
        globalArgs = args;
        if (action.equals("getImei")) {
            checkPermission();
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            JSONObject returnObj = new JSONObject();
            addProperty(returnObj, KEY_RESULT_PERMISSION, true);
            getImei(globalArgs, globalCallback);
        } else if (hasAllPermissions(permission)) {
            JSONObject returnObj = new JSONObject();
            addProperty(returnObj, KEY_RESULT_PERMISSION, true);
            getImei(globalArgs, globalCallback);
        } else {
            cordova.requestPermissions(this, REQUEST_CODE_ENABLE_PERMISSION, new String[]{permission});
        }
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {


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
    private boolean hasAllPermissions(JSONArray permissions) throws JSONException {
        return hasAllPermissions(getPermissions(permissions));
    }
    private boolean hasAllPermissions(String[] permissions) throws JSONException {

        for (String permission : permissions) {
            if(!cordova.hasPermission(permission)) {
                return false;
            }
        }

        return true;
    }
}
