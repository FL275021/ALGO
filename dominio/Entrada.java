package dominio;

import java.util.Objects;
import tads.ILista;
import tads.Lista;
import tads.Nodo;

public class Entrada implements Comparable<Entrada>, Identificador {

    private String codEvt;
    private String cedula;
    private boolean estado;
    private int numEntrada; 

    public Entrada() {

    }

    public Entrada(String codEvt, String cedula, boolean estado, int numEntrada) {
        this.codEvt = codEvt;
        this.cedula = cedula;
        this.estado = estado;
        this.numEntrada = numEntrada;
    }

    public String getCodigoEvt() {
        return codEvt;
    }

    public void setCodigoEvt(String codEvt) {
        this.codEvt = codEvt;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getNumEntrada() {
        return numEntrada;
    }

    public void setNumEntrada(int numEntrada) {
        this.numEntrada = numEntrada;
    }

    public boolean tieneEntrada(String cedula, String codEvt) {
        return this.cedula.equals(cedula) && this.codEvt.equals(codEvt);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entrada)) {
            return false;
        }

        Entrada e = (Entrada) obj;
        return this.numEntrada == e.numEntrada;
    }

    @Override
    public String toString() {
        return numEntrada + "#" + codEvt + "#" + cedula + "#" + estado;
    }

    @Override
    public int compareTo(Entrada unaEntrada) {
        return String.valueOf(this.numEntrada).compareTo(String.valueOf(unaEntrada.numEntrada));
    }

    @Override
    public String getIdentificador() {
        return String.valueOf(numEntrada);
    }

 
   
}