package dominio;

import java.time.LocalDate;
import tads.Cola;
import tads.ICola;
import tads.ILista;
import tads.IListaOrdenada;
import tads.IPila;
import tads.Lista;
import tads.ListaOrdenada;
import tads.Pila;

public class Modalidad implements Comparable<Modalidad>, Identificador {

    private String id;
    private String texto;

    public Modalidad() {

    }
//modalidad(String id, String texto) "indica el tipo de modelo: Texto, Vision, Audio, Mulotimodal, Otro"
    public Modalidad(String id, String texto) {
        this.id = id;
        this.texto = texto;
      
    }

    public String getIdModalidad() {
        return id;
    }

    public void setIdModalidad(String id) {
        this.id = id;
    }

        public String gettextoModalidad() {
        return texto;
    }

    public void settextoModalidad(String texto) {
        this.texto = texto;
    }



    
 /*    public ILista<coso> getcosos()
    {
        return this.cosos;
    } */

  /*   public void agregarCoso(Entrada e) {
        cosos.agregar(e);
    } */

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Modalidad)) {
            return false;
        }

        Modalidad e = (Modalidad) obj;
        return this.id.equals(e.getIdModalidad());
    }

    @Override
    public String toString() {
        return "Id Modalidad: " + id + " - Descripcion: " + texto;
    }

    public String toListadoString() {
     
        return id + "-" + texto;
    }

    @Override
    public int compareTo(Modalidad unaModalidad) {
        return this.id.compareTo(unaModalidad.id);
    }

    @Override
    public String getIdentificador() {
        return this.id;
    }
}