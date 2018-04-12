package Modelo;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;

public class TheBackground extends BranchGroup{
    
    // Constructor del background
    
    public TheBackground() {
        
        // Se crea un aspecto basado en la textura a mostrar
        Appearance appearance = new Appearance ();
        Texture texture = new TextureLoader ("images/fondo.jpg", null).getTexture();
        appearance.setTexture (texture);
        appearance.setPolygonAttributes(new PolygonAttributes (PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0.0f));
        
        Box caja = new Box(100.0f, 70.0f, 100.0f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS_INWARD, appearance);
        
        Shape3D parte_arriba = caja.getShape(Box.TOP);
        Appearance app = new Appearance();
        Texture tex = new TextureLoader("images/cielo.jpg", null).getTexture();
        app.setTexture(tex);
        app.setPolygonAttributes(new PolygonAttributes (PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0.0f));
        parte_arriba.setAppearance(app);
        
        Shape3D parte_abajo = caja.getShape(Box.BOTTOM);
        app = new Appearance();
        tex = new TextureLoader("images/suelo.jpg", null).getTexture();
        app.setTexture(tex);
        app.setPolygonAttributes(new PolygonAttributes (PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0.0f));
        parte_abajo.setAppearance(app);
        
        
        // Se crea la rama para la geometría del fondo
        BranchGroup bgGeometry = new BranchGroup();
        bgGeometry.addChild(caja);
        
        // La rama del background se cuelga de su padre.
        this.addChild(bgGeometry);        
    }
}
