/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tads;


import java.util.Iterator;


import dominio.Cliente;

/**
 *
 * @author Usuario 
 */
public class Cola<T> implements ICola<T> {

        /* A los atributos se les debe establecer visibilidad protected para poder acceder a ellos desde las subclases */
   
    private Nodo<T> primero = null;
    private Nodo<T> ultimo = null;
    protected int cantidadElementos;

    public Cola() {
        this.primero = null;
        this.ultimo = null;
        cantidadElementos = 0;
    }

    @Override
    public void enqueue(T dato) {
        Nodo<T> nodo = new Nodo<>(dato);
        
        if (esVacia()) {
            primero = nodo;
            ultimo = nodo;
        } else {
            ultimo.setSig(nodo);
            ultimo = nodo; 
        }
        cantidadElementos++;
    }

    @Override
    public T dequeue() {
        if (esVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }

        T dato = primero.getDato();
        primero = primero.getSig();
        
        if (primero == null) {
            ultimo = null;
        }
        
        cantidadElementos--;
        return dato;
    }

    @Override
    public T front() {
        if (esVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        
        return primero.getDato();
    }

    @Override 
    public boolean esVacia() {
        return (primero == null) && (ultimo == null);
    }

    @Override
    public int cantidadElementos() {
        return cantidadElementos;
    }

    @Override
    public void mostrar() {
        Nodo<T> aux = primero;
        while (aux != null) {            
            System.out.println(aux.getDato());
            aux = aux.getSig();
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo<T> aux = primero;
        while (aux != null) {
            sb.append(aux.getDato()).append("\n");
            aux = aux.getSig();
        }
        return sb.toString().trim();
    }

    @Override
    public Iterator<T> iterator() { ///////////////////////////
             return new Iterator<T>() {

            Nodo<T> aux = primero;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public T next() {
                T dato = aux.getDato();
                aux = aux.getSig();
                return dato;
            }
        };
    }

    @Override
    public T obtenerPorIndice(int i) {
       if (i < 0 || i >= cantidadElementos) {
            return null;
        }

        Nodo<T> aux = primero;
        int cont = 0;
        while (cont < i) {
            cont++;
            aux = aux.getSig();
        }
        return aux.getDato();
    }
}
