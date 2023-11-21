package com.valdiviezomazautp.lockerapp.Login_usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.valdiviezomazautp.lockerapp.MainActivity;
import com.valdiviezomazautp.lockerapp.R;


public class Logeo_usuario extends AppCompatActivity {

    EditText EtPasswordU;
    Button BtnIngresar, BtnInicioSesionBiometrico;
    SharedPreferences sharedPreferences;
    ImageButton Ib_Aviso;

    private static final String SHARED_PREF = "mi_pref";
    private static final String KEY_PASSWORD = "password";

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bloquear captura de pantalla
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE , WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_logeo_usuario);
        InicializarVariables();

        BtnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String S_password = EtPasswordU.getText().toString().trim();
                //Obtener la contraseña maestra almacenada en SP
                String password_SP = sharedPreferences.getString(KEY_PASSWORD, null);

                if (S_password.equals("")){
                    Toast.makeText(Logeo_usuario.this, "Campo es obligatorio", Toast.LENGTH_SHORT).show();
                }
                else if (!S_password.equals(password_SP)){
                    Toast.makeText(Logeo_usuario.this, "La contraseña no es la correcta", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Logeo_usuario.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        biometricPrompt = new BiometricPrompt(Logeo_usuario.this, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(Logeo_usuario.this, "No existen huellas dactilares registradas", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(Logeo_usuario.this, "Autenticación biométrica exitosa, Bienvedido(a)!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Logeo_usuario.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(Logeo_usuario.this, "Falló la autenticación", Toast.LENGTH_SHORT).show();
            }
        });

        /*Configurar comportamiento del aviso biométrico*/
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación biométrica")
                .setNegativeButtonText("Cancelar")
                .build();

        BtnInicioSesionBiometrico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

        Ib_Aviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Logeo_usuario.this);
                builder.setTitle("Importante");
                builder.setMessage("Esta funcionalidad sólo está disponible con huella dactilares previamente registradas en su dispositivo");
                builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
            }
        });
    }

    private void InicializarVariables(){
        EtPasswordU = findViewById(R.id.EtPasswordU);
        BtnIngresar = findViewById(R.id.BtnIngresar);
        BtnInicioSesionBiometrico = findViewById(R.id.BtnInicioSesionBiometrico);
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Ib_Aviso = findViewById(R.id.Ib_Aviso);
    }
}