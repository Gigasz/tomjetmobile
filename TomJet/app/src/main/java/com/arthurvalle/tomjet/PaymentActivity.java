package com.arthurvalle.tomjet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurvalle.tomjet.service.PaymentService;
import com.arthurvalle.tomjet.service.SaveSeatService;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    EditText numeroCartaoText, tarjaText, anoText, mesText;
    TextView valorEditText;
    Button btnFinalizarPagamento;
    String userId, seat, token;
    int vooId;
    double valorPassagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        binding();

        btnFinalizarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking if the payment have worked

              String numeroCartao = numeroCartaoText.getText().toString();
              String tarjaCartao = tarjaText.getText().toString();
              String anoCartao = anoText.getText().toString();
              String mesCartao= mesText.getText().toString();

              if (numeroCartao != null && tarjaCartao != null && anoCartao != null && mesCartao != null){

                  final String[] params = {numeroCartaoText.getText().toString(), tarjaText.getText().toString(),anoText.getText().toString(),mesText.getText().toString(),Double.toString(valorPassagem)};

                  PaymentService.TaskListener taskListener = new PaymentService.TaskListener() {
                      @Override
                      public void onFinished(String result) {
                          try {
                              JSONObject jObj = new JSONObject(result);

                              if(jObj.getString("status").equals("APROVADO")){
                                  //if the card was approved, it will redirect to approvedpayment activity
                                   Toast.makeText(getApplicationContext(),"Pagamento Aprovado", Toast.LENGTH_LONG).show();

                                   //Saving seat in Service
                                  String[] params = {Integer.toString(vooId),seat,token};
                                  SaveSeatService.TaskListener taskListener = new SaveSeatService.TaskListener() {
                                      @Override
                                      public void onFinished(String result) {
                                          if(result.equals("404")){
                                              Toast.makeText(getApplicationContext(),"Nao foi possivel reservar o assento",
                                                      Toast.LENGTH_LONG).show();
                                          }else{
                                          try {
                                              JSONObject jObj = new JSONObject(result);
                                              if(jObj.getString("id")!= null){ //checking if it has worked

                                                  Toast.makeText(getApplicationContext(),"Assento Reservado com Sucesso!",
                                                          Toast.LENGTH_LONG).show();

//                                                  Intent itn = new Intent(getApplicationContext(), PassagesActivity.class);
//                                                  itn.putExtra("userId",userId);
//                                                  itn.putExtra("token",token);
//
//                                                  startActivity(itn);

                                                  setResult(11);
                                                  finish();


                                              }else{
                                                  Toast.makeText(getApplicationContext(),"Erro ao Reservar o Assento",
                                                          Toast.LENGTH_LONG).show();
                                              }
                                          } catch (JSONException e) {
                                              e.printStackTrace();
                                          }
                                          }
                                      }
                                  };

                                   //Execute service for saving the seat
                                  SaveSeatService saveSeatService = new SaveSeatService(taskListener);
                                  saveSeatService.execute(params);

                                   Intent itn = new Intent(getApplicationContext(),PassagesActivity.class);
                                   itn.putExtra("userId",userId);
                                   itn.putExtra("token",token);

                                   startActivity(itn);

                              }else{
                                  //if the card was denied
                                  Toast.makeText(getApplicationContext(),"Cartao Reprovado",
                                          Toast.LENGTH_LONG).show();
                              }

                          } catch (JSONException e) {
                              e.printStackTrace();
                          }

                      }
                  };

                  PaymentService paymentService = new PaymentService(taskListener);
                  paymentService.execute(params);

              }else{
                  Toast.makeText(getApplicationContext(),"Dados Inválidos!",
                          Toast.LENGTH_LONG).show();
              }
            }
        });
    }

    private void binding() {
        numeroCartaoText = findViewById(R.id.numeroCartaoText);
        tarjaText = findViewById(R.id.tarjaText);
        anoText = findViewById(R.id.anoText);
        mesText = findViewById(R.id.mesText);
        valorEditText = findViewById(R.id.valorText);

        btnFinalizarPagamento = findViewById(R.id.btnFinalizarPagamento);

        Bundle bundle = getIntent().getExtras();
        vooId = (Integer) bundle.get("vooId");
        userId = (String) bundle.get("userId");
        token = (String) bundle.get("token");
        seat = (String) bundle.get("seat");
        valorPassagem = (double) bundle.get("valorPassagem");

        valorEditText.setText("Valor R$"+Double.toString(valorPassagem));


    }
}
