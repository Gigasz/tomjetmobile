package com.arthurvalle.tomjet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.arthurvalle.tomjet.model.Assento;
import com.arthurvalle.tomjet.model.Voo;
import com.arthurvalle.tomjet.service.FindFlightService;
import com.arthurvalle.tomjet.service.SeatListService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectActivity extends AppCompatActivity {

    int vooId;
    String userId;
    String token;
    Spinner seatSpinner;
    Button btnPayment;
    List<Assento> assentos = new ArrayList<>();
    double valorPassagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_select);

        binding();

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(getApplicationContext(), PaymentActivity.class);
                itn.putExtra("vooId",vooId);
                itn.putExtra("userId",userId);
                itn.putExtra("token",token);
                itn.putExtra("valorPassagem",valorPassagem);

                String selected = seatSpinner.getSelectedItem().toString();
                itn.putExtra("seat",selected);

                startActivity(itn);

            }
        });
    }

    private void binding() {
        btnPayment = findViewById(R.id.btnPayment);
        seatSpinner = findViewById(R.id.seatSpinner);

        Bundle bundle = getIntent().getExtras();
        vooId = (Integer) bundle.get("vooId");
        userId = (String) bundle.get("userId");
        token = (String) bundle.get("token");
        valorPassagem = (double) bundle.get("valorPassagem");

        setAvailableSeats();
    }

    private void setAvailableSeats() {
        String[] params = {Integer.toString(vooId),token};

        SeatListService.TaskListener taskListener = new SeatListService.TaskListener() {
            @Override
            public void onFinished(String result) {

                try {
                    JSONArray arr  = new JSONArray(result);

                    int aux = 0; //this aux will be used to check if the array was ran completely by the loop
                    for(int i = 0; i < arr.length(); i++){

                        //Parsing from json to Object and adding to list
                        JSONObject jObj = arr.getJSONObject(i);
                        if(jObj.getString("ocupado").equals("false")){ //checking if the current seat is available
                            Assento ass = new Assento();
                            ass.setAssento(jObj.getInt("assento"));
                            ass.setOcupado(0);
                            ass.setOrigem(jObj.getString("origem"));
                            ass.setDestino(jObj.getString("destino"));
                            ass.setDataVoo(jObj.getString("dataVoo"));
                            ass.setAviao(jObj.getString("aviao"));
                            ass.setValorPassagem(jObj.getDouble("valorPassagem"));

                            assentos.add(ass);//adding to list
                        }
                        aux++;
                        if(aux == arr.length()-1){
                            fillSpinner(); //populate spinner
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        /* LoginService loginService = new LoginService(listener);

                loginService.execute(params);*/
        SeatListService seatListService = new SeatListService(taskListener);
        seatListService.execute(params);
    }

    private void fillSpinner() {

        List<String> spinnerArray =  new ArrayList<String>();
        for(int i = 0; i < assentos.size(); i++){
            spinnerArray.add(Integer.toString(assentos.get(i).getAssento()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seatSpinner.setAdapter(adapter);

    }
}
