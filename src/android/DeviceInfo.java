package cordova.plugin.kaaryaa.deviceinfo;

import android.os.Build;

import java.util.logging.Logger;

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

    public static final String PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final int PHONE_STATE_REQ_CODE = 0;

    private CallbackContext globalCallback;
    private JSONArray globalArgs;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        globalCallback = callbackContext;
        globalArgs = args;
        if (action.equals("getImei")) {
            if (cordova.hasPermission(PHONE_STATE)) {
                getImei(args, callbackContext);
            } else {
                getPermission(PHONE_STATE_REQ_CODE);
            }
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

    protected void getPermission(int requestCode) {
        cordova.requestPermission(this, requestCode, PHONE_STATE);
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults)
            throws JSONException {
        System.out.println("Entered on request call back");
        for (int r : grantResults) {
            System.out.println("granted result is: " + r );
            if (r == PackageManager.PERMISSION_DENIED) {
                this.callbackContext
                        .sendPluginResult(new PluginResult(PluginResult.Status.ERROR, PERMISSION_DENIED_ERROR));
                return;
            } else {

            }  
        }
        System.out.println("requestCode is " + requestCode);
        // switch (requestCode) {
        // case PHONE_STATE_REQ_CODE:
        // search(executeArgs);
        // break;
        // case SAVE_REQ_CODE:
        // save(executeArgs);
        // break;
        // case REMOVE_REQ_CODE:
        // remove(executeArgs);
        // break;
        // }
    }

    // private void checkPermission() {
    // if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
    // addProperty(KEY_RESULT_PERMISSION, true);
    // getImei(globalArgs, globalCallback);
    // } else if (cordova.hasPermission(permission)) {
    // addProperty(KEY_RESULT_PERMISSION, true);
    // getImei(globalArgs, globalCallback);
    // } else {
    // cordova.requestPermissions(this, REQUEST_CODE_ENABLE_PERMISSION, new String[]
    // { permission });
    // }
    // }

    // @Override
    // public void onRequestPermissionResult(int requestCode, String[] permissions,
    // int[] grantResults)
    // throws JSONException {

    // // Call info method
    // logger.info("Entered on request call back");
    // if (globalCallback == null) {
    // return;
    // }

    // if (permission != null) {
    // // Call checkPermission again to verify
    // boolean hasAllPermissions = cordova.hasPermission(permission);
    // addProperty(KEY_RESULT_PERMISSION, hasAllPermissions);
    // getImei(globalArgs, globalCallback);
    // } else {
    // addProperty(KEY_ERROR, ACTION_REQUEST_PERMISSION);
    // addProperty(KEY_MESSAGE, "Unknown error.");
    // globalCallback.error("Permission Denied");
    // }
    // globalCallback = null;
    // }

    // private void addProperty(String key, Object value) {
    // JSONObject obj = new JSONObject();
    // try {
    // if (value == null) {
    // obj.put(key, JSONObject.NULL);
    // } else {
    // obj.put(key, value);
    // }
    // } catch (JSONException ignored) {
    // // Believe exception only occurs when adding duplicate keys, so just ignore

    // }
    // }
}
