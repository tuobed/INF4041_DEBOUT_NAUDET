package com.example.guill.projetmobile;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toast(this.getCurrentFocus());

    }

    @Override
    //ajoute les icons à l'ActionBar
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les icons à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    //Fonction créatrice de toast
    public void toast (View v) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        String message = username + ", welcome to your user area";
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    //Toast du bouton d'aide dans l'action bar
    public void toastActionBar (View v) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        String message = username + ", you can download the beer guide by clicking on the Download button";
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }


    //Fonction d'ouverture de ma "SecondeActivity". Cette fonction est lancée au moment ou l'utilisateur click sur "Download"
    public void ouvrirActivity (View v) {
        Intent intent = new Intent(FirstActivity.this, SecondeActivity.class );
        FirstActivity.this.startActivity(intent);
    }

    @Override
    //Fonction qui lance mon toast quand l'utilisateur click sur le bouton d'aide dans la barre d'action
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_help:
                toastActionBar(this.getCurrentFocus());
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //Fonction utilisée quand l'utilisateur click sur le boutton "Web site"
    //cette fonction nous envois sur le lien
    public void lien(View v) {
        String url = "http://www.guidedesbieres.com";
        Intent a = new Intent(Intent.ACTION_VIEW);
        a.setData(Uri.parse(url));
        startActivity(a);
    }

    //Fonction utilisée quand l'utilisateur click sur le boutton "Google Maps"
    //cette fonction ouvre l'application Google Map
    public void googleMaps(View v) {
        try {
            Intent i = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            //Si l'application n'existe pas, on affiche un message d'erreur dans une boite de dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
            builder.setMessage("Google map is missing")
                    .setNegativeButton("Ok", null)
                    .create()
                    .show();
        }
    }

}
