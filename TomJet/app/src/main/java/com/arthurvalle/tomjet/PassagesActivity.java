package com.arthurvalle.tomjet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.arthurvalle.tomjet.adapter.PassagesAdapter;
import com.arthurvalle.tomjet.model.Assento;
import com.arthurvalle.tomjet.service.FindUserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PassagesActivity extends AppCompatActivity {

    ListView listViewPassages;
    List<Assento> passages = new ArrayList<>();
    String userId,token;
    Button btnNovaPassagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passages);

        binding();

        btnNovaPassagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(getApplicationContext(), FlightsListActivity.class);
                itn.putExtra("token",token);
                itn.putExtra("userId",userId);
                startActivity(itn);

            }
        });
    }

    private void binding() {
        listViewPassages = findViewById(R.id.listViewPassages);
        btnNovaPassagem = findViewById(R.id.btnNovaPassagem);
        Bundle bundle = getIntent().getExtras();
        userId = (String) bundle.get("userId");
        token = (String) bundle.get("token");

        getPassagesList();
    }

    private void getPassagesList() {
        String[] params = {userId,token};

        FindUserService.TaskListener taskListener = new FindUserService.TaskListener() {
            @Override
            public void onFinished(String result) {
                try {
                    JSONObject jObj = new JSONObject(result);

                    JSONArray arr = jObj.getJSONArray("passagens");

                    for (int i = 0; i < arr.length(); i++){
                        JSONObject AssObj = arr.getJSONObject(i);
                        Assento ass = new Assento();
                        ass.setAssento(AssObj.getInt("assento"));
                        ass.setOrigem(AssObj.getString("origem"));
                        ass.setDestino(AssObj.getString("destino"));
                        ass.setDataVoo(AssObj.getString("dataVoo"));
                        ass.setAviao(AssObj.getString("aviao"));
                        ass.setOcupado(1);
                        ass.setValorPassagem(AssObj.getDouble("valorPassagem"));

                        passages.add(ass);

                        if(i == arr.length()-1){ //see if the array was fully ran
                            setViewTexts();
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        FindUserService findUserService = new FindUserService(taskListener);
        findUserService.execute(params);

    }

    private void setViewTexts(){
        PassagesAdapter pa = new PassagesAdapter(getApplicationContext(), passages);
        listViewPassages.setAdapter(pa);

    }
}
