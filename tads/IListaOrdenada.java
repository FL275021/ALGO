/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tads;

/**
 *
 * @author Usuario
 */
public interface IListaOrdenada<T extends Comparable<T>> extends Iterable<T> {

    void agregar(T dato);

    void eliminar(T dato);

    T obtenerPorIndice(int indice);

    T buscarPorIdentificador(String str);

    T obtenerDato(T dato);

    boolean existe(T dato);

    int cantidadElementos();

    boolean esVacia();

    void mostrar();

    @Override
    String toString();
}
