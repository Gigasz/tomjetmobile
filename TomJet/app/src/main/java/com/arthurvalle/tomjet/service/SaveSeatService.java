package com.arthurvalle.tomjet.service;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SaveSeatService extends AsyncTask<String, Void, String> {

    public interface TaskListener {
        public void onFinished(String result);
    }

    private final SaveSeatService.TaskListener taskListener;

    public SaveSeatService(SaveSeatService.TaskListener listener) {
        // The listener reference is passed in through the constructor
        this.taskListener = listener;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // In onPostExecute we check if the listener is valid
        if (this.taskListener != null) {
            // And if it is we call the callback function on it.
            this.taskListener.onFinished(result);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String poltrona = params[1];
        String token = params[2];



        // Create URL
        URL url = null;
        try {
            //Setting URL
            url = new URL("https://service.davesmartins.com.br/api/voo/"+id+"/poltronas/"+poltrona);

            //Setting request params
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setReadTimeout(95 * 1000);
            urlConnection.setConnectTimeout(95 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("X-Environment", "android");
            urlConnection.setRequestProperty("code",token);

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
