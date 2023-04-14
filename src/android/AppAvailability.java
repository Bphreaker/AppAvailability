package com.ohh2ahh.appavailability;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;

import java.util.List;

public class AppAvailability extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("checkAvailability")) {
            String uri = args.getString(0);
            this.checkAvailability(uri, callbackContext);
            return true;
        }
        return false;
    }

    
    private void checkAvailability(String uri, CallbackContext callbackContext) {

        boolean installed = false;
        Context ctx = this.cordova.getActivity().getApplicationContext();
        final PackageManager pm = ctx.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        
        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(uri)) {
                installed = true;
                break;
            }
        }
        
        
        if(installed) {
            try {
                callbackContext.success("App URI is installed.");
            } 
            catch(Exception e) {
                callbackContext.error("Error: "+e.getMessage());    
            }
        }
        else {
            callbackContext.error("App URI is not installed");
        }

    }
}
