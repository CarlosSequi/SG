package Modelo;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Tapete extends BranchGroup{
    
    private float ancho;
    private float largo;
    private float grueso;
    
    public Tapete(float anch, float larg){
        
        this.setPickable(false);
        this.ancho = anch;
        this.largo = larg;
        this.grueso = 0.1f;
        
        // Se crea el aspecto.
        Appearance appearance = new Appearance();
        appearance.setPolygonAttributes(new PolygonAttributes (PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0.0f)); 
        
        // Se le aplica un material
        Color3f ambiental = new Color3f(0.2f,0.2f,0.2f);
        Color3f emisiva = new Color3f(0.0f,0.7f,0.0f);;
        Color3f difusa = new Color3f(0.49f,0.34f,0.0f);
        Color3f especular = new Color3f(1f,1f,1f);
        float brillo = 3.0f;
        appearance.setMaterial(new Material(
                ambiental, emisiva, difusa, especular, brillo));
        
        // Se le aplica la textura.
        String text = "images/tapete.jpg";
        Texture texture = new TextureLoader(text, null).getTexture();
        appearance.setTexture(texture);
        
        // Se pone atributo de textura modulate para que haya iluminación y textura.
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        appearance.setTextureAttributes(texAttr);
        
        // Se crea un cubo con coordenadas de textura, con las normales hacia
        // el exterior y la apariencia creada.
        Box tapete = new Box(ancho, grueso, largo,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        TransformGroup TG = new TransformGroup();
        Transform3D posicion = new Transform3D();
        posicion.setTranslation(new Vector3d(ancho/2, 0, largo/2));
        
        TG.setTransform(posicion);
        
        TG.addChild(tapete);
        this.addChild(TG);
    }
    
}
