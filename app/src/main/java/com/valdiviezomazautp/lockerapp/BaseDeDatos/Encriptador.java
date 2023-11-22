package com.valdiviezomazautp.lockerapp.BaseDeDatos;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encriptador {

    private static final String ALGORITMO = "AES";
    private static final String CLAVE_SECRETA = "TuClaveSecreta123"; // Cambia esto y guárdalo de forma segura

    public static String encriptar(String texto) throws Exception {
        Key clave = generarClave();
        Cipher cifrador = Cipher.getInstance(ALGORITMO);
        cifrador.init(Cipher.ENCRYPT_MODE, clave);
        byte[] textoCifrado = cifrador.doFinal(texto.getBytes());
        return Base64.encodeToString(textoCifrado, Base64.DEFAULT);
    }

    public static String desencriptar(String textoCifrado) throws Exception {
        Key clave = generarClave();
        Cipher cifrador = Cipher.getInstance(ALGORITMO);
        cifrador.init(Cipher.DECRYPT_MODE, clave);
        byte[] texto = cifrador.doFinal(Base64.decode(textoCifrado, Base64.DEFAULT));
        return new String(texto);
    }

    private static Key generarClave() {
        return new SecretKeySpec(CLAVE_SECRETA.getBytes(), ALGORITMO);
    }
}

