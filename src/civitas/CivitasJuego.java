
package civitas;
import java.util.ArrayList;
import java.util.Collections;
import GUI.VistaDado;



public class CivitasJuego {
    
    private int indiceJugadorActual;

    private ArrayList<Jugador> jugadores;
 
    private EstadoJuego estado;

    private GestorEstados estadojuego;

    private MazoSorpresas mazo;

    private Tablero tablero;
    
    private OperacionJuego operacion;

    //Constructor privado

    public CivitasJuego (ArrayList<String> nombres,boolean debug){
        
        jugadores=new ArrayList<Jugador>();
       
        for(int i=0;i<nombres.size();i++){
            String nombre_jugador = nombres.get(i);

            Jugador jugador = new Jugador(nombre_jugador);

            jugadores.add(jugador);

        }
        estadojuego = new GestorEstados();
        estado=estadojuego.estadoInicial();
        
        VistaDado dado = VistaDado.getInstance(); 
        dado.setDebug(debug);

        indiceJugadorActual=dado.quienEmpieza(4);

        mazo=new MazoSorpresas(debug);

        tablero=new Tablero();

        inicializaMazoSorpresa();

        inicializaTablero(mazo);



    }

    private void inicializaTablero(MazoSorpresas mazo){
        tablero.añadeCasilla(new Casilla("Salida"));
        tablero.añadeCasilla(new CasillaCalle("Madrid", 60, 20, 50));
        tablero.añadeCasilla(new CasillaCalle( "Barcelona", 80, 30, 100));
        tablero.añadeCasilla(new CasillaCalle("Sevilla", 100, 40, 200));
        tablero.añadeCasilla(new CasillaCalle("Cádiz", 120, 50, 300));
        tablero.añadeCasilla(new CasillaSorpresa("Sorpresa", mazo));
        tablero.añadeCasilla(new CasillaCalle("Granada", 240, 60, 400));
        tablero.añadeCasilla(new CasillaCalle("Santander", 100, 70, 500));
        tablero.añadeCasilla(new CasillaSorpresa("Sorpresa", mazo));
        tablero.añadeCasilla(new CasillaCalle("Málaga", 200, 80, 600));
        tablero.añadeCasilla(new Casilla("Puro relax"));
        tablero.añadeCasilla(new CasillaCalle("Quevedo", 220, 90, 700));
        tablero.añadeCasilla(new CasillaCalle("Diana", 240, 100, 800));
        tablero.añadeCasilla(new CasillaCalle("Murcia", 60, 110, 900));
        tablero.añadeCasilla(new CasillaSorpresa("Sorpresa", mazo));
        tablero.añadeCasilla(new CasillaCalle("Oviedo", 120, 120, 1000));
        tablero.añadeCasilla(new CasillaCalle("Almeria", 300, 130, 1100));
        tablero.añadeCasilla(new CasillaSorpresa("Sorpresa", mazo));
        tablero.añadeCasilla(new CasillaCalle("Motril", 350, 140, 1200));
        tablero.añadeCasilla(new CasillaCalle("Toledo", 400, 150, 1300));

    }

    private void inicializaMazoSorpresa(){

        mazo.alMazo(new SorpresaPagarCobrar("Sorpresa_1", 100));
        mazo.alMazo(new SorpresaPagarCobrar("Sorpresa_1", 500));
        mazo.alMazo(new SorpresaPagarCobrar("Sorpresa_1", 1000));
        mazo.alMazo(new SorpresaPagarCobrar("Sorpresa_1", -100));
        mazo.alMazo(new SorpresaPagarCobrar("Sorpresa_1", -500));
        mazo.alMazo(new SorpresaPagarCobrar("Sorpresa_1", -1000));
        mazo.alMazo(new SorpresaPorCasaHotel("Sorpresa_2", 70));
        mazo.alMazo(new SorpresaPorCasaHotel("Sorpresa_2", 1000));
        mazo.alMazo(new SorpresaPorCasaHotel("Sorpresa_2", -70));
        mazo.alMazo(new SorpresaPorCasaHotel("Sorpresa_2", -1000));
        mazo.alMazo(new SorpresaPorCasaHotel("Sorpresa_3", 0));
        mazo.alMazo(new SorpresaPorCasaHotel("Sorpresa_3", 0));
    }

    public Jugador getJugadorActual(){

        return jugadores.get(indiceJugadorActual);
    }

    public Tablero getTablero(){
        return tablero;
    }

    private void pasarTurno(){

        indiceJugadorActual = (indiceJugadorActual+1) % 4;

    }

    public void siguientePasoCompletado(OperacionJuego operacion){

        estado=estadojuego.siguienteEstado (jugadores.get(indiceJugadorActual), estado, operacion);

    }

    public boolean construirCasa(int ip){

        return jugadores.get(indiceJugadorActual).construirCasa(ip);

    }

    public boolean construirHotel(int ip){

        return jugadores.get(indiceJugadorActual).construirHotel(ip);

    }

    public boolean finalDelJuego(){
        boolean fin = false;
        for(Jugador jugador : jugadores){
            if(jugador.enBancarrota() == true)
                fin = true;
        }
        
        return fin;
    }

    public ArrayList<Jugador> ranking(){

        Collections.sort(jugadores,Jugador::compareTo);
        return jugadores;

    }

    private void contabilizarPasosPorSalida(){

        if(tablero.computarPasoPorSalida())
            jugadores.get(indiceJugadorActual).pasaPorSalida();

    }

    public boolean comprar(){

        Jugador jugadorActual=jugadores.get(indiceJugadorActual);
        int numCasillaActual=jugadorActual.getCasillaActual();

        Casilla casilla=tablero.getCasilla(numCasillaActual);

        boolean res=jugadorActual.comprar(casilla);

        return res;

    }

    void avanzaJugador(){

        Jugador jugadorActual=getJugadorActual();

        int posicionActual=jugadorActual.getCasillaActual();

        int tirada=VistaDado.getInstance().tirar();

        int posicionNueva=tablero.nuevaPosicion(posicionActual,tirada);

        Casilla casilla=tablero.getCasilla(posicionNueva);

        this.contabilizarPasosPorSalida();

        jugadorActual.moverACasilla(posicionNueva);

        casilla.recibeJugador(indiceJugadorActual,jugadores);
    }

    public OperacionJuego siguientePaso(){
        Jugador jugadorActual = getJugadorActual();

        OperacionJuego operacion = estadojuego.siguienteOperacion(jugadorActual, estado);

        if (operacion == OperacionJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else if(operacion == OperacionJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }

        return operacion;
    }
}