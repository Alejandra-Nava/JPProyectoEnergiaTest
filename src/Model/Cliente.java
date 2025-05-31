package Model;

import java.util.ArrayList;

public class Cliente {
    private String id;
    private String tipoId;
    private String correo;
    private String direccion;
    private String nombre; // ✅ NUEVO campo
    private ArrayList<Registrador> registradores;

    // ✅ Constructor actualizado (agregado el campo nombre)
    public Cliente(String id, String tipoId, String correo, String direccion, String nombre) {
        this.id = id;
        this.tipoId = tipoId;
        this.correo = correo;
        this.direccion = direccion;
        this.nombre = nombre;
        this.registradores = new ArrayList<>();
    }

    public void agregarRegistrador(Registrador r) {
        registradores.add(r);
    }

    public ArrayList<Registrador> getRegistradores() {
        return registradores;
    }

    public String getId() {
        return id;
    }

    public String getTipoId() {
        return tipoId;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombre() { // ✅ NUEVO getter
        return nombre;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) { // ✅ NUEVO setter
        this.nombre = nombre;
    }
}
