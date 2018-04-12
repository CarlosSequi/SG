package Modelo;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;

public class Carta extends BranchGroup{
    
    private float ancho;
    private float largo;
    private float grueso;
    private float coord_x;
    private float coord_y;
    private float coord_z;
    private TransformGroup coloca;
    private TransformGroup t_aux;
    private Box carta;
    private int id;
    private static Scene escena;
    
    Alpha subir = new Alpha(1,1000);
    Alpha bajar = new Alpha(1, Alpha.INCREASING_ENABLE, 1400, 0, 1000,0,0,0,0,0);
    
    boolean onClick = false;
    private static int num_cartas_arriba = 0;
    private static int id1;
    private static int id2;
    
    private static Carta carta1;
    private static Carta carta2;
    
    public Carta(float posx, float posy, float posz){
        
        this.setCapability(BranchGroup.ALLOW_DETACH);
        this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        this.ancho = 2f;
        this.largo = 3f;
        this.grueso = 0.1f;
        this.coord_x = posx;
        this.coord_y = posy;
        this.coord_z = posz;
        
        // Se crea el aspecto.
        Appearance appearance = new Appearance();
        appearance.setPolygonAttributes(new PolygonAttributes (PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0.0f)); 
        
        // Se le aplica un material
        Color3f ambiental = new Color3f(0.2f,0.2f,0.2f);
        Color3f emisiva = new Color3f(1.0f,1.0f,1.0f);;
        Color3f difusa = new Color3f(0.49f,0.34f,0.0f);
        Color3f especular = new Color3f(1f,1f,1f);
        float brillo = 3.0f;
        appearance.setMaterial(new Material(
                ambiental, emisiva, difusa, especular, brillo));
        
        // Se le aplica la textura.
        String text = "images/reverso.jpg";
        Texture texture = new TextureLoader(text, null).getTexture();
        //appearance.setTexture(texture);
        
        // Se pone atributo de textura modulate para que haya iluminación y textura.
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        //appearance.setTextureAttributes(texAttr);
        
        // Se crea un cubo con coordenadas de textura, con las normales hacia
        // el exterior y la apariencia creada.
        carta = new Box(ancho, grueso, largo,
                Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS,
                appearance);
        
        Shape3D parte_arriba = carta.getShape(Box.TOP);
        Appearance app = new Appearance();
        app.setTexture(texture);
        parte_arriba.setAppearance(app);
        
        
        coloca = colocarEnMesa(coord_x, coord_y, coord_z);
        t_aux = girarCarta();
        float [] angulos = {0f, 0f, (float) Math.PI/2, (float) Math.PI, (float) Math.PI};
        inicializarInterpolador1(subir, angulos);
        float [] angulos2 = {(float) Math.PI, (float) Math.PI, (float) Math.PI/2, 0f, 0f};
        inicializarInterpolador1(bajar, angulos2);
        
        t_aux.addChild(carta);
        coloca.addChild(t_aux);
        this.addChild(coloca);
    }
    
    private TransformGroup colocarEnMesa(float x, float y, float z) {
        
        // Traslación para establecer la posición en la mesa
        TransformGroup TG = new TransformGroup();
        Transform3D posicion = new Transform3D();
        posicion.setTranslation(new Vector3d(x,y,z));
        
        TG.setTransform(posicion);
        return TG;
    }
    
    protected TransformGroup girarCarta(){
                
        //Transform3D transf = new Transform3D();
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        return tg;
    }
    
    public void añadirParteAbajo(String text){
        
        Shape3D parte_abajo = carta.getShape(Box.BOTTOM);
        Appearance app = new Appearance();
        String ruta = "images/"+text+".jpg";
        Texture texture = new TextureLoader(ruta, null).getTexture();
        app.setTexture(texture);
        parte_abajo.setAppearance(app);
    }
    
    private void inicializarInterpolador1(Alpha alfa, float [] angulos) {
        
        Transform3D transf = new Transform3D();
        //alfa = new Alpha(1,2000);
        alfa.setStartTime(System.currentTimeMillis());
        alfa.pause();
        float [] alfas = {0.0f, 0.25f, 0.50f, 0.75f, 1.0f};
        
        Point3f [] posiciones = {
            new Point3f(0.0f, 0.0f, 0.0f), new Point3f(0.0f, 5.0f, 0.0f),
            new Point3f(0.0f, 5.0f, 0.0f), new Point3f(0.0f, 5.0f, 0.0f),
            new Point3f(0.0f, 0.0f, 0.0f)
        };
        
        Quat4f [] orientaciones = new Quat4f[5];
        for (int i = 0; i < 5; i++){
            orientaciones[i] = new Quat4f();
        }
        orientaciones[0].set(new AxisAngle4f(0f,0f,1f, angulos[0]));
        orientaciones[1].set(new AxisAngle4f(0f,0f,1f, angulos[1]));
        orientaciones[2].set(new AxisAngle4f(0f,0f,1f, angulos[2]));
        orientaciones[3].set(new AxisAngle4f(0f,0f,1f, angulos[3]));
        orientaciones[4].set(new AxisAngle4f(0f,0f,1f, angulos[4]));
        
        RotPosPathInterpolator interpolador = new RotPosPathInterpolator(
                alfa, t_aux, transf, alfas, orientaciones, posiciones);
        
        interpolador.setSchedulingBounds(new BoundingSphere (new Point3d(), 2.0f));
        
        t_aux.addChild(interpolador);
    }
    
    public void mostrar() {
               
        if(!onClick) {
            subir.resume();
            subir.setStartTime(System.currentTimeMillis());
            onClick = true;
            num_cartas_arriba++;
            if (num_cartas_arriba == 1){
                id1 = (int) this.getID();
                carta1 = (Carta) this;
            }
            else if (num_cartas_arriba == 2){
                id2 = (int) this.getID();
                carta2 = (Carta) this;
            }
        }
        
        if (num_cartas_arriba == 2) {
            num_cartas_arriba = 0;
            if (id1 == id2){
                this.escena.parejaEncontrada();
            }
            else {
                carta1.ocultar();
                carta2.ocultar();
            }
        }
    }
    
    public void ocultar() {
        bajar.resume();
        bajar.setStartTime(System.currentTimeMillis());
        
        onClick = false;
    }
    
    public void setID(int ident){
        this.id = ident;
    }
    
    public int getID(){
        return id;
    }
    
    public void setScene(Scene esce){
        this.escena = esce;
    }
      
}
