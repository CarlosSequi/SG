package Modelo;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import java.util.Random;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Reloj extends BranchGroup{
    
    private float ancho;
    private float largo;
    private float grueso;
    
    public Reloj(){
        
        this.setPickable(false);
        this.ancho = 10f;
        this.largo = 5f;
        this.grueso = 30f;
        
        // Se crea el aspecto.
        Appearance appearance = new Appearance();
        appearance.setPolygonAttributes(new PolygonAttributes (PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0.0f)); 
        
        // Se le aplica un material
        Color3f ambiental = new Color3f(0.2f,0.2f,0.2f);
        Color3f emisiva = new Color3f(0.0f,0.0f,0.0f);;
        Color3f difusa = new Color3f(0.49f,0.34f,0.0f);
        Color3f especular = new Color3f(0.2f,0.2f,0.2f);
        float brillo = 3.0f;
        appearance.setMaterial(new Material(
                ambiental, emisiva, difusa, especular, brillo));
        
        // Se le aplica la textura.
        String text = "images/madera.jpg";
        Texture texture = new TextureLoader(text, null).getTexture();
        appearance.setTexture(texture);
        
        // Se pone atributo de textura modulate para que haya iluminación y textura.
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        appearance.setTextureAttributes(texAttr);
        
        // Se crea un cubo con coordenadas de textura, con las normales hacia
        // el exterior y la apariencia creada.
        Box reloj = new Box(ancho, grueso, largo,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        Box base = new Box(ancho+4, 1f, largo+4,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        // pata delantera derecha
        Box pata1 = new Box(2f, 3f, 2f,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        // pata delantera izquierda
        Box pata2 = new Box(2f, 3f, 2f,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        // pata trasera derecha
        Box pata3 = new Box(2f, 3f, 2f,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        // pata trasera izquierda
        Box pata4 = new Box(2f, 3f, 2f,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        Box tapa = new Box(ancho+4, 1f, largo+4,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        TransformGroup TG = new TransformGroup();
        Transform3D posicion = new Transform3D();
        posicion.setTranslation(new Vector3d(0, 0, -30));
        TG.setTransform(posicion);
        
        TransformGroup TG2 = new TransformGroup();
        Transform3D posicion2 = new Transform3D();
        posicion2.setTranslation(new Vector3d(0, -grueso, -30f));
        TG2.setTransform(posicion2);
        
        TransformGroup TG3 = new TransformGroup();
        Transform3D posicion3 = new Transform3D();
        posicion3.setTranslation(new Vector3d(ancho, -grueso-2, -30f+largo));
        TG3.setTransform(posicion3);
        
        TransformGroup TG4 = new TransformGroup();
        Transform3D posicion4 = new Transform3D();
        posicion4.setTranslation(new Vector3d(-ancho, -grueso-2, -30f+largo));
        TG4.setTransform(posicion4);
        
        TransformGroup TG5 = new TransformGroup();
        Transform3D posicion5 = new Transform3D();
        posicion5.setTranslation(new Vector3d(ancho, -grueso-2, -30f-largo));
        TG5.setTransform(posicion5);
        
        TransformGroup TG6 = new TransformGroup();
        Transform3D posicion6 = new Transform3D();
        posicion6.setTranslation(new Vector3d(-ancho, -grueso-2, -30f-largo));
        TG6.setTransform(posicion6);
        
        TransformGroup TG7 = new TransformGroup();
        Transform3D posicion7 = new Transform3D();
        posicion7.setTranslation(new Vector3d(0, grueso, -22+largo));
        TG7.setTransform(posicion7);
        
        Random r = new Random();
        Pendulum pendulo = new Pendulum(r.nextFloat()+1);
        
        TG7.addChild(pendulo);
        TG7.addChild(tapa);
        this.addChild(TG7);
        TG6.addChild(pata4);
        this.addChild(TG6);
        TG5.addChild(pata3);
        this.addChild(TG5);
        TG4.addChild(pata2);
        this.addChild(TG4);
        TG3.addChild(pata1);
        this.addChild(TG3);
        TG2.addChild(base);
        this.addChild(TG2);
        TG.addChild(reloj);
        this.addChild(TG);
    }
}
