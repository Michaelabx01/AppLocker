package com.valdiviezomazautp.lockerapp.OpcionesPassword;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.valdiviezomazautp.lockerapp.BaseDeDatos.BDHelper;
import com.valdiviezomazautp.lockerapp.MainActivity;
import com.valdiviezomazautp.lockerapp.R;

public class Agregar_Password extends AppCompatActivity {

    EditText EtTitulo, EtCuenta, EtNombreUsuario, EtPassword, EtSitioWeb, EtNota;
    ImageView Imagen;
    Button Btn_Adjuntar_Imagen;

    String id, titulo, cuenta, nombre_usuario, password, sitio_web, nota, tiempo_registro, tiempo_actualizacion;
    private boolean MODO_EDICION = false;

    private BDHelper bdHelper;

    Uri imagenUri = null;

    ImageView Iv_imagen_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_password);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        InicializarVariables();
        ObtenerInformacion();

        Btn_Adjuntar_Imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarCuadroDialogo();
            }
        });
    }

    private void InicializarVariables() {
        // Inicialización de variables
        EtTitulo = findViewById(R.id.EtTitulo);
        EtCuenta = findViewById(R.id.EtCuenta);
        EtNombreUsuario = findViewById(R.id.EtNombreUsuario);
        EtPassword = findViewById(R.id.EtPassword);
        EtSitioWeb = findViewById(R.id.EtSitioWeb);
        EtNota = findViewById(R.id.EtNota);

        Imagen = findViewById(R.id.Imagen);
        Btn_Adjuntar_Imagen = findViewById(R.id.Btn_Adjuntar_Imagen);

        Iv_imagen_eliminar = findViewById(R.id.Iv_eliminar_imagen);

        bdHelper = new BDHelper(this);
    }

    private void ObtenerInformacion(){
        Intent intent = getIntent();
        MODO_EDICION = intent.getBooleanExtra("MODO_EDICION", false);

        if (MODO_EDICION){
            //Verdadero
            id = intent.getStringExtra("ID");
            titulo = intent.getStringExtra("TITULO");
            cuenta = intent.getStringExtra("CUENTA");
            nombre_usuario = intent.getStringExtra("NOMBRE_USUARIO");
            password = intent.getStringExtra("PASSWORD");
            sitio_web = intent.getStringExtra("SITIO_WEB");
            nota = intent.getStringExtra("NOTA");
            imagenUri = Uri.parse(intent.getStringExtra("IMAGEN"));
            tiempo_registro = intent.getStringExtra("T_REGISTRO");
            tiempo_actualizacion = intent.getStringExtra("T_ACTUALIZACION");

            /*Seteamos la información recuperada en las vistas*/
            EtTitulo.setText(titulo);
            EtCuenta.setText(cuenta);
            EtNombreUsuario.setText(nombre_usuario);
            EtPassword.setText(password);
            EtSitioWeb.setText(sitio_web);
            EtNota.setText(nota);

            /*Si la imagen no existe*/
            if (imagenUri.toString().equals("null")){
                Imagen.setImageResource(R.drawable.ic_imagen);
                Iv_imagen_eliminar.setVisibility(View.VISIBLE);
            }
            /*Si la imagen existe*/
            else {
                Imagen.setImageURI(imagenUri);
                Iv_imagen_eliminar.setVisibility(View.VISIBLE);
            }

            Iv_imagen_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imagenUri = null;
                    Imagen.setImageResource(R.drawable.ic_imagen);
                    Toast.makeText(Agregar_Password.this, "Imagen eliminada", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            //Falso, se agrega un nuevo registro
        }
    }

    private void MostrarCuadroDialogo() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cuadro_dialogo_elegir_imagen);

        Button btnElegirGaleria = dialog.findViewById(R.id.Btn_Elegir_Galeria);
        Button btnElegirCamara = dialog.findViewById(R.id.Btn_Elegir_Camara);

        btnElegirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeleccionarImagenDesdeGaleria();
                dialog.dismiss();
            }
        });

        btnElegirCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SolicitarPermisoCamara();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void SeleccionarImagenDesdeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*"); //solo mostramos imagenes
        startActivityForResult(intent.createChooser(intent, "Seleccionar imagen"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Este método se llamará cuando se seleccione la imagen de la galería
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                //Obtener la imagen
                imagenUri = data.getData();
                //Establecer la imagen
                Imagen.setImageURI(imagenUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void SolicitarPermisoCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            TomarFotografia();
        } else {
            SolicitudPermisoCamara.launch(Manifest.permission.CAMERA);
        }
    }





    private void Agregar_Actualizar_R(){
        /*Obtener datos de entrada*/
        titulo = EtTitulo.getText().toString().trim();
        cuenta = EtCuenta.getText().toString().trim();
        nombre_usuario = EtNombreUsuario.getText().toString().trim();
        password = EtPassword.getText().toString().trim();
        sitio_web = EtSitioWeb.getText().toString().trim();
        nota = EtNota.getText().toString().trim();

        if (MODO_EDICION){
            //Actualizar el registro
            /*Tiempo del dispositivo*/
            String tiempo_actual = ""+ System.currentTimeMillis();
            bdHelper.actualizarRegistro(
                    ""+ id,
                    ""+ titulo,
                    ""+ cuenta,
                    ""+ nombre_usuario,
                    ""+ password,
                    ""+ sitio_web,
                    ""+ nota,
                    ""+ imagenUri,
                    ""+ tiempo_registro,
                    ""+ tiempo_actual
            );

            Toast.makeText(this, "Actualizado con éxito", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Agregar_Password.this, MainActivity.class));
            finish();

        }

        else {
            //Agregar un nuev registro
            if (!titulo.equals("")){
                /*Obtener el tiempo del dispositivo*/
                String tiempo = ""+System.currentTimeMillis();
                long id = bdHelper.insertarRegistro(
                        "" + titulo,
                        "" + cuenta,
                        "" + nombre_usuario,
                        ""+ password,
                        ""+ sitio_web,
                        ""+ nota,
                        "" + imagenUri,
                        "" + tiempo,
                        "" + tiempo
                );

                Toast.makeText(this, "Se ha guardado con éxito: ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Agregar_Password.this, MainActivity.class));
                finish();
            }
            else {
                EtTitulo.setError("Campo obligatorio");
                EtTitulo.setFocusable(true);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Guardar_Password){
            Agregar_Actualizar_R();
        }
        return super.onOptionsItemSelected(item);
    }

    private void TomarFotografia() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nueva imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción");
        imagenUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);
        camaraActivityResultLauncher.launch(intent);

    }

    private ActivityResultLauncher<Intent> camaraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Imagen.setImageURI(imagenUri);
                    } else {
                        Toast.makeText(Agregar_Password.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private  ActivityResultLauncher<String> SolicitudPermisoCamara =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), Concede_permiso ->{
                if (Concede_permiso){
                    TomarFotografia();
                }else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            });
}
