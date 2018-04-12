package SistemaSolar;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Texture;
import javax.vecmath.Point3d;

public class TheBackground extends BranchGroup{
    
    // Constructor del background
    
    public TheBackground() {
        
        // Se crea el objeto para el fondo y 
        //     se le asigna un área de influencia
        //BoundingSphere(centro, radio)
        Background background = new Background ();
        background.setApplicationBounds (new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 100.0));

        // Se crea un aspecto basado en la textura a mostrar
        Appearance app = new Appearance ();
        Texture texture = new TextureLoader ("images/background2.jpg", null).getTexture();
        app.setTexture (texture);
        
        // Se hace la esfera indicándole:
        //   Que genere coordenadas de textura
        //   Que genere las normales hacia adentro
        //      Sphere (radio, flags, apariencia)
        //      flags -> Sirve para meter las coordenadas de textura y las normales.
        Primitive sphere = new Sphere(1.0f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS_INWARD, app) ;
        // Se crea la rama para la geometría del fondo,
        BranchGroup bgGeometry = new BranchGroup();
        // Se le añade la esfera        
        bgGeometry.addChild(sphere);
        // Y se establece como geometría del objeto background
        background.setGeometry(bgGeometry);
        // Finalmente se crea el BranchGroup para devolver el Background
        //BranchGroup bgBackground = new BranchGroup();
        //bgBackground.addChild(backgroundNode);
        
        // La rama del background se cuelga de su padre.
        this.addChild(background);
        
    }
}
