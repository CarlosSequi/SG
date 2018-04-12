package Modelo;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class TheUniverse {
    
    // Atributos de la clase
    private Canvas3D canvas;
    private SimpleUniverse universe;
    
    private Scene scene;
    private TheBackground background;
    private Axes axes;
    private Lights lights;
    
    public static final int DIRECTIONALLIGHT = 1;
    
    private Pick pick;
        
    // Constructor de la clase
    
    public TheUniverse (Canvas3D aCanvas) {
        
        // Todo cuelga de un nodo raiz
        BranchGroup root = new BranchGroup();
        
        canvas = aCanvas;
        
        // Se crea la rama del fondo y se cuelga al universo
        background = new TheBackground();
        root.addChild(background);
        
        // Se crea la rama de los ejes y se cuelga al universo
        axes = new Axes(10.0f);
        root.addChild(axes);
        
        // Se crea la rama de la escena y se cuelga al universo
        scene = new Scene();
        root.addChild(scene);
        
        // Se crean y se añaden luces
        lights = new Lights();
        root.addChild(lights);
        
        // Se crea el universo y la rama de la vista con ese canvas
        universe = createUniverse(canvas);
        
        // Se crea el pick y se cuelga de la escena
        pick = new Pick(canvas, scene);
        pick.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 100.0));
        scene.addChild(pick);
        
        // Se optimiza la escena y se cuelga del universo
        root.compile();
        universe.addBranchGraph(root);
        
        // Se inicia la búsqueda del pick
        pick.initSearch(scene); 

    }
    
    // Creación del SimpleUniverse
    
    private SimpleUniverse createUniverse(Canvas3D canvas) {
        // Se crea manualmente un ViewingPlatform
        //      para poder personalizarlo y asignárselo al universo
        ViewingPlatform viewingPlatform = new ViewingPlatform();
        // Se establece el radio de activación
        viewingPlatform.getViewPlatform().setActivationRadius(100f);
        // La transformación de vista :
        //      dónde se está, a dónde se mira, Vup
        TransformGroup viewTransformGroup = viewingPlatform.getViewPlatformTransform();
        Transform3D viewTransform3D = new Transform3D();
        viewTransform3D.lookAt(new Point3d(5, 40, 50), new Point3d(5, 0, 5), new Vector3d(0, 1, 0));
        viewTransform3D.invert();
        viewTransformGroup.setTransform(viewTransform3D);

        // El comportamiento, para mover la camara con el ratón
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0.0f, 0.0f, 0.0f), 100.0f));
        orbit.setZoomFactor(2.0f);
        viewingPlatform.setViewPlatformBehavior(orbit);

        // Parámetros de la proyección
        // Se establece el angulo de vision a 45 grados y el plano de recorte trasero
        Viewer viewer = new Viewer(canvas);
        View view = viewer.getView();
        view.setFieldOfView(Math.toRadians(45));
        view.setBackClipDistance(50.0);
        // Se construye y devuelve el Universo con los parametros definidos
        return new SimpleUniverse(viewingPlatform, viewer);   
    }
    
    // Método getCanvas para pasarselo a la clase Visualization.    
    public Canvas3D getCanvas() {
        return canvas;
    }

    // Método para mostrar los ejes
    public void showAxes(boolean onOff) {
        // La clase fachada transmite las órdenes a los objetos que correspondan
        axes.showAxes(onOff);
    }
    
    // Encender o apagar las luces
    public void setLightOnOff (int lightIndex, boolean onOff) {
        lights.setLightOnOff(lightIndex, onOff);
    }

}
