package com.valdiviezomazautp.lockerapp.Detalle;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.github.chrisbanes.photoview.PhotoView;
import com.valdiviezomazautp.lockerapp.BaseDeDatos.BDHelper;
import com.valdiviezomazautp.lockerapp.BaseDeDatos.Constants;
import com.valdiviezomazautp.lockerapp.R;

import java.util.Calendar;
import java.util.Locale;

public class Detalle_registro extends AppCompatActivity {

    TextView D_Titulo, D_Cuenta, D_NombreUsuario, D_SitioWeb, D_Nota, D_Tiempo_registro, D_Tiempo_actualizacion;
    EditText D_Password;

    ImageView D_Imagen;
    String id_registro;

    BDHelper helper;

    Dialog dialog;

    ImageButton Im_Ir_Web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bloquear captura de pantalla
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE , WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_detalle_registro);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        id_registro = intent.getStringExtra("Id_registro");
        Toast.makeText(this, "Id del registro: "+id_registro, Toast.LENGTH_SHORT).show();

        helper = new BDHelper(this);

        InicializarVariables();
        MostrarInformacionRegistro();

        /*Obtener titulo del registro*/
        String titulo_registro = D_Titulo.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(titulo_registro);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        D_Imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Visualizar();
            }
        });

        Im_Ir_Web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_pagina_web = D_SitioWeb.getText().toString().trim();

                if (!url_pagina_web.equals("")){
                    abrirPaginaWeb(url_pagina_web);
                }else {
                    Toast.makeText(Detalle_registro.this, "No existe una url", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void InicializarVariables(){
        D_Titulo = findViewById(R.id.D_Titulo);
        D_Cuenta = findViewById(R.id.D_Cuenta);
        D_NombreUsuario = findViewById(R.id.D_NombreUsuario);
        D_Password = findViewById(R.id.D_Password);
        D_SitioWeb = findViewById(R.id.D_SitioWeb);
        D_Nota = findViewById(R.id.D_Nota);
        D_Tiempo_registro = findViewById(R.id.D_Tiempo_registro);
        D_Tiempo_actualizacion = findViewById(R.id.D_Tiempo_actualizacion);
        D_Imagen = findViewById(R.id.D_Imagen);

        Im_Ir_Web = findViewById(R.id.Im_Ir_Web);

        dialog = new Dialog(this);
    }

    private void MostrarInformacionRegistro(){
        String consulta = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id_registro+"\"";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String id = ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                @SuppressLint("Range") String titulo = ""+cursor.getString(cursor.getColumnIndex(Constants.C_TITULO));
                @SuppressLint("Range") String cuenta = ""+cursor.getString(cursor.getColumnIndex(Constants.C_CUENTA));
                @SuppressLint("Range") String nombre_usuario = ""+cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE_USUARIO));
                @SuppressLint("Range") String password = ""+cursor.getString(cursor.getColumnIndex(Constants.C_PASSWORD));
                @SuppressLint("Range") String sitio_web = ""+cursor.getString(cursor.getColumnIndex(Constants.C_SITIO_WEB));
                @SuppressLint("Range") String nota = ""+cursor.getString(cursor.getColumnIndex(Constants.C_NOTA));
                @SuppressLint("Range") String imagen = ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGEN));
                @SuppressLint("Range") String t_registro = ""+cursor.getString(cursor.getColumnIndex(Constants.C_TIEMPO_REGISTRO));
                @SuppressLint("Range") String t_actualizacion = ""+cursor.getString(cursor.getColumnIndex(Constants.C_TIEMPO_ACTUALIZACION));

                /*Convertir tiempo a dia/mes/anio 12:00 am-pm*/

                /*Tiempo registro*/
                Calendar calendar_t_r = Calendar.getInstance(Locale.getDefault());
                calendar_t_r.setTimeInMillis(Long.parseLong(t_registro));
                String tiempo_registro = ""+ DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_t_r);

                /*Tiempo actualización*/
                Calendar calendar_t_a = Calendar.getInstance(Locale.getDefault());
                calendar_t_a.setTimeInMillis(Long.parseLong(t_actualizacion));
                String tiempo_actualizacion = ""+ DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_t_a);


                /*Setear la información en las vistas*/
                D_Titulo.setText(titulo);
                D_Cuenta.setText(cuenta);
                D_NombreUsuario.setText(nombre_usuario);
                D_Password.setText(password);
                D_Password.setEnabled(false);
                D_Password.setBackgroundColor(Color.TRANSPARENT);
                D_Password.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
                D_SitioWeb.setText(sitio_web);
                D_Nota.setText(nota);
                D_Tiempo_registro.setText(tiempo_registro);
                D_Tiempo_actualizacion.setText(tiempo_actualizacion);

                if (imagen.equals("null")){
                    D_Imagen.setImageResource(R.drawable.ic_imagen);
                }else {
                    D_Imagen.setImageURI(Uri.parse(imagen));
                }


            }while (cursor.moveToNext());

        }

        db.close();

    }

    private void Dialog_Visualizar(){
        PhotoView Visualizar_imagen;
        Button Btn_cerrar_imagen;

        dialog.setContentView(R.layout.cuadro_dialogo_visualizar_imagen);

        Visualizar_imagen = dialog.findViewById(R.id.Visualizar_imagen);
        Btn_cerrar_imagen = dialog.findViewById(R.id.Btn_cerrar_imagen);

        String consulta = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id_registro+"\"";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String imagen = ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGEN));

                if (imagen.equals("null")){
                    Visualizar_imagen.setImageResource(R.drawable.ic_imagen);
                }else {
                    Visualizar_imagen.setImageURI(Uri.parse(imagen));
                }
            }while (cursor.moveToNext());

        }

        db.close();

        Btn_cerrar_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);

    }

    private void abrirPaginaWeb(String url_pagina_web) {
        Intent navegar = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+url_pagina_web));
        startActivity(navegar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
