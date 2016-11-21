package com.example.guill.projetmobile;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final Button youhouButton = (Button) findViewById(R.id.button_youhou);

    }
    public void action (View v) {
        Toast.makeText(getApplicationContext(),getString(R.string.message),Toast.LENGTH_LONG).show();
    }

    public void notification_test (View v) {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ma notification")
                .setContentText("Hello World!");

        int mNotificationId = 001;
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public void ouvrirActivity (View v) {
        Intent intent = new Intent(MainActivity.this, SecondeActivity.class );
        MainActivity.this.startActivity(intent);
    }



}
