package com.arthurvalle.tomjet.service;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by arthurvalle on 24/06/2018.
 */

public class LoginService extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        String login = params[0];
        String senha = params[1];


        // Create URL
        URL url = null;
        try {
            //Setting URL
            url = new URL("https://service.davesmartins.com.br/api/usuarios/login");

            //Setting request params
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(95 * 1000);
            urlConnection.setConnectTimeout(95 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("X-Environment", "android");


            String loginJson = "{\n" +
                    "  \"login\": \"" + login + "\",\n" +
                    "  \"senha\": \"" + senha + "\"\n" +
                    "}";

            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(loginJson.getBytes());

            urlConnection.connect();

            String finalJson = "";
            if (urlConnection.getResponseCode() == 200) {
                InputStream responseBody = urlConnection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                BufferedReader reader = new BufferedReader(responseBodyReader);
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                finalJson = buffer.toString();
//                Log.e("sucesso", "sucessinho");
            } else {
                finalJson = urlConnection.getResponseCode()+"";
            }

            return finalJson;


        } catch (MalformedURLException e) {
            return "Erro1: "+e.getMessage();
        } catch (IOException e) {
            return "Erro2: "+e.getMessage();
        }
    }
}
