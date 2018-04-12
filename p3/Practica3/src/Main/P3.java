package Main;

import GUI.Control;
import Modelo.TheUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;

public class P3 {

    public static void main(String[] args) {
                
        // Se obtiene la configuración gráfica del sistema y se crea el 
        // Canvas3D que va a mostrar la imagen.
        Canvas3D canvas = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
        
        // Se crea el universo.
        TheUniverse universe = new TheUniverse(canvas);
        
        // Se construye la ventana de control.
        // La ventana de control es la que crea y abre la ventana de 
        // visualización.
        Control controlWindow = new Control(universe);
        // Se hace la aplicación visible.
        controlWindow.setVisible(true);
        
    }
    
}
