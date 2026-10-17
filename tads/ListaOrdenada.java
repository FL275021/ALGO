package tads;

import dominio.Identificador;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ListaOrdenada<T extends Comparable<T>> implements IListaOrdenada<T> {

    private Nodo<T> inicio;
    private int cantElementos;

    public ListaOrdenada() {
        this.inicio = null;
        this.cantElementos = 0;
    }
 
    // ==================== AGREGAR ====================
    @Override
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo(dato);

        if (inicio == null || dato.compareTo(inicio.getDato()) < 0) {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
        } else {
            Nodo<T> actual = inicio;
            while (actual.getSiguiente() != null && dato.compareTo(actual.getSiguiente().getDato()) >= 0) {
                actual = actual.getSiguiente();
            }

            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }

        cantElementos++;
    }

    // ==================== ELIMINAR ====================
    @Override
    public void eliminar(T dato) {
        if (esVacia()) {
            return;
        }

        if (inicio.getDato().equals(dato)) {
            inicio = inicio.getSiguiente();
            cantElementos--;
            return;
        }

        Nodo<T> actual = inicio;
        while (actual.getSiguiente() != null && !actual.getSiguiente().getDato().equals(dato)) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            cantElementos--;
        }
    }

    // ==================== BUSCAR / OBTENER ====================
    @Override
    public T obtenerPorIndice(int indice) {
        if (indice < 0 || indice >= cantElementos) {
            return null;
        }

        Nodo<T> aux = inicio;
        int cont = 0;

        while (cont < indice) {
            aux = aux.getSiguiente();
            cont++;
        }

        return aux.getDato();
    }

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
        Nodo<T> aux = inicio;
        while (aux != null) {
            System.out.println(aux.getDato());
            aux = aux.getSiguiente();
        }
    }

    @Override
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
    public void forEach(Consumer<? super T> action) {
        IListaOrdenada.super.forEach(action); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Spliterator<T> spliterator() {
        return IListaOrdenada.super.spliterator(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    // ==================== NODO ====================
    private class Nodo<T> {

        private T dato;
        private Nodo<T> siguiente;

        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        public Nodo(T dato, Nodo<T> siguiente) {
            this.dato = dato;
            this.siguiente = siguiente;
        }

        public T getDato() {
            return dato;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }
    }
}
