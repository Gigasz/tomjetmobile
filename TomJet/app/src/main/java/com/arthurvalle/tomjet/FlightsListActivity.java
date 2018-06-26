package com.arthurvalle.tomjet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arthurvalle.tomjet.adapter.VooAdapter;
import com.arthurvalle.tomjet.model.Aeroporto;
import com.arthurvalle.tomjet.model.Aviao;
import com.arthurvalle.tomjet.model.Usuario;
import com.arthurvalle.tomjet.model.Voo;
import com.arthurvalle.tomjet.service.FlightListService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arthurvalle on 25/06/2018.
 */

public class FlightsListActivity extends AppCompatActivity {

    List<Voo> voos = new ArrayList();
   // Usuario user;
    Bundle bundle;
    ListView listVoos;
    ProgressBar progressBar;
    String token;
    String userId;
    Button btnPassages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flightslist);

        binding();

        btnPassages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(getApplicationContext(),PassagesActivity.class);
                itn.putExtra("userId",userId);
                itn.putExtra("token",token);
                startActivity(itn);
            }
        });

    }
    protected void binding(){
        listVoos = findViewById(R.id.listVoos);
        progressBar = findViewById(R.id.progressBar);
        btnPassages = findViewById(R.id.btnPassages);

        //showing progress bar (loading)
        progressBar.setVisibility(View.VISIBLE);

        //getting extras from loginActivity
        bundle = getIntent().getExtras();
        token = bundle.getString("token");
        userId = bundle.getString("userId");

        String[] params = {token};

            //accessing FlightListService to gather all flights
            FlightListService.TaskListener listener = new FlightListService.TaskListener() {
                @Override
                public void onFinished(String result) {
                    Log.e("flightResult",result);

                    //parsing json to List of flights
                    try {
                        JSONArray arr  = new JSONArray(result);
                        for(int i = 0; i < arr.length(); i++){


                            //Setting up Flight`s attributes
                            JSONObject jObj = arr.getJSONObject(i);
                            Voo v = new Voo();
                            v.setId(jObj.getInt("id"));
                            v.setValorPassagem(jObj.getDouble("valorPassagem"));
                            v.setDataVoo(jObj.getString("dataVoo"));

                            //Setting Flight`s origin address
                            JSONObject jsonOrigem = jObj.getJSONObject("origem");
                            Aeroporto origem = new Aeroporto();
                            origem.setId(jsonOrigem.getInt("id"));
                            origem.setAeroporto(jsonOrigem.getString("aeroporto"));
                            origem.setCidade(jsonOrigem.getString("cidade"));
                            v.setOrigem(origem);

                            //Setting Flight`s Arrival address
                            JSONObject jsonDestino = jObj.getJSONObject("destino");
                            Aeroporto destino = new Aeroporto();
                            destino.setId(jsonDestino.getInt("id"));
                            destino.setCidade(jsonDestino.getString("cidade"));
                            destino.setAeroporto(jsonDestino.getString("aeroporto"));
                            v.setDestino(destino);

                            //Setting Flight`s Airplane
                            JSONObject jsonAviao = jObj.getJSONObject("aviao");
                            Aviao aviao = new Aviao();
                            aviao.setId(jsonAviao.getInt("id"));
                            aviao.setModelo(jsonAviao.getString("modelo"));
                            aviao.setCapacidade(jsonAviao.getInt("capacidade"));
                            aviao.setPrefixo(jsonAviao.getString("prefixo"));
                            v.setAviao(aviao);

                            //Adding Flight into list
                            voos.add(v);

                            if(i == arr.length()-1){
                                setUpList();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            FlightListService flightListService = new FlightListService(listener);

            flightListService.execute(params);


    }

    protected void setUpList(){
        VooAdapter va = new VooAdapter(getApplicationContext(), voos);
        listVoos.setAdapter(va);
        progressBar.setVisibility(View.INVISIBLE);

        //On click event of the list.
        listVoos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //retrieving selected object
                Object v = listVoos.getItemAtPosition(position);
                Voo voo = (Voo) v;

                //starting new activity with extras
                Intent itn = new Intent(getApplicationContext(),FlightDetailsActivity.class);
                itn.putExtra("token", token);
                itn.putExtra("vooId", voo.getId());
                itn.putExtra("userId",userId);

                startActivity(itn);
            }
        });

    }
}
