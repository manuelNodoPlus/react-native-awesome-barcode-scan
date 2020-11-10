package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AwesomeBarcodeScanModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final ReactApplicationContext reactContext;
    Promise promise;
    public AwesomeBarcodeScanModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AwesomeBarcodeScan";
    }

    @ReactMethod
    public void scan(Promise promise) {
        this.promise = promise;
        ReactApplicationContext context = getReactApplicationContext();
//        intent = new Intent(context,App.class);
        context.addActivityEventListener(this);
//        // TODO: Implement some actually useful functionality
//        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
        IntentIntegrator intentIntegrator = new IntentIntegrator(context.getCurrentActivity());
        intentIntegrator.setPrompt("Pon el código de barras dentro del rectángulo de escaneo.");
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        ReactApplicationContext context = getReactApplicationContext();
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(scanResult != null) {
            System.out.println(scanResult.toString().replaceAll("[^A-Za-z0-9]+", " "));
//            Log.i("SCAN", "scan result: " + scanResult.toString().replaceAll("[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/]"," ") + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaoooooooooooooooooooooo");

            this.promise.resolve(scanResult.toString().replaceAll("[^A-Za-z0-9]+", " "));

        } else
            Log.e("SCAN", "Sorry, the scan was unsuccessful...");
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
