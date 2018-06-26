package com.arthurvalle.tomjet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arthurvalle.tomjet.model.Aeroporto;
import com.arthurvalle.tomjet.model.Aviao;
import com.arthurvalle.tomjet.model.Voo;
import com.arthurvalle.tomjet.service.FindFlightService;

import org.json.JSONException;
import org.json.JSONObject;

public class FlightDetailsActivity extends AppCompatActivity {

    TextView textDataDetails, textOrigemCidade, textOrigemAeroporto, textDestinoCidade, textDestinoAeroporto, textAviaoModelo, textCapacidade, textValor;
    Button btnComprar;
    Bundle bundle;
    Voo voo;
    String token;
    ProgressBar progressBarDetails;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);

        binding();

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(getApplicationContext(),SeatSelectActivity.class);
                itn.putExtra("token",token);
                itn.putExtra("vooId",voo.getId());
                itn.putExtra("userId",userId);
                itn.putExtra("valorPassagem",voo.getValorPassagem());

                startActivity(itn);
            }
        });
    }

    private void binding() {
        textDataDetails = findViewById(R.id.textDataDetails);

        progressBarDetails = findViewById(R.id.progressBarDetails);

        textOrigemAeroporto = findViewById(R.id.origemAeroportoText);
        textOrigemCidade = findViewById(R.id.origemCidadeText);

        textDestinoAeroporto = findViewById(R.id.destinoAeroportoText);
        textDestinoCidade = findViewById(R.id.destinoCidadeText);

        textAviaoModelo = findViewById(R.id.aviaoModeloText);
        textCapacidade = findViewById(R.id.capacidadeText);

        textValor = findViewById(R.id.valorText);

        btnComprar = findViewById(R.id.btnComprar);

        bundle = getIntent().getExtras();

        setFlight();

    }

    //Retrieve data from service and parse to object <Voo>
    private void setFlight() {
        int vooId =  (int) bundle.get("vooId");
        token = (String) bundle.get("token");
        userId = (String) bundle.get("userId");

        String[] params = {Integer.toString(vooId),token};

        FindFlightService.TaskListener taskListener = new FindFlightService.TaskListener() {
            @Override
            public void onFinished(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    voo = new Voo();
                    voo.setId(obj.getInt("id"));
                    voo.setDataVoo(obj.getString("dataVoo"));
                    voo.setValorPassagem(obj.getDouble("valorPassagem"));

                    //setting destinny
                    JSONObject objDestino = obj.getJSONObject("destino");
                    Aeroporto destino = new Aeroporto();
                    destino.setId(objDestino.getInt("id"));
                    destino.setCidade(objDestino.getString("cidade"));
                    destino.setAeroporto(objDestino.getString("aeroporto"));
                    voo.setDestino(destino);

                    //setting origin
                    JSONObject objOrigem = obj.getJSONObject("origem");
                    Aeroporto origem = new Aeroporto();
                    origem.setId(objOrigem.getInt("id"));
                    origem.setAeroporto(objOrigem.getString("aeroporto"));
                    origem.setCidade(objOrigem.getString("cidade"));
                    voo.setOrigem(origem);

                    //setting airplane
                    JSONObject objAviao = obj.getJSONObject("aviao");
                    Aviao aviao = new Aviao();
                    aviao.setId(objAviao.getInt("id"));
                    aviao.setModelo(objAviao.getString("modelo"));
                    aviao.setCapacidade(objAviao.getInt("capacidade"));
                    aviao.setPrefixo(objAviao.getString("prefixo"));
                    voo.setAviao(aviao);

                    if(voo != null){
                        setTexts();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        FindFlightService findFlightService = new FindFlightService(taskListener);
        findFlightService.execute(params);
    }

    //Fill the textViews with info
    protected void setTexts(){
        textDataDetails.setText("Data: "+voo.getDataVoo());
        textOrigemCidade.setText("Cidade: "+voo.getOrigem().getCidade());
        textOrigemAeroporto.setText("Aeroporto "+voo.getOrigem().getAeroporto());
        textDestinoCidade.setText("Cidade: "+voo.getDestino().getCidade());
        textDestinoAeroporto.setText("Aeroporto: "+voo.getDestino().getAeroporto());
        textAviaoModelo.setText("Aviao: "+voo.getAviao().getModelo()+" || "+voo.getAviao().getPrefixo());
        textCapacidade.setText("Capacidade: "+voo.getAviao().getCapacidade());
        textValor.setText("R$"+voo.getValorPassagem());
        progressBarDetails.setVisibility(View.INVISIBLE);
    }
}
