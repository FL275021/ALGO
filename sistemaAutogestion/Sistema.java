package sistemaAutogestion;

import dominio.Calificacion;
import java.time.LocalDate;
import dominio.Evento;
import dominio.Despliegue;
import dominio.Cliente;
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

    private IListaOrdenada<Evento> eventos;
    private ILista<Despliegue> despliegues;
    private IListaOrdenada<Cliente> clientes;
    private IPila<Entrada> historialCompras;
    private ILista<Calificacion> calificaciones;


/* programa para gestionar una plataforma global de inferencia: por un lado, el registro y la consulta de los despliegues de modelos y, por otro, 
las despliegues de datacenters. El sistema deberá permitir solicitar un despliegue en una región, procesar las solicitudes pendientes, consultar los 
despliegues activos de cada región y retirar el último despliegue procesado cuando corresponda */

    
    @Override//01      
    public Retorno inicializarSistema() {
eventos = new ListaOrdenada<>();
despliegues = new ListaOrdenada<>();  //despliegue(String id(incremental), String codigo, String descripcion, boolean cuantizado, Modalidad modalidad)
clientes = new Lista<>();            //region(String codigo, String nombre, String pais, String zona)
historialCompras = new Pila<>();    //modalidad(String id, String texto) "indica el tipo de modelo: Texto, Vision, Audio, Mulotimodal, Otro"
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
4. Si ya existe un despliegue registrado con ese código */
    public Retorno registrarDespliegue(String codigo, String descripcion, boolean cuantizado, String modalidadTexto) {
        Despliegue salaExistente = despliegues.buscarPorIdentificador(nombre);
        if (salaExistente != null) {
            return Retorno.error1();
        }

        if (capacidad <= 0) {
            return Retorno.error2();
        }

        Despliegue nuevaDespliegue = new Despliegue(nombre, capacidad);
        despliegues.agregarFinal(nuevaDespliegue);
        return Retorno.ok();
    }

    @Override//1.3          /////////PRIMERA ENTREGA/////////
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

    @Override//1.4          /////////PRIMERA ENTREGA/////////
    //ERRORES
    //1. Ya existe un evento con ese código
    //2. Si aforo necesario <= 0
    //3. No hay despliegues disponibles para esa fecha con aforo suficiente
    public Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        Evento eventoExistente = eventos.buscarPorIdentificador(codigo);
        if (eventoExistente != null) {
            return Retorno.error1();
        }

        if (aforoNecesario <= 0) {
            return Retorno.error2();
        }

        for (Despliegue unaDespliegue : despliegues) {
            if (unaDespliegue.getCapacidad() >= aforoNecesario && !unaDespliegue.estaOcupadaEnFecha(fecha)) {
                Evento nuevoEvento = new Evento(codigo, descripcion, aforoNecesario, fecha, unaDespliegue);
                eventos.agregar(nuevoEvento);
                unaDespliegue.ocuparFecha(fecha);
                return Retorno.ok();
            }
        }
        return Retorno.error3();
    }

    @Override//1.5          /////////PRIMERA ENTREGA/////////
    //ERRORES
    //1. Formato inválido de cédula (La cédula debe tener exactamente 8 dígitos)
    //2. Cliente ya registrado (cédula ya registrada)
    public Retorno registrarCliente(String cedula, String nombre) {
        if (cedula.length() != 8) {
            return Retorno.error1();
        }

        Cliente clienteEncontrado = clientes.buscarPorIdentificador(cedula);
        if (clienteEncontrado != null) {
            return Retorno.error2();
        }

        Cliente nuevoCliente = new Cliente(cedula, nombre);
        clientes.agregar(nuevoCliente);
        return Retorno.ok();
    }

    @Override//1.6
    //ERRORES
    //1. Cliente no existe
    //2. Evento no existe
    public Retorno comprarEntrada(String cedula, String codigoEvento) {
        Cliente unCliente = clientes.buscarPorIdentificador(cedula);
        if (unCliente == null) {
            return Retorno.error1();
        }

        Evento unEvento = eventos.buscarPorIdentificador(codigoEvento);
        if (unEvento == null) {
            return Retorno.error2();
        }

        if (unEvento.getCantEntradas() < unEvento.getAforoNecesario()) {
            //Hay lugar
            int numeroEntrada = unEvento.getCantEntradas() + 1;
            Entrada nuevaEntrada = new Entrada(codigoEvento, cedula, true, numeroEntrada);
            unCliente.agregarEntrada(nuevaEntrada);
            unEvento.agregarEntradaVendida(nuevaEntrada);
            unEvento.setCantEntradas(numeroEntrada);
            historialCompras.push(nuevaEntrada);
            return Retorno.ok();
        } else {
            //No hay lugar
            unEvento.getListaEspera().enqueue(unCliente);
            return Retorno.ok();
        }
    }
 
    @Override//1.7
    public Retorno eliminarEvento(String codigo) {
            Evento eventoEncontrado = eventos.buscarPorIdentificador(codigo);
        if (eventoEncontrado == null) {
            return Retorno.error1(); // No exite el evento
        }
        if (!eventoEncontrado.getEntradasVendidas().esVacia()) {
            return Retorno.error2(); // El evento tiene entradas vendidas
        }

        eventos.eliminar(eventoEncontrado);
        return Retorno.ok();
    }

    @Override//1.8
    //ERRORES
    //1. Cliente no existe
    //2. Evento no existe
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        Cliente unCliente = clientes.buscarPorIdentificador(cedula);
        if (unCliente == null) {
            return Retorno.error1();
        }

        Evento unEvento = eventos.buscarPorIdentificador(codigoEvento);
        if (unEvento == null) {
            return Retorno.error2();
        }

        Entrada entradaADevolver = null;
        for (Entrada e : unCliente.getEntradasCompradas()) {
            if (e.tieneEntrada(cedula, codigoEvento) && e.getEstado()) {
                entradaADevolver = e;
                break;
            }
        }

        if (entradaADevolver == null) {
            return Retorno.ok();
        }

        entradaADevolver.setEstado(false);
        unEvento.setCantEntradas(unEvento.getCantEntradas() - 1);

        if (!unEvento.getListaEspera().esVacia()) {
            Cliente siguiente = unEvento.getListaEspera().dequeue();
            int numEntrada = entradaADevolver.getNumEntrada();
            Entrada unaEntrada = new Entrada(codigoEvento, siguiente.getCedula(), true, numEntrada);

            siguiente.agregarEntrada(unaEntrada);
            unEvento.agregarEntradaVendida(unaEntrada);
        }

        return Retorno.ok();
    }

    @Override//1.9
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {//Califica el evento al que asistio
        Cliente clienteEncontrado = clientes.buscarPorIdentificador(cedula);
        Evento eventoEncontrado = eventos.buscarPorIdentificador(codigoEvento);
        if (clienteEncontrado == null) {
            return Retorno.error1();    //1. Cliente no existe
        }
        if (eventoEncontrado == null) {
            return Retorno.error2();    //2. Evento no existe
        }
        if (puntaje < 1 || puntaje > 10) {
            return Retorno.error3();    //3. Puntaje < 1 o puntaje > 10
        }
        for (int i = 0; i < eventoEncontrado.getCalificaciones().cantidadElementos(); i++) {
            Calificacion calif = eventoEncontrado.getCalificaciones().obtenerPorIndice(i);
            if (calif.getCedula().equals(cedula)) {
                return Retorno.error4(); //4. Cliente ya calificó este evento
            }
        }
         Calificacion nuevaCalificacion = new Calificacion(cedula, codigoEvento, puntaje, comentario);
         eventoEncontrado.getCalificaciones().agregarFinal(nuevaCalificacion);
        return Retorno.ok();
    }

    @Override//2.1          /////////PRIMERA ENTREGA/////////
    public Retorno listarDespliegues() {
        if (despliegues.esVacia()) {
            return Retorno.ok(); //No hay despliegues registradas.
        }

        String listadoDespliegues = despliegues.mostrarInvertido();

        Retorno ret = Retorno.ok();
        ret.valorString = listadoDespliegues;
        return ret;
    }

    @Override//2.2          /////////PRIMERA ENTREGA/////////
    public Retorno listarEventos() {
        if (eventos.esVacia()) {
            return Retorno.ok(); //No hay eventos registrados.
        }

        StringBuilder sb = new StringBuilder();

        for (Evento evento : eventos) {
            sb.append(evento.toListadoString()).append("#");
        }

        String resultado = sb.substring(0, sb.length() - 1);
        Retorno ret = Retorno.ok();
        ret.valorString = resultado;
        return ret;
    }

    @Override//2.3          /////////PRIMERA ENTREGA/////////
    public Retorno listarClientes() {
        if (clientes.esVacia()) {
            return Retorno.ok(); //No hay clientes registrados.
        }

        StringBuilder sb = new StringBuilder();
        for (Cliente c : clientes) {
            sb.append(c.toString()).append("#");
        }

        Retorno ret = Retorno.ok();
        ret.valorString = sb.toString().trim();
        return ret;
    }

    @Override//2.4          /////////PRIMERA ENTREGA/////////              
    //“X”- asiento libre, “O”-asiento ocupado, "#" - límite 
    public Retorno esDespliegueOptima(String[][] vistaDespliegue) {
        int filas = vistaDespliegue.length;
        int columnas = vistaDespliegue[0].length;
        int columnasOptimas = 0;

        for (int unaCol = 0; unaCol < columnas; unaCol++) {
            int libres = 0;
            int maxOcupadosConsecutivos = 0;
            int ocupadosConsecutivos = 0;
            for (int unaFil = 0; unaFil < filas; unaFil++) {
                String asiento = vistaDespliegue[unaFil][unaCol];

                if (asiento.equals("X")) {
                    libres++;
                    ocupadosConsecutivos = 0;
                } else if (asiento.equals("O")) {
                    ocupadosConsecutivos++;
                    if (ocupadosConsecutivos > maxOcupadosConsecutivos) {
                        maxOcupadosConsecutivos = ocupadosConsecutivos;
                    }
                } else { // por si es #
                    ocupadosConsecutivos = 0;
                }
            }

            if (maxOcupadosConsecutivos > libres) {
                columnasOptimas++;
            }
        }
 
        Retorno ret = Retorno.ok();
        ret.valorString = (columnasOptimas >= 2) ? "Es óptima" : "No es óptima";
        return ret;
    }

    @Override//2.5
    //1. Evento no existe
    //2. n < 1
    public Retorno listarClientesDeEvento(String codigo, int n) {
        if (n < 1) {
            return Retorno.error2();
        }

        Evento unEvento = eventos.buscarPorIdentificador(codigo);
        if (unEvento == null) {
            return Retorno.error1();
        }

        ILista<Entrada> entradasActivas = new Lista<>();

        for (Entrada e : unEvento.getEntradasVendidas()) {
            if (e.getEstado()) {
                entradasActivas.agregarFinal(e);
            }
        }

        int totalEntradas = entradasActivas.cantidadElementos();
        int inicio = Math.max(0, totalEntradas - n);

        StringBuilder sb = new StringBuilder();
        for (int i = totalEntradas - 1; i >= inicio; i--) {
            Entrada unaEntrada = entradasActivas.obtenerPorIndice(i);
            Cliente unCliente = clientes.buscarPorIdentificador(unaEntrada.getCedula());

            if (unCliente != null) {
                sb.insert(0, unCliente.getCedula() + "-" + unCliente.getNombre() + (sb.length() > 0 ? "#" : ""));
            }
        }

        Retorno ret = Retorno.ok();
        ret.valorString = sb.toString();
        return ret;
    }

       @Override//2.6     
    // Muestra los clientes en lista de espera para cada evento. Los eventos deben estar ordenados en forma alfabética por
    // código de evento y los clientes, ordenados por cédula dentro del mismo evento. Solo se deben listar aquellos eventos con clientes en espera
    public Retorno listarEsperaEvento() {
        StringBuilder sb = new StringBuilder();

        for (Evento evento : eventos) { 
            ICola<Cliente> colaEspera = evento.getListaEspera();
            if (!colaEspera.esVacia()) {    //si no tiene lista de espera no lo muestra
             
                IListaOrdenada<Cliente> clientesOrdenados = new ListaOrdenada<>();   // clientes de la cola a una lista para poder ordenarlos
                
                int cantidad = colaEspera.cantidadElementos();//colaEspera es una ICola, no podemos iterar directamente
                for (int i = 0; i < cantidad; i++) {
                    Cliente c = colaEspera.obtenerPorIndice(i);
                    clientesOrdenados.agregar(c);
                }
                for (Cliente c : clientesOrdenados) {
                if (sb.length() > 0) sb.append("#");
                sb.append(evento.getCodigoEvento()).append("-").append(c.getCedula());
                  }
                           }
                    }
                    Retorno ret = Retorno.ok();
                    ret.valorString = sb.toString();
                    return ret;
                }


    @Override//2.7
    public Retorno deshacerUtimasCompras(int n) {
        ILista<Entrada> reembolsos = new Lista<>();
        int contador = 0;
        while (contador < n && !historialCompras.esVacia()) {
            Entrada unaEntrada = historialCompras.pop();
            if (unaEntrada.getEstado()) {
                unaEntrada.setEstado(false);
                Evento unEvento = eventos.buscarPorIdentificador(unaEntrada.getCodigoEvt());
                if (unEvento != null) {
                    unEvento.setCantEntradas(unEvento.getCantEntradas() - 1);
                }
                reembolsos.agregarFinal(unaEntrada);
                contador++;
            }
        }
        IListaOrdenada<String> listaReembolsos = new ListaOrdenada<>();
        for (Entrada e : reembolsos) {
            String entrada = e.getCodigoEvt() + "-" + e.getCedula();
            listaReembolsos.agregar(entrada);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : listaReembolsos) {
            if (sb.length() > 0) {
                sb.append("#");
            }
            sb.append(s);
        }

        Retorno ret = Retorno.ok();
        ret.valorString = sb.toString();
        return ret;
    }

    
    @Override//2.8
    public Retorno eventoMejorPuntuado() {
        if (eventos.esVacia()) {
            return Retorno.ok();
        }

        double promedioMax = -1.0;
        ILista<String> mejorPuntuados = new Lista<>();

        for (Evento e : eventos) {
            double suma = 0.0;
            double cant = 0.0;

            for (Calificacion c : e.getCalificaciones()) {
                suma += c.getPuntaje();
                cant++;
            }

            if (cant == 0.0) {
                continue;
            }

            double promedio = suma / cant;
            
            if (promedio > promedioMax) {
                promedioMax = promedio;
                mejorPuntuados = new Lista<>();
                mejorPuntuados.agregarFinal(e.getCodigoEvento());
            } else if (promedio == promedioMax) {
                mejorPuntuados.agregarFinal(e.getCodigoEvento());
            }
        }
        
        if (mejorPuntuados.esVacia()) {
          Retorno ret = Retorno.ok();
            ret.valorString = "";
            return ret; 
        }

        IListaOrdenada<String> eventosOrdenados = new ListaOrdenada<>();
        for (String codigoEvt : mejorPuntuados) {
            eventosOrdenados.agregar(codigoEvt);
        }

        StringBuilder sb = new StringBuilder();
        for (String codigoEvt : eventosOrdenados) {
            if (sb.length() > 0) {
                sb.append("#");
            }
            sb.append(codigoEvt).append("-").append((int) promedioMax);
        }

        Retorno ret = Retorno.ok();
        ret.valorString = sb.toString();
        System.out.println("Promedio final → " + sb.toString());
        return ret;
    }

     @Override//2.9
    public Retorno comprasDeCliente(String cedula) {
        Cliente unCliente = clientes.buscarPorIdentificador(cedula);
        if (unCliente == null) {
            return Retorno.error1();
        }

        StringBuilder sb = new StringBuilder();
        ILista<Entrada> compras = obtenerComprasPorCliente(cedula);
        boolean primero = true;
        for (Entrada e : compras) {
            if (!primero) sb.append("#");   //TEC43-N#CUC11-N# COP10-D
            sb.append(e.getCodigoEvt()).append("-").append(e.getEstado() ? "N" : "D");  // Devuelta o No Devuelta
            primero = false;
        }
        Retorno ret = Retorno.ok();
        ret.valorString = sb.toString();
        return ret;
    } 

        public ILista<Entrada> obtenerComprasPorCliente(String cedula) {
            Cliente cliente = clientes.buscarPorIdentificador(cedula);
            if (cliente == null) return new Lista<>();
            ILista<Entrada> listaCompras = cliente.getEntradasCompradas();
            return listaCompras.invertirLista(listaCompras);
        }

 
@Override//2.10
public Retorno comprasXDia(int mes) {
    if (mes < 1 || mes > 12) {
        return Retorno.error1(); // Mes inválido
    }
    int[] comprasPorDia = new int[32];// máximo 31 días
    for (Entrada entrada : historialCompras) {
        Evento evento = eventos.buscarPorIdentificador(entrada.getCodigoEvt());
        if (evento != null) {
            LocalDate fecha = evento.getFecha();
            if (fecha.getMonthValue() == mes && entrada.getEstado()) {
                comprasPorDia[fecha.getDayOfMonth()]++;
            }
        }
    }
    
    StringBuilder sb = new StringBuilder(); //1-10#2-6#4-67#7-5#28-6
    for (int dia = 1; dia <= 31; dia++) {
        if (comprasPorDia[dia] > 0) {
            if (sb.length() > 0) sb.append("#");
            sb.append(dia).append("-").append(comprasPorDia[dia]);
        }
    }
    Retorno ret = Retorno.ok();
    ret.valorString = sb.toString();
    return ret;
}

}
