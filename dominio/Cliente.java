package dominio;

import java.util.Objects;
import tads.ILista;
import tads.Lista;
import tads.Nodo;

public class Cliente implements Comparable<Cliente>, Identificador {

    private String nombre;
    private String cedula;
    private ILista<Entrada> entradasCompradas;

    public Cliente(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.entradasCompradas = new Lista<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public ILista<Entrada> getEntradasCompradas() {
        return entradasCompradas;
    }

    public void agregarEntrada(Entrada e) {
        entradasCompradas.agregarFinal(e);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cliente)) {
            return false;
        }

        Cliente c = (Cliente) obj;
        return this.cedula.equals(c.getCedula());
    }

    @Override
    public String toString() {
        return "Cedula: " + cedula  + " - " +  "Nombre: " + nombre;
    }

    @Override
    public int compareTo(Cliente unaCliente) {
        return this.cedula.compareTo(unaCliente.cedula);
    }

    @Override
    public String getIdentificador() {
        return this.cedula;
    }

     
}
