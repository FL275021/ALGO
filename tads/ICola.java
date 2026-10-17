/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tads;

import dominio.Cliente;

/**
 *
 * @author Usuario
 */
public interface ICola<T> extends Iterable<T>{

    void enqueue(T dato);

    T dequeue();

    T front();



    boolean esVacia();

    int cantidadElementos();

    void mostrar();

    @Override
    String toString();

    T obtenerPorIndice(int i);
}
