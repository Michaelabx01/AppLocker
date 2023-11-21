package com.valdiviezomazautp.lockerapp.Modelo;

public class Password {

    String id, titulo, cuenta, nombre_usuario, password, sitio_web, nota, imagen, t_registro, t_actualiacion;

    public Password(String id, String titulo, String cuenta, String nombre_usuario, String password, String sitio_web, String nota, String imagen, String t_registro, String t_actualiacion) {
        this.id = id;
        this.titulo = titulo;
        this.cuenta = cuenta;
        this.nombre_usuario = nombre_usuario;
        this.password = password;
        this.sitio_web = sitio_web;
        this.nota = nota;
        this.imagen = imagen;
        this.t_registro = t_registro;
        this.t_actualiacion = t_actualiacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSitio_web() {
        return sitio_web;
    }

    public void setSitio_web(String sitio_web) {
        this.sitio_web = sitio_web;
    }

    public String getNota() {
        return nota;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getT_registro() {
        return t_registro;
    }

    public void setT_registro(String t_registro) {
        this.t_registro = t_registro;
    }

    public String getT_actualiacion() {
        return t_actualiacion;
    }

    public void setT_actualiacion(String t_actualiacion) {
        this.t_actualiacion = t_actualiacion;
    }
}
