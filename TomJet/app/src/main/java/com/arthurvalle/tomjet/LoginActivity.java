package com.arthurvalle.tomjet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arthurvalle.tomjet.service.FlightListService;
import com.arthurvalle.tomjet.service.LoginService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button btnRedirectToRegister, btnLogin;
    EditText edtUser, edtPassword;
    ProgressBar loginSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();

        btnRedirectToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginSpinner.setVisibility(View.VISIBLE);

                String[] params = {edtUser.getText().toString(), edtPassword.getText().toString()};

                //loging in
                LoginService.TaskListener listener = new LoginService.TaskListener() {
                    @Override
                    public void onFinished(String result) {
                        Log.e("result", result);
                        loginSpinner.setVisibility(View.INVISIBLE);

                        //checking if the user is authenticated
                        if (!result.equals("404")){
                            Intent i = new Intent(getApplicationContext(), FlightsListActivity.class);

                            try {
                                JSONObject reader = new JSONObject(result);
                                String token = reader.getString("token");
                                String userId = reader.getString("id");
                                i.putExtra("token",token);
                                i.putExtra("userId",userId);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                         startActivity(i);

                        }else{
                            Toast.makeText(getApplicationContext(),"Usuário ou Senha Inválidos!",
                            Toast.LENGTH_LONG).show();
                        }


                    }
                };

                LoginService loginService = new LoginService(listener);

                loginService.execute(params);
            }
        });

    }

    private void binding() {
        btnRedirectToRegister = findViewById(R.id.btnRedirectToRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        loginSpinner = findViewById(R.id.loginSpinner);
    }
}
