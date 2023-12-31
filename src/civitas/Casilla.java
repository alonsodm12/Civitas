package civitas;

import java.util.ArrayList;

public class Casilla {
    
    //Unico atributo que tiene sentido para una casilla general

    protected String nombre;

    Casilla(String nombre){
        this.nombre=nombre;
    }

    public String getNombre(){
        return nombre;
    }

     
    public String toString(){

        return nombre;
    }
    
    public boolean esCalle(){
        return false;
    }
    
    public boolean esSorpresa(){
        return false;
    }
    
    void informe (int actual, ArrayList<Jugador>todos){

        Diario.getInstance().ocurreEvento(" El jugador: " + todos.get(actual).getNombre() + 
                                          " ha caido en esta casilla cuyos datos son: "
                                           + this.toString());
    }
    void recibeJugador(int actual, ArrayList<Jugador>todos){
        informe(actual, todos);
    }
} 

