package Modelo;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.pickfast.PickCanvas;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;

// Hereda de la clase Behavior para poder gestionar el comportamiento según las
// acciones que indiquemos a continuación. Behavior es abstracta, hay que implementar
// un par de métodos: initialize y processStimulus.
public class Pick extends Behavior{
    
    // Condición de respuesta
    private WakeupOnAWTEvent condicion;
    // Permite realizar un Pick a partir de la posición indicada en el canvas con el ratón
    private PickCanvas pickCanvas;
    private Canvas3D canvas;
    private Scene escena;
    
    // Constructor
    
    public Pick(Canvas3D aCanvas, Scene esce) {
        escena = esce;
        canvas = aCanvas;
        condicion = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
        // Escuchamos cuando se haga click con el ratón.
    }
    
    // Búsqueda del elemento seleccionado al realizar el click
    
    public void initSearch(BranchGroup bg) {
        // Se selecciona una posición del canvas usando un pick.
        pickCanvas = new PickCanvas(canvas, bg);
        // Radio de búsqueda en píxeles entorno al píxel clicado. =.0f = tolerancia mínima
        pickCanvas.setTolerance(0.0f);
        // Selecciona elementos a partir de su geometría
        pickCanvas.setMode(PickInfo.PICK_BOUNDS);

    }
    
    @Override
    public void initialize() {
        // El pick comienza deshabilitado esperando al initSearch
        //setEnable(false); 
        // Se despierta cuando se produzca la condición, en nuestro caso un click.
        wakeupOn(condicion);
    }
    
    @Override
    public void processStimulus(Enumeration cond) {
        // Se extrae de los criterios el botón clicado
        WakeupOnAWTEvent unaCondicion = (WakeupOnAWTEvent) cond.nextElement();
        AWTEvent[] unEvento = unaCondicion.getAWTEvent();
        MouseEvent mouse = (MouseEvent) unEvento[0];
        
        // Se le indica la posición del ratón para hacer click
        pickCanvas.setShapeLocation(mouse);
        // Devuelve información sobre la geometría más cercana
        PickInfo pi = pickCanvas.pickClosest();
        
        // Si hay algo seleccionado...
        
        if(pi != null) {
            // Se obtiene la rama del grafo en el que está el shape clicado
            Shape3D nodo = (Shape3D) pi.getNode();
            Box caja = (Box) nodo.getParent();
            TransformGroup tg1 = (TransformGroup) caja.getParent();
            TransformGroup tg2 = (TransformGroup) tg1.getParent();
            Carta carta = (Carta) tg2.getParent();

                carta.mostrar();
                escena.comprobarFin();

        }
        
        // Aquí tendremos que añadir que si se hace pick se giren las cartas.
        // Y se vuelve a esperar de nuevo el próximo click
        wakeupOn(condicion);
    }
    
}
