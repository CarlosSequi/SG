package Main;

import GUI.Control;
import SistemaSolar.TheUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;

public class P1 {

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

/*

EXAMEN: pregunta 5.

a) El modo MODULATE hace que se "mezclen" de manera correcta las texturas con el color del material, de manera que obtenemos unos
   objetos con un mayor realismo en la escena, es decir, hace que se vea parte del color del material y parte de la taxtura juntos,
   para que no todo sea o color o textura.

b) Es recomendable utilizarlo en situaciones en las que es preciso (como he dicho antes) un mayor realismo en los objetos de una
escena.

*/