package com.valdiviezomazautp.lockerapp.Fragmentos;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.security.KeyStoreException;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.valdiviezomazautp.lockerapp.BaseDeDatos.BDHelper;
import com.valdiviezomazautp.lockerapp.BaseDeDatos.Constants;
import com.valdiviezomazautp.lockerapp.Login_usuario.Logeo_usuario;
import com.valdiviezomazautp.lockerapp.MainActivity;
import com.valdiviezomazautp.lockerapp.Modelo.Password;
import com.valdiviezomazautp.lockerapp.R;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.cert.CertificateException;

public class F_Ajustes extends Fragment {

    TextView Eliminar_Todos_Registros, Exportar_Archivo, Importar_Archivo, Cambiar_password_maestra;
    Dialog dialog, dialog_p_m;

    BDHelper bdHelper;

    String ordenarTituloAsc = Constants.C_TITULO + " ASC";

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF = "mi_pref";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_C_PASSWORD = "c_password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Bloquear captura de pantalla
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE , WindowManager.LayoutParams.FLAG_SECURE);
        View view = inflater.inflate(R.layout.fragment_f__ajustes, container, false);

        Eliminar_Todos_Registros = view.findViewById(R.id.Eliminar_Todos_Registros);
        Exportar_Archivo = view.findViewById(R.id.Exportar_Archivo);
        Importar_Archivo = view.findViewById(R.id.Importar_Archivo);
        Cambiar_password_maestra = view.findViewById(R.id.Cambiar_password_maestra);
        dialog = new Dialog(getActivity());
        dialog_p_m = new Dialog(getActivity());
        bdHelper = new BDHelper(getActivity());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        Eliminar_Todos_Registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Eliminar_Registros();
            }
        });

        Exportar_Archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Exportar archivo", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    ExportarRegistros();
                }else {
                    SolicitudPermisoExportar.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            }
        });

        Importar_Archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("¿Importar CSV?");
                builder.setMessage("Se eliminarán todos los registros actuales.");
                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                            bdHelper.EliminarTodosRegistros();
                            ImportarRegistros();
                        }else {
                            SolicitudPermisoImportar.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Importación cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();

            }
        });

        Cambiar_password_maestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Cambiar contraseña maestra", Toast.LENGTH_SHORT).show();
                CuadroDialogoPasswordMaestra();
            }
        });

        return view;
    }


    private void Dialog_Eliminar_Registros() {

        Button Btn_Si, Btn_Cancelar;

        dialog.setContentView(R.layout.cuadro_dialogo_eliminar_todos_registros);


        Btn_Si = dialog.findViewById(R.id.Btn_Si);
        Btn_Cancelar = dialog.findViewById(R.id.Btn_Cancelar);

        Btn_Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdHelper.EliminarTodosRegistros();
                startActivity(new Intent(getActivity(), MainActivity.class));
                Toast.makeText(getActivity(), "Se ha eliminado todos los registros", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);


    }

    private void ExportarRegistros() {
        //Nombre de la carpeta
        File carpeta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Password App");


        boolean carpetaCreada = false;

        if (!carpeta.exists()){
            //Si la carpeta no existe, creamos una nueva
            carpetaCreada = carpeta.mkdirs();
        }

        //Nombre del archivo
        String csvnombreArchivo = "Registros.csv";
        //Concatenar el nombre de la carpeta y del archivo
        String Carpeta_Archivo = carpeta + "/" + csvnombreArchivo;

        /*Obtener el registro que vamos a exportar*/
        ArrayList<Password> registroList = new ArrayList<>();
        registroList.clear();
        registroList = bdHelper.ObtenerTodosRegistros(ordenarTituloAsc);

        try {
            // Generar una clave secreta y un vector de inicialización (IV) para AES
            SecureRandom random = new SecureRandom();
            byte[] keyBytes = new byte[16];
            random.nextBytes(keyBytes);
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            byte[] ivBytes = new byte[16];
            random.nextBytes(ivBytes);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            // Crear instancia de cifrado AES
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            // Flujo de salida para escribir el archivo cifrado
            FileOutputStream fileOutputStream = new FileOutputStream(Carpeta_Archivo);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            // Escribir IV al archivo
            bufferedOutputStream.write(ivBytes);

            // Crear flujo de salida cifrado
            CipherOutputStream cipherOutputStream = new CipherOutputStream(bufferedOutputStream, cipher);

            // Escribir datos cifrados al archivo
            for (int i = 0; i < registroList.size(); i++) {
                cipherOutputStream.write(("" + registroList.get(i).getId() + "," +
                        registroList.get(i).getTitulo() + "," +
                        registroList.get(i).getCuenta() + "," +
                        registroList.get(i).getNombre_usuario() + "," +
                        registroList.get(i).getPassword() + "," +
                        registroList.get(i).getSitio_web() + "," +
                        registroList.get(i).getNota() + "," +
                        registroList.get(i).getImagen() + "," +
                        registroList.get(i).getT_registro() + "," +
                        registroList.get(i).getT_actualiacion() + "\n").getBytes());
            }

            // Cerrar flujo de salida cifrado
            cipherOutputStream.close();

            Toast.makeText(getActivity(), "Se ha exportado y cifrado el archivo CSV con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error al exportar y cifrar el archivo CSV: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private SecretKey obtenerClaveSecreta() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException, java.security.cert.CertificateException, UnrecoverableKeyException, java.security.KeyStoreException {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (java.security.KeyStoreException e) {
            throw new RuntimeException(e);
        }
        keyStore.load(null);

        // Nombre de la clave en el almacén de claves
        String alias = "mi_clave_maestra";

        Key key = keyStore.getKey(alias, null);

        if (key == null) {
            // Si la clave no existe, la creamos
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder = new KeyGenParameterSpec.Builder(alias,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(false)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(builder.build());
            }
            key = keyGenerator.generateKey();
        }

        return (SecretKey) key;
    }




    private void ImportarRegistros() {
        //Establecer la ruta
        String Carpeta_Archivo = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Password App/Registros.csv").getAbsolutePath();
        File file = new File(Carpeta_Archivo);
        if (file.exists()){
            // Si el respaldo existe
            try {
                CSVReader csvReader = new CSVReader(new FileReader(file.getAbsoluteFile()));
                String [] nextLine;
                while ((nextLine = csvReader.readNext())!=null){
                    String ids = nextLine[0];
                    String titulo = nextLine[1];
                    String cuenta = nextLine[2];
                    String nombre_usuario = nextLine[3];
                    String password = nextLine[4];
                    String sitio_web = nextLine[5];
                    String nota = nextLine[6];
                    String imagen = nextLine[7];
                    String tiempoR = nextLine[8];
                    String tiempoA = nextLine[9];

                    long id = bdHelper.insertarRegistro(
                            ""+titulo,
                            ""+ cuenta,
                            ""+ nombre_usuario,
                            ""+password,
                            ""+sitio_web,
                            ""+ nota,
                            ""+imagen,
                            ""+tiempoR,
                            ""+tiempoA);
                }
                Toast.makeText(getActivity(), "Archivo CSV importado con éxito", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(getActivity(), "No existe un respaldo", Toast.LENGTH_SHORT).show();
        }

    }

    private void CuadroDialogoPasswordMaestra() {
        //Establecer las vistas
        EditText Password_maestra;
        EditText Et_nuevo_password_maestra, Et_C_nuevo_password_maestra;
        Button Btn_cambiar_password_maestra, Btn_cancelar_password_maestra;

        String password_maestra_recuperada = sharedPreferences.getString(KEY_PASSWORD, null);

        //Hacemos la conexión con el cuadro de diálogo
        dialog_p_m.setContentView(R.layout.cuadro_dialogo_password_maestra);

        //Inicializar las vistas
        Password_maestra = dialog_p_m.findViewById(R.id.Password_maestra);
        Et_nuevo_password_maestra = dialog_p_m.findViewById(R.id.Et_nuevo_password_maestra);
        Et_C_nuevo_password_maestra = dialog_p_m.findViewById(R.id.Et_C_nuevo_password_maestra);
        Btn_cambiar_password_maestra = dialog_p_m.findViewById(R.id.Btn_cambiar_password_maestra);
        Btn_cancelar_password_maestra = dialog_p_m.findViewById(R.id.Btn_cancelar_password_maestra);

        Btn_cambiar_password_maestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtener los datos de los edittext
                String S_nuevo_password = Et_nuevo_password_maestra.getText().toString().trim();
                String S_c_nuevo_password = Et_C_nuevo_password_maestra.getText().toString().trim();

                /*Validación de datos*/
                if (S_nuevo_password.equals("")){
                    Toast.makeText(getActivity(), "Ingrese nueva contraseña", Toast.LENGTH_SHORT).show();
                }
                else if (S_c_nuevo_password.equals("")){
                    Toast.makeText(getActivity(), "Confirme nueva contraseña", Toast.LENGTH_SHORT).show();
                }
                else if (S_nuevo_password.length()<6){
                    Toast.makeText(getActivity(), "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_SHORT).show();
                }
                else if (!S_nuevo_password.equals(S_c_nuevo_password)){
                    Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    /*Pasar los nuevos datos a las llaves*/
                    editor.putString(KEY_PASSWORD, S_nuevo_password);
                    editor.putString(KEY_C_PASSWORD, S_c_nuevo_password);
                    editor.apply();
                    /*Salir de la aplicación, para iniciar sesión con la nueva contraseña*/
                    startActivity(new Intent(getActivity(), Logeo_usuario.class));
                    getActivity().finish();
                    Toast.makeText(getActivity(), "La contraseña maestra se ha cambiado", Toast.LENGTH_SHORT).show();
                    dialog_p_m.dismiss();
                }


            }
        });

        Btn_cancelar_password_maestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                dialog_p_m.dismiss();
            }
        });

        Password_maestra.setText(password_maestra_recuperada);
        Password_maestra.setEnabled(false);
        Password_maestra.setBackgroundColor(Color.TRANSPARENT);
        Password_maestra.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        dialog_p_m.show();
        dialog_p_m.setCancelable(false);
    }


    //Permiso exportar registro
    private ActivityResultLauncher<String> SolicitudPermisoExportar =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), Concede_permiso_exportar -> {
                if (Concede_permiso_exportar){
                    ExportarRegistros();
                }else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            });

    //Permiso importar registro
    private ActivityResultLauncher<String> SolicitudPermisoImportar =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), Concede_permiso_importar -> {
                if (Concede_permiso_importar){
                    ImportarRegistros();
                }else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            });
}
