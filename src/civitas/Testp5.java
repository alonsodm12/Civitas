
package civitas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import GUI.CivitasView;
import GUI.CapturaNombres;
import controladorCivitas.Controlador;
import GUI.VistaDado;
//import javax.lang.model.util.ElementScanner6;


public class Testp5 {

    public static void main(String[] args) {
        
        CivitasView vista_civitas = new CivitasView();
        
        CapturaNombres captura = new CapturaNombres(vista_civitas,true);
        
        ArrayList<String> nombres = new ArrayList();
        
        nombres=captura.getNombres();
        
        VistaDado.createInstance(vista_civitas);
        
        CivitasJuego juego = new CivitasJuego(nombres,false);
        
        Controlador controlador = new Controlador(juego,vista_civitas);
        
        vista_civitas.setCivitasJuego(juego);
        
        controlador.juega();
    }
}