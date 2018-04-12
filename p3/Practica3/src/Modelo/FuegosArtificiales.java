package Modelo;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class FuegosArtificiales extends BranchGroup{
    
    
    public FuegosArtificiales(String textura, float x, float z){
        
        this.setPickable(false);
        
        // Se crea el aspecto.
        Appearance appearance = new Appearance();
        appearance.setPolygonAttributes(new PolygonAttributes (PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0.0f)); 
        
        // Se le aplica un material
        Color3f ambiental = new Color3f(0.2f,0.2f,0.2f);
        Color3f emisiva = new Color3f(1.0f,1.0f,1.0f);;
        Color3f difusa = new Color3f(0.49f,0.34f,0.0f);
        Color3f especular = new Color3f(0.8f,0.8f,0.8f);
        float brillo = 3.0f;
        appearance.setMaterial(new Material(
                ambiental, emisiva, difusa, especular, brillo));
        
        // Se le aplica la textura.
        String text = "images/"+textura;
        Texture texture = new TextureLoader(text, null).getTexture();
        appearance.setTexture(texture);
        
        // Se pone atributo de textura modulate para que haya iluminación y textura.
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        appearance.setTextureAttributes(texAttr);
        
        Cylinder cylinder = new Cylinder (0.5f, 7,  Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, appearance);
        Transform3D transCylinder = new Transform3D();
        transCylinder.set (new Vector3f (x, 0.0f, z));
        TransformGroup tgCylinder = new TransformGroup (transCylinder);
        tgCylinder.addChild(cylinder);
        
        Cone cono = new Cone(0.7f, 1, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, appearance);
        Transform3D transCone = new Transform3D();
        transCone.set (new Vector3f (x, 3.8f, z));
        TransformGroup tgCone = new TransformGroup (transCone);
        tgCone.addChild(cono);
        
        Transform3D posicion = new Transform3D();
        posicion.set (new Vector3f (0, -40f, 0));
        TransformGroup TG = new TransformGroup (posicion);
        
        TG.addChild(tgCone);
        TG.addChild(tgCylinder);
        this.addChild(TG);
    }
    
}
