package com.arthurvalle.tomjet.service;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FlightListService extends AsyncTask<String, Void, String> {
    public interface TaskListener {
        public void onFinished(String result);
    }

    private final FlightListService.TaskListener taskListener;

    public FlightListService(FlightListService.TaskListener listener) {
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
        //Getting params

        String token = params[0];

        // Create URL
        URL url = null;
        try {
            //Setting URL
            url = new URL("https://service.davesmartins.com.br/api/voo");

            //Setting request params
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(95 * 1000);
            urlConnection.setConnectTimeout(95 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("X-Environment", "android");
            urlConnection.setRequestProperty("code",token);

            //Setting request data
//            String registerJson = "{\n" +
//                    "  \"Accept\": \"" + "application/json" + "\",\n" +
//                    "  \"code\": \"" + token + "\",\n" +
//                    "}";

//            urlConnection.setDoOutput(true);
//            urlConnection.getOutputStream().write(registerJson.getBytes());

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
