package dominio;

import java.time.LocalDate;
import java.util.Objects;
import tads.ILista;
import tads.Lista;

public class Despliegue implements Comparable<Despliegue>, Identificador {
//despliegue(String id(incremental), String codigo, String descripcion, boolean cuantizado, Modalidad modalidad)
    private String codigo;
    private String descripcion;
    private boolean cuantizado;
    private Modalidad modalidad;


    public Despliegue() {

    }

    public Despliegue(String codigo, String descripcion, boolean cuantizado, String modalidadTexto) {
        this.codigo = codigo;
        this.descripcion = descripcion;
         this.cuantizado = cuantizado;
          this.modalidadTexto = modalidadTexto;
    }

    public String getNombre() {
        return codigo;
    }

    public void setNombre(String codigo) {
        this.codigo = codigo;
    }

    public int getCapacidad() {
        return descripcion;
    }

    public void setCapacidad(int descripcion) {
        this.descripcion = descripcion;
    }

    public ILista<LocalDate> getFechasOcupadas() {
        return fechasOcupadas;
    }

    public boolean estaOcupadaEnFecha(LocalDate fecha) {
        for (LocalDate f : fechasOcupadas) {
            if (f.equals(fecha)) {
                return true;
            }
        }
        return false;
    }

    public void ocuparFecha(LocalDate fecha) {
        if (!estaOcupadaEnFecha(fecha)) {
            fechasOcupadas.agregarFinal(fecha);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Despliegue)) {
            return false;
        }

        Despliegue s = (Despliegue) obj;
        return this.codigo.equals(s.getNombre());
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + " - " + "Descripcion: " + descripcion;
    }

    @Override
    public int compareTo(Despliegue unaSala) {
        return this.codigo.compareTo(unaSala.codigo);
    }

    @Override
    public String getIdentificador() {
        return this.codigo;
    }
}
