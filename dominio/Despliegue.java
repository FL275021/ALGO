package dominio;

import java.time.LocalDate;
import java.util.Objects;
import tads.ILista;
import tads.Lista;

public class Despliegue implements Comparable<Despliegue>, Identificador {
//despliegue(String id(incremental), String codigo, String descripcion, boolean cuantizado, Modalidad modalidad)
    private String codigo;  //solo letras, mayúsculas o minúsculas, y números
    private String descripcion;
    private boolean cuantizado;
    private String modalidadTexto;


    public Despliegue() {

    }

    public Despliegue(String codigo, String descripcion, boolean cuantizado, String modalidadTexto) {
        this.codigo = codigo;
        this.descripcion = descripcion;
         this.cuantizado = cuantizado;
          this.modalidadTexto = modalidadTexto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

       public boolean getCuantizado() {
        return cuantizado;
    }

    public void setCuantizado(boolean cuantizado) {
        this.cuantizado = cuantizado;
    }

           public String getModalidadDespliegue() {
        return modalidadTexto;
    }

    public void setModalidadDespliegue(String modalidadTexto) {
        this.modalidadTexto = modalidadTexto;
    }


   
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Despliegue)) {
            return false;
        }

        Despliegue s = (Despliegue) obj;
        return this.codigo.equals(s.getCodigo());
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + " - " + "Descripcion: " + descripcion + "Cuantizado: " + cuantizado + "Modalidad: " + modalidadTexto;
    }

    @Override
    public int compareTo(Despliegue unDespliegue) {
        return this.codigo.compareTo(unDespliegue.codigo);
    }

    @Override
    public String getIdentificador() {
        return this.codigo;
    }
}
