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
import android.content.pm.PackageManager;
import android.Manifest.permission;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class DeviceInfo extends CordovaPlugin {

    // public static final String PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String  PHONE_STATE= "android.permission.READ_PHONE_STATE";
    private static final int REQUEST_CODE_ENABLE_PERMISSION = 0;

    private CallbackContext globalCallback;
    private JSONArray globalArgs;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        globalCallback = callbackContext;
        globalArgs = args;
        if (action.equals("getImei")) {

            getImei(args, callbackContext);
          
            return true;
        }
        if (action.equals("getMac")) {
            getMac(args, callbackContext);
            return true;
        }
        if (action.equals("getUuid")) {
            getUuid(args, callbackContext);
            return true;
        }
        return false;
    }

    private void getImei(JSONArray args, CallbackContext callbackContext) {
        String deviceUniqueIdentifier = null;
        Context context = cordova.getActivity().getApplicationContext();
        final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephony.getDeviceId();
        if (imei == null) {
             callbackContext.error("error finding imei");
        } else {
            callbackContext.success(imei);
        }
    }

    public void getMac(JSONArray args, CallbackContext callbackContext) {
        Context context = cordova.getActivity().getApplicationContext();
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final WifiInfo wInfo = wifiManager.getConnectionInfo();
        String mac = wInfo.getMacAddress();
        if (mac == null) {
            callbackContext.error("error finding mac");
        } else {
            callbackContext.success(mac);
        }
    }

    public void getUuid(JSONArray args, CallbackContext callbackContext) {
        Context context = cordova.getActivity().getApplicationContext();
        String uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (uuid == null) {
            callbackContext.error("error finding uuid");
        } else {
            callbackContext.success(uuid);
        }
    }

    // protected void getPermission(int requestCode)
    // {
    //     cordova.requestPermission(this, requestCode, PHONE_STATE);
    // }

    // public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException
    // {
    //     for(int r:grantResults)
    //     {
    //         if(r == PackageManager.PERMISSION_DENIED)
    //         {
    //             return;
    //         }
    //     }
    // }

      // getImei(args, callbackContext); 

            // if (cordova.hasPermission(PHONE_STATE)) {
            //     getImei(args, callbackContext);
            // } else {
            //     cordova.getThreadPool().execute(new Runnable() {
            //         public void run() {
            //             try {
            //                 getPermission(REQUEST_CODE_ENABLE_PERMISSION);
            //             } catch (Exception e) {
            //                 e.printStackTrace();
            //                 callbackContext.error("Request permission has been denied.");
            //                 globalCallback = null;
            //             }
            //         }
            //     });
            // }
}
