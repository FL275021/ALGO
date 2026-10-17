package dominio;

import java.util.Objects;
import tads.ILista;
import tads.Lista;
import tads.Nodo;

public class Region implements Comparable<Region>, Identificador {

    private String nombre;
    private String cedula;
    private ILista<DespliegueEnRegion> entradasCompradas;

    public Region(String cedula, String nombre) {
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

    public ILista<DespliegueEnRegion> getEntradasCompradas() {
        return entradasCompradas;
    }

    public void agregarEntrada(DespliegueEnRegion e) {
        entradasCompradas.agregarFinal(e);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Region)) {
            return false;
        }

        Region c = (Region) obj;
        return this.cedula.equals(c.getCedula());
    }

    @Override
    public String toString() {
        return "Cedula: " + cedula  + " - " +  "Nombre: " + nombre;
    }

    @Override
    public int compareTo(Region unaCliente) {
        return this.cedula.compareTo(unaCliente.cedula);
    }

    @Override
    public String getIdentificador() {
        return this.cedula;
    }

     
}
