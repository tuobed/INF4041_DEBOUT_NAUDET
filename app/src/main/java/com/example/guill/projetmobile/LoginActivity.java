package com.example.guill.projetmobile;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialisation de nos boutons/textviews
        final EditText editUsername = (EditText) findViewById(R.id.editUsername);
        final EditText editPassword = (EditText) findViewById(R.id.editPassword);
        final Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        final Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        final TextView Registerlink = (TextView) findViewById(R.id.Registerlink);

        Registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });

        //Fonction lorsqu'on clic sur le bouton de login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editUsername.getText().toString();
                final String Password = editPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //On vérifie si l'utilisateur est bien enregistré en base de donnée.
                            //Le fichier PHP renvois un success
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                //si success est vérifié alors on charge la page d'accueil (UserActivity) avec
                                //le nom (name) et l'identifiant (username) de l'utilisateur
                                String name = jsonResponse.getString("name");
                                Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                LoginActivity.this.startActivity(intent);
                            }else {
                                //sinon messages d'erreur
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, Password, responseListener);
                //On place loginRequest en file d'attente d'exécution et on spécifie un objet contenant
                // les données que la méthode doit utiliser.
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }
}
