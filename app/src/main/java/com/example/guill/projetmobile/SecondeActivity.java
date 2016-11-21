package com.example.guill.projetmobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SecondeActivity extends AppCompatActivity {

    RecyclerView rv_biere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);

        GetBiersServices.startActionGetAllBiers(this);
        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);
        rv_biere = (RecyclerView) findViewById(R.id.rv_biere);
        rv_biere.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_biere.setAdapter(new BiersAdapter(getBiersFromFile()));


    }

    public static final String TAG = "GetBiersServices";

    public static final String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
        public class BierUpdate extends BroadcastReceiver{

            @Override
            public void onReceive(Context context, Intent intent) {

                ((BiersAdapter)rv_biere.getAdapter()).setNewBiere(getBiersFromFile());
                //action(this).show;
            }
        }

    public void action (View v) {
        Toast.makeText(getApplicationContext(),getString(R.string.message),Toast.LENGTH_LONG).show();
    }

    private class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {

        private JSONArray biere;

        public BiersAdapter(JSONArray a) {

            biere=a;
        }

        public void setNewBiere (JSONArray b) {
            biere=b;
            notifyDataSetChanged();
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            //TextView rv_biere_element_name = (TextView) findViewById(R.id.rv_biere_element_name);

            View v = inflater.inflate(R.layout.rv_biere_element,parent ,false );
            return new BierHolder(v);
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {
            try {
                JSONObject b = biere.getJSONObject(position);
                holder.name.setText(b.getString("name"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return biere.length();
        }

        public class BierHolder extends RecyclerView.ViewHolder {
            public TextView name;

            public BierHolder(View v) {
                super(v);
                name = (TextView) v.findViewById(R.id.rv_biere_element_name);

            }
        }


    }

    public JSONArray getBiersFromFile() {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();

        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
