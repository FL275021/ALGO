package dominio;

import java.time.LocalDate;
import java.util.Objects;
import tads.ILista;

public class Calificacion implements Comparable<Calificacion> {

    private String cedula;
    private String codigoEvento;
    private int puntaje;
    private String comentario;

    public Calificacion() {

    }

    public Calificacion(String cedula, String codigoEvento, int puntaje, String comentario) {
        this.cedula = cedula;
        this.codigoEvento = codigoEvento;
        this.puntaje = puntaje;
        this.comentario = comentario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(String codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Calificacion)) {
            return false;
        }

        Calificacion ca = (Calificacion) obj;
        return this.cedula.equals(ca.getCedula()) && this.codigoEvento.equals(ca.getCodigoEvento());
    }

    @Override
    public String toString() {
        return cedula + "#" + codigoEvento + "#" + puntaje + "#" + comentario;
    }

    @Override
    public int compareTo(Calificacion unaCalificacion) {
        int comparacion = this.codigoEvento.compareTo(unaCalificacion.codigoEvento);
        return (comparacion != 0) ? comparacion : this.cedula.compareTo(unaCalificacion.cedula);
    }
}