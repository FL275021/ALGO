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

public class Evento implements Comparable<Evento>, Identificador {

    private String codigoEvento;
    private String descripcion;
    private int aforoNecesario;
    private LocalDate fecha;
    private int cantEntradas;
    private Sala salaAsignada;
    private IListaOrdenada<Entrada> entradasVendidas;
    private ICola<Cliente> listaDeEspera; /////////
    private ILista<Calificacion> calificaciones;

    public Evento() {

    }

    public Evento(String codigoEvento, String descripcion, int aforoNecesario, LocalDate fecha, Sala salaAsignada) {
        this.codigoEvento = codigoEvento;
        this.descripcion = descripcion;
        this.aforoNecesario = aforoNecesario;
        this.fecha = fecha;
        this.salaAsignada = salaAsignada;
        this.cantEntradas = 0;
        this.entradasVendidas = new ListaOrdenada<>();
        this.listaDeEspera = new Cola<>();
        this.calificaciones = new Lista<>();
    }

    public String getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(String codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public int getCantEntradas() {
        return cantEntradas;
    }

    public void setCantEntradas(int cantEntradas) {
        this.cantEntradas = cantEntradas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAforoNecesario() {
        return aforoNecesario;
    }

    public void setAforoNecesario(int aforoNecesario) {
        this.aforoNecesario = aforoNecesario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Sala getSalaAsignada() {
        return salaAsignada;
    }

    public IListaOrdenada<Entrada> getEntradasVendidas() {
        return entradasVendidas;
    }

    public ICola<Cliente> getListaEspera() {
        return listaDeEspera;
    }
    
    public ILista<Calificacion> getCalificaciones()
    {
        return this.calificaciones;
    }

    public void agregarEntradaVendida(Entrada e) {
        entradasVendidas.agregar(e);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Evento)) {
            return false;
        }

        Evento e = (Evento) obj;
        return this.codigoEvento.equals(e.getCodigoEvento());
    }

    @Override
    public String toString() {
        return "Evento: " + codigoEvento + " - Descripcion: " + descripcion + " - Aforo: " + aforoNecesario + " - Fecha: " + fecha;
    }

    public String toListadoString() {
        String sala = (salaAsignada != null) ? salaAsignada.getNombre() : "No asignada";
        int disponibles = aforoNecesario - cantEntradas;
        int vendidas = cantEntradas;

        return codigoEvento + "-" + descripcion + "-" + sala + "-" + disponibles + "-" + vendidas;
    }

    @Override
    public int compareTo(Evento unaEvento) {
        return this.codigoEvento.compareTo(unaEvento.codigoEvento);
    }

    @Override
    public String getIdentificador() {
        return this.codigoEvento;
    }
}