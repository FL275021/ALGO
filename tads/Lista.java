package tads;

import dominio.Entrada;
import dominio.Identificador;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Lista<T> implements ILista<T> {

    /* A los atributos se les debe establecer visibilidad protected para poder acceder a ellos desde las subclases */
    protected Nodo<T> inicio;
    protected int cantElementos;
 
    // ==================== AGREGAR ====================
    @Override
    public void agregarInicio(T dato) {
        inicio = new Nodo<>(dato, inicio);
        cantElementos++;
    }

    @Override
    public void agregarFinal(T dato) {
        Nodo nuevo = new Nodo(dato);

        if (inicio == null) {
            inicio = nuevo;
        } else {
            Nodo aux = inicio;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
        cantElementos++;
    }

    // ==================== ELIMINAR ====================
    @Override
    public void eliminarInicio() {
        if (!esVacia()) {
            inicio = inicio.getSiguiente();
            cantElementos--;
        }
    }

    @Override
    public void eliminarDato(T dato) {
        if (esVacia()) {
            return;
        }

        if (inicio.getDato().equals(dato)) {
            eliminarInicio();
            return;
        }

        Nodo<T> aux = inicio;
        while (aux.getSiguiente() != null && !dato.equals(aux.getSiguiente().getDato())) {
            aux = aux.getSiguiente();
        }

        if (aux.getSiguiente() != null) {
            aux.setSiguiente(aux.getSiguiente().getSiguiente());
            cantElementos--;
        }
    }

    // ==================== BUSCAR / OBTENER ====================
    @Override
    public T obtenerDato(T dato) {
        Nodo<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return aux.getDato();
            }
            aux = aux.getSiguiente();
        }
        return null;
    }

    @Override
    public T obtenerPorIndice(int indice) {
        if (indice < 0 || indice >= cantElementos) {
            return null;
        }

        Nodo<T> aux = inicio;
        int cont = 0;
        while (cont < indice) {
            cont++;
            aux = aux.getSiguiente();
        }
        return aux.getDato();
    }

    /* 
    public T obtenerPorIndice(String str) {
        Nodo<T> nodoAux = inicio;
        while (nodoAux != null) {
            T dato = nodoAux.getDato();
            if (dato instanceof Nodo && ((Nodo) dato).getNombre().equals(str)) {
                return dato;
            }
            nodoAux = nodoAux.getSiguiente();
        }
        return null; 
    }
     */
    @Override
    public T buscarPorIdentificador(String str) {
        Nodo<T> aux = inicio;

        while (aux != null) {
            T dato = aux.getDato();

            if (dato instanceof Identificador) {
                Identificador id = (Identificador) dato;
                if (id.getIdentificador().equals(str)) {
                    return dato;
                }
            }

            aux = aux.getSiguiente();
        }

        return null;
    }

    @Override
    public boolean existe(T dato) {
        return obtenerDato(dato) != null;
    }

    // ==================== UTILIDADES ====================
    @Override
    public int cantidadElementos() {
        return cantElementos;
    }

    @Override
    public boolean esVacia() {
        return cantElementos == 0;
    }

    @Override
    public void mostrar() {
        mostrarRec(this.inicio);
        System.out.println("");
    }

    private void mostrarRec(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.getDato() + " ");
            mostrarRec(nodo.getSiguiente());
        }
    }
     
    @Override
    public String mostrarInvertido()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = cantElementos - 1; i >= 0; i--) {
            sb.append(obtenerPorIndice(i)).append("\n");
        }
        return sb.toString().trim();
    }
            
    /*
    No te parece mejor trabajarlo asi el mostrar?
    @Override
    public void mostrar() {
        Nodo aux = inicio;
        while (aux != null) {
            System.out.println(aux.getDato() + " ");
            aux = aux.getSiguiente();
        }
    }
     */
    
    
    
    
    
    @Override
    //Supongo que lo queres para hacer debugging
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo<T> aux = inicio;

        while (aux != null) {
            sb.append(aux.getDato().toString());
            if (aux.getSiguiente() != null) {
                sb.append(" #");
            }
            aux = aux.getSiguiente();
        }

        return sb.toString();
    }

    // ==================== ITERABLE ====================
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Nodo<T> aux = inicio;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public T next() {
                T dato = aux.getDato();
                aux = aux.getSiguiente();
                return dato;
            }
        };
    }

     @Override
    public ILista<T> invertirLista(ILista<T> original) {
    ILista<T> invertida = new Lista<>();
    for (int i = original.cantidadElementos() - 1; i >= 0; i--) {
        invertida.agregarFinal(original.obtenerPorIndice(i));
    }
    return invertida;
}


    @Override
    public void forEach(Consumer<? super T> action) {
        ILista.super.forEach(action); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Spliterator<T> spliterator() {
        return ILista.super.spliterator(); //To change body of generated methods, choose Tools | Templates.
    }

    // ==================== NODO ====================
    private class Nodo<T> {

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

        public Nodo<T> getSiguiente() {
            return sig;
        }

        public void setSiguiente(Nodo<T> sig) {
            this.sig = sig;
        }
    }
}
