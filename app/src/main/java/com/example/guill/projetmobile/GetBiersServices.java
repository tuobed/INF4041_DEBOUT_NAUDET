package com.example.guill.projetmobile;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.nfc.Tag;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;


public class GetBiersServices extends IntentService {

    public static final String ACTION_GET_ALL_BIERS = "com.example.guill.projetmobile.ACTION_GET_ALL_BIERS";
    public static final String TAG = "GetBiersServices";


    public GetBiersServices() {
        super("GetBiersServices");
    }

    public static void startActionGetAllBiers(Context context) {
        Intent intent = new Intent(context, GetBiersServices.class);
        intent.setAction(ACTION_GET_ALL_BIERS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_BIERS.equals(action)) {
                getAllBiers();
                handleActionBiers();
            }
        }
    }

    private void getAllBiers() {
        Log.d(TAG, "getallbiers");

    }

    private void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleActionBiers() {
        Log.d(TAG, "Thread service name:" + Thread.currentThread().getName());
        URL url = null;

        try {
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "bieres.json"));
                //Log.d(TAG, "Bieres json downloaded !");
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SecondeActivity.BIERS_UPDATE));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
