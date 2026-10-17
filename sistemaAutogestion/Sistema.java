package sistemaAutogestion;

import dominio.Calificacion;
import java.time.LocalDate;
import dominio.Modalidad;
import dominio.Despliegue;
import dominio.Region;
import dominio.Entrada;
import tads.ICola;
import tads.ILista;
import tads.IListaOrdenada;
import tads.IPila;
import tads.Lista;
import tads.ListaOrdenada;
import tads.Nodo;
import tads.Pila;

public class Sistema implements IObligatorio {

    private IListaOrdenada<Modalidad> modalidades;
    private ILista<Despliegue> despliegues;
    private IListaOrdenada<Region> regiones;
    private IPila<Entrada> historialCompras;
    private ILista<Calificacion> calificaciones;


/* programa para gestionar una plataforma global de inferencia: por un lado, el registro y la consulta de los despliegues de modelos y, por otro, 
las despliegues de datacenters. El sistema deberá permitir solicitar un despliegue en una región, procesar las solicitudes pendientes, consultar los 
despliegues activos de cada región y retirar el último despliegue procesado cuando corresponda */

    
    @Override//01      
    public Retorno inicializarSistema() {
modalidades = new ListaOrdenada<>();
despliegues = new ListaOrdenada<>();  //despliegue(String id(incremental), String codigo(4 ALFANUMERICOS), String descripcion, boolean cuantizado(3 DIGITOS), Modalidad modalidad)
regiones = new Lista<>();            //region(String codigo, String nombre, String pais, String zona)
historialCompras = new Pila<>();    //modalidad(String id, String texto) "indica el tipo de modelo: Texto(LLM), Vision(VIS), Audio(AUD), Mulotimodal(MUL), Otro(OTR)"
calificaciones = new Lista<>();    //DespliegueEnRegion(String idDespliegue, String codigoRegion)

        return Retorno.ok();
    }

    @Override//02     
    /* el código identifica la versión exacta de los pesos publicados; ambos son únicos, el parámetro cuantizado indica si el modelo se sirve con
     pesos de precisión reducida */   
/*ERRORES
1. Si id, código o descripción son vacíos o null, o modalidad es null.
2. Si código no tiene el formato válido.
3. Si ya existe un despliegue registrado con ese id.
4. Si ya existe un despliegue registrado con ese código     */

    public Retorno registrarDespliegue(String codigo, String descripcion, boolean cuantizado, String modalidadTexto) {
        Despliegue codDespliegueExistente = despliegues.buscarPorIdentificador(codigo);
        if (despliegueExistente != null) {
            return Retorno.error1();
        }

        if (capacidad <= 0) {
            return Retorno.error2();
        }

        Despliegue nuevaDespliegue = new Despliegue(nombre, capacidad);
        despliegues.agregarFinal(nuevaDespliegue);
        return Retorno.ok();
    }

    @Override//1.3         
    //ERRORES
    //1. No existe una despliegue con ese nombre
    public Retorno eliminarDespliegue(String nombre) {
        Despliegue salaEncontrada = despliegues.buscarPorIdentificador(nombre);
        if (salaEncontrada == null) {
            return Retorno.error1();
        }

        despliegues.eliminarDato(salaEncontrada);
        return Retorno.ok();
    }

    @Override//1.4         
    //ERRORES
    //1. Ya existe un evento con ese código
    //2. Si aforo necesario <= 0
    //3. No hay despliegues disponibles para esa fecha con aforo suficiente
    public Retorno registrarModalidad(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        Modalidad eventoExistente = modalidades.buscarPorIdentificador(codigo);
        if (eventoExistente != null) {
            return Retorno.error1();
        }

        if (aforoNecesario <= 0) {
            return Retorno.error2();
        }

        for (Despliegue unaDespliegue : despliegues) {
            if (unaDespliegue.getCapacidad() >= aforoNecesario && !unaDespliegue.estaOcupadaEnFecha(fecha)) {
                Modalidad nuevoModalidad = new Modalidad(codigo, descripcion, aforoNecesario, fecha, unaDespliegue);
                modalidades.agregar(nuevoModalidad);
                unaDespliegue.ocuparFecha(fecha);
                return Retorno.ok();
            }
        }
        return Retorno.error3();
    }

}
