package dominio;

import java.util.Objects;
import tads.ILista;
import tads.Lista;
import tads.Nodo;
//String idDespliegue, String codigoRegion
public class DespliegueEnRegion implements Comparable<Entrada>, Identificador {

    private int ID;
    private String idDespliegue;
    private String codigoRegion;


    public DespliegueEnRegion() {

    }

    public DespliegueEnRegion(String idDespliegue, String codigoRegion) {
        this.idDespliegue = idDespliegue;
        this.codigoRegion = codigoRegion;

    }

    public String getidDespliegue() {
        return idDespliegue;
    }

    public void setidDespliegue(String idDespliegue) {
        this.idDespliegue = idDespliegue;
    }

    public String getcodigoRegion() {
        return codigoRegion;
    }

    public void setcodigoRegion(String codigoRegion) {
        this.codigoRegion = codigoRegion;
    }

    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }





    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DespliegueEnRegion)) {
            return false;
        }

        DespliegueEnRegion e = (DespliegueEnRegion) obj;
        return this.ID == e.ID;
    }

    @Override
    public String toString() {
        return idDespliegue + "#" + codigoRegion  ;
    }

    @Override
    public int compareTo(Entrada unaEntrada) {
        return String.valueOf(this.ID).compareTo(String.valueOf(unaEntrada.ID));
    }

    @Override
    public String getIdentificador() {
        return String.valueOf(ID);
    }

 
   
}