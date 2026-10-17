/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tads;

import java.util.Iterator;

import dominio.Entrada;

/**
 *
 * @author Usuario
 */
public interface IPila<T> extends Iterable<T>{

    void push(T dato);

    T pop();

    T top();
    
    void vaciar();

    boolean esVacia();

    int cantidadElementos();

    void mostrar(); 

    @Override
    String toString();

}
