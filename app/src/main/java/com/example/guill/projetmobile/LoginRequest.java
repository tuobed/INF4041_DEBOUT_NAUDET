package com.example.guill.projetmobile;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://guillaume5.esy.es/Login.php";
    private Map<String, String> params;

    //permets d'envoyer les paramètres à la base de donnée.
    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params=new HashMap<>();
        //On ajoute dans "username" le username entré. Pareil pour password.
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    //Fonction obligatoire, on retourne les paramètres.
    public Map<String, String> getParams() {
        return params;
    }
}

