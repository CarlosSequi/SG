package SistemaSolar;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Light;
import javax.media.j3d.SpotLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class Estrella extends CuerpoCeleste {
    
    private Light aLight;
        
    public Estrella(float size, String name) {
        
        super(size, name, true, 0.0f, 0.0f, 0.0f, 0, 37600, false,false);   
        
        // Se crea la luz focal
        BoundingSphere influencingBound = new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 100.0);
        
        Color3f white = new Color3f (1.0f, 1.0f, 1.0f);
        Vector3f direction = new Vector3f (1f, 0f, 0f);
        Point3f position = new Point3f (0f, 0f, 0f);
        Point3f atenuation = new Point3f (2f, 0f, 0f);
        //ultimos dos parámetros: spreadAngle, concentration
        aLight = new SpotLight (white, position, atenuation, direction, 7f, 1f);
        aLight.setInfluencingBounds (influencingBound);
        
        aLight.setEnable (true);
        this.addChild(aLight);
        
    }
    
    public void addPlaneta(Planeta unPlaneta) {
        
        this.addChild(unPlaneta);
    }
    
}
