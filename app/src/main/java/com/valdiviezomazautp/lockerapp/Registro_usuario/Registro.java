package com.valdiviezomazautp.lockerapp.Registro_usuario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valdiviezomazautp.lockerapp.Login_usuario.Logeo_usuario;
import com.valdiviezomazautp.lockerapp.MainActivity;
import com.valdiviezomazautp.lockerapp.R;


public class Registro extends AppCompatActivity {

    EditText EtPasswordU, EtCPassword;
    Button BtnRegistrar;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF = "mi_pref";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_C_PASSWORD = "c_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bloquear captura de pantalla
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE , WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_registro);
        InicializarVariables();
        VerificarPasswordMaestra();

        BtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String S_password = EtPasswordU.getText().toString().trim();
                String S_C_password = EtCPassword.getText().toString().trim();

                //Validar los campos
                if (TextUtils.isEmpty(S_password)){
                    Toast.makeText(Registro.this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
                }
                else if (S_password.length()<6){
                    Toast.makeText(Registro.this, "Contraseña muy corta", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(S_C_password)){
                    Toast.makeText(Registro.this, "Confirme contraseña", Toast.LENGTH_SHORT).show();
                }
                else if (!S_password.equals(S_C_password)){
                    Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Pasamos los datos introducidos en los edittext al SP
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_PASSWORD, S_password);
                    editor.putString(KEY_C_PASSWORD, S_C_password);
                    editor.apply();

                    Toast.makeText(Registro.this, "KEY PASSWORD"+S_password, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Registro.this, "KEY_C_PASSWORD"+ S_C_password, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Registro.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    private void InicializarVariables(){
        EtPasswordU = findViewById(R.id.EtPasswordU);
        EtCPassword = findViewById(R.id.EtCPassword);
        BtnRegistrar = findViewById(R.id.BtnRegistrar);
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    }

    private void VerificarPasswordMaestra(){
        String mipassword = sharedPreferences.getString(KEY_PASSWORD, null);

        //Si el usuario ya tiene una contraseña maestra registrada
        if (mipassword!=null){
            Intent intent = new Intent(Registro.this, Logeo_usuario.class);
            startActivity(intent);
            finish();
        }
    }
}