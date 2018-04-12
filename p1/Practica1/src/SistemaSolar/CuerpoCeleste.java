package SistemaSolar;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class CuerpoCeleste extends BranchGroup{
    
    // Atributos
    private float tam;
    private String nombre;
    private boolean es_estrella, es_luna, es_satelite;
    private float x, y, z;
    private long velocidad_rotacion, velocidad_traslacion;
    boolean movimiento_r = true, movimiento_t = true;
    
    protected TransformGroup posicion;
    private TransformGroup rotacion;
    private TransformGroup traslacion;
    
    private Alpha value_rot;
    private Alpha value_trasl;
    
    // Constructor de la clase CuerpoCeleste.
    //      Solo creará la esfera, le dará textura y la posicionará inicialmente.   
    //EXAMEN: le añado un booleano para comprobar si es luna o no
    public CuerpoCeleste(float tama, String nom, boolean estr,
                         float xp, float yp, float zp, long vr, long vt, boolean esLuna, boolean esSatelite) {
        
        this.setPickable(true);
        this.setCapability(Node.ENABLE_PICK_REPORTING);
        
        // Asignamos los valores a los atributos
        tam = tama;
        nombre = nom;
        es_estrella = estr;
        x = xp; y = yp; z = zp;
        velocidad_rotacion = vr;
        velocidad_traslacion = vt;
        es_luna = esLuna;
        es_satelite = esSatelite;
        
        // Se crea el aspecto.
        Appearance appearance = new Appearance();
        appearance.setPolygonAttributes(new PolygonAttributes (PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0.0f)); 
        
        //Gouraud
        //ColoringAttributes atricolor = new ColoringAttributes(new Color3f(0.5f,0.5f,0.5f), ColoringAttributes.SHADE_GOURAUD);
        //appearance.setColoringAttributes(atricolor);
        
        // Se le aplica un material. Emisiva a 1 solo si es estrella.
        Color3f ambiental = new Color3f(0.2f,0.2f,0.2f);
        Color3f emisiva;
        Color3f difusa;
        Color3f especular;
        if (es_estrella)
        {
            emisiva = new Color3f(1.0f,1f,1.0f);
            especular = new Color3f(1f,1f,1f);
            difusa = new Color3f(0.49f,0.34f,0.0f);
        }
        else
        {
            emisiva = new Color3f(0.0f,0.0f,0.0f);
            difusa = new Color3f(0.49f,0.34f,0.0f);
            especular = new Color3f(1f,1f,1f);
        }
        float brillo = 3.0f;
        appearance.setMaterial(new Material(
                ambiental, emisiva, difusa, especular, brillo));
        
        // Se le aplica la textura.
        String text = "images/"+nombre+".jpg";
        Texture texture = new TextureLoader(text, null).getTexture();
        appearance.setTexture(texture);
        
        // Se pone atributo de textura modulate para que haya color y textura.
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        appearance.setTextureAttributes(texAttr);
        
        // Se crea la esfera con coordenadas de textura, con las normales hacia
        // el exterior y la apariencia creada.
        Sphere sphere = new Sphere(tam, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, 30, appearance);
        
        // Se crean los TG que generarán todo el movimiento de los Cuerpos.
        posicion = posicionar(x,y,z);
        rotacion = rotar(velocidad_rotacion);
        traslacion = trasladar(velocidad_traslacion);
        
        // Se crea el grafo de escena de cada uno de los Cuerpos.
        rotacion.addChild(sphere);
        posicion.addChild(rotacion);
        traslacion.addChild(posicion);
        this.addChild(traslacion);
        
    }
    
    private TransformGroup posicionar(float x, float y, float z) {
        
        // Traslación para establecer la distancia con el sol
        TransformGroup TG = new TransformGroup();
        Transform3D posicion = new Transform3D();
        posicion.setTranslation(new Vector3d(x,y,z));
        
        TG.setTransform(posicion);
        
        return TG;
    }
    
    private TransformGroup rotar(long veloc_rotac) {
        
        // Se crea el grupo que contendrá la transformación de rotación
        // Todo lo que cuelgue de él rotará
        TransformGroup transform = new TransformGroup();
        
        // Se le permite que se cambie en tiempo de ejecución
        transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        // Se crea la matriz de rotación
        Transform3D yAxis = new Transform3D ();
        //yAxis.rotZ(Math.PI/4);
        
        // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
        value_rot = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, 
                veloc_rotac, 0, 0, 0, 0, 0);
        
        // Se crea el interpolador de rotación, las figuras iran rotando
        //EXAMEN
        RotationInterpolator rotator = new RotationInterpolator (value_rot, transform, yAxis,
         0.0f, (float) Math.PI*2.0f);
        
        // Se le pone el entorno de activación y se activa
        rotator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 100.0));
        rotator.setEnable(true);
        
        // Se cuelga del grupo de transformación y este se devuelve
        transform.addChild(rotator);
        
        return transform;
    }
    
    private TransformGroup trasladar(long veloc_trasla) {
        
        // Se crea el grupo que contendrá la transformación de rotación
        // Todo lo que cuelgue de él rotará
        TransformGroup transform = new TransformGroup();
        
        // Se le permite que se cambie en tiempo de ejecución
        transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        // Se crea la matriz de rotación
        Transform3D yAxis = new Transform3D ();
        int rotacionAleatoria;
        
        
        // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
        value_trasl = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, 
                veloc_trasla, 0, 0, 0, 0, 0);
        
        // Se crea el interpolador de rotación, las figuras iran rotando
        RotationInterpolator rotator = new RotationInterpolator (value_trasl, transform, yAxis,
            0.0f, (float) Math.PI*2.0f);
        
        
        // Se le pone el entorno de activación y se activa
        rotator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 100.0));
        rotator.setEnable(true);
        
        // Se cuelga del grupo de transformación y este se devuelve
        transform.addChild(rotator);
        
        return transform;
    }
    
    private void setRotationOnOff (boolean onOff) {
        if (onOff){
            value_rot.resume();
            movimiento_r = true;
        }
        else{
            value_rot.pause();
            movimiento_r = false;
        }
    }
    
    void setTranslationOnOff (boolean onOff) {
        if (onOff){
            value_trasl.resume();
            movimiento_t = true;
        }
        else{
            value_trasl.pause();
            movimiento_t = false;
        }
    }
}
