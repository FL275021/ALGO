package tads;

public interface ILista<T> extends Iterable<T> {

    void agregarInicio(T dato);

    void agregarFinal(T dato);

    void eliminarInicio();

    void eliminarDato(T dato);

    T obtenerPorIndice(int indice);

    T buscarPorIdentificador(String str);

    T obtenerDato(T dato);

    boolean existe(T dato);

    void mostrar();

    String mostrarInvertido();
     
    int cantidadElementos();

    ILista<T> invertirLista(ILista<T> original);

    boolean esVacia();

    @Override
    String toString();
}
