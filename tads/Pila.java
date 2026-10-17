/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tads;

import java.util.Iterator;

import dominio.Entrada;

/**
 *
 * @author Usuario 
 */
public class Pila<T> implements IPila<T> {

    protected Nodo<T> tope;
    protected int cantidadElementos;

    public Pila() {
        this.tope = null;
        cantidadElementos = 0;
    }

    @Override
    public void push(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.setSig(tope);
        tope = nuevoNodo;
        cantidadElementos++;
    }

    @Override
    public T pop() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
 
        T dato = tope.getDato();
        tope = tope.getSig();
        cantidadElementos--;
        return dato;
    }

    @Override
    public T top() {
        return esVacia() ? null : tope.getDato();
    }
    
    public void vaciar() {
        this.tope = null;
        this.cantidadElementos = 0;
    }

    @Override
    public boolean esVacia() {
        return (this.tope == null);
    }

    @Override
    public int cantidadElementos() {
        return cantidadElementos;
    }

    @Override
    public void mostrar() {
        Nodo<T> aux = tope;
        while (aux != null) {            
            System.out.println(aux.getDato());
            aux = aux.getSig();
        }
    } 

     @Override
    public Iterator<T> iterator() { /////////////////////////// Se hace iterable para poder recorrer la pila con un for 
             return new Iterator<T>() {

            Nodo<T> aux = tope;

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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo<T> aux = tope;
        while (aux != null) {
            sb.append(aux.getDato()).append("\n");
            aux = aux.getSig();
        }
        return sb.toString().trim();
    }

    
    
    

  
}