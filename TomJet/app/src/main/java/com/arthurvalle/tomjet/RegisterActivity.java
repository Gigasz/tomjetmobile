package com.arthurvalle.tomjet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arthurvalle.tomjet.service.RegisterService;

/**
 * Created by arthurvalle on 24/06/2018.
 */

public class RegisterActivity extends AppCompatActivity {
    EditText edtRegisterName, edtRegisterLogin, edtRegisterEmail, edtRegisterPassword, edtRegisterPasswordConfirm;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] params = {edtRegisterEmail.getText().toString(), edtRegisterLogin.getText().toString(), edtRegisterName.getText().toString(), edtRegisterPassword.getText().toString()};

                RegisterService.TaskListener listener = new RegisterService.TaskListener() {
                    @Override
                    public void onFinished(String result) {
                        Log.d("result: ", result);
                        Toast.makeText(getApplicationContext(),"Usuário Registrado!",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                };

                RegisterService registerService = new RegisterService(listener);

                registerService.execute(params);
            }
        });
    }

    private void binding() {
        edtRegisterName = findViewById(R.id.edtRegisterName);
        edtRegisterLogin = findViewById(R.id.edtRegisterLogin);
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail);
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword);
        edtRegisterPasswordConfirm = findViewById(R.id.edtRegisterPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);
    }
}
