package tads;

/* La clase Nodo es una inner class dentro de la clase
ListaImp, debe tener visibilidad privada para poder
acceder desde la subclase */

public class Nodo<T> {

    private T dato;
    private Nodo<T> sig;

    public Nodo(T dato) {
        this.dato = dato;
        this.sig = null;  
    }

    public Nodo(T dato, Nodo<T> sig) {
        this.dato = dato;
        this.sig = sig;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    } 

    public Nodo<T> getSig() {
        return sig;
    }

    public void setSig(Nodo<T> sig) {
        this.sig = sig;
    }

    
    
}
