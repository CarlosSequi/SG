package SistemaSolar;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Lights extends BranchGroup{
    
    public static final int DIRECTIONALLIGHT = 1;
    public static final int SPOTLIGHT = 2;
    private BranchGroup lightsNode;
    
    public Lights () {
        lightsNode = new BranchGroup();
        BoundingSphere influencingBound = new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 100.0);
        
        // Se crea la luz ambiente
        
        
        Light aLight = new AmbientLight (new Color3f (0.9f, 0.9f, 0.9f));
        aLight.setInfluencingBounds (influencingBound);
        aLight.setEnable(true);
        lightsNode.addChild(aLight);

        // Se crea la primera luz (luz focal)
//        Color3f white = new Color3f (1.0f, 1.0f, 1.0f);
//        Vector3f direction = new Vector3f (-1f, 0f, 0f);
//        Point3f position = new Point3f (0f, 0f, 0f);
//        Point3f atenuation = new Point3f (1f, 0f, 0f);
//        aLight = new SpotLight (white, position, atenuation, direction, 7f, 1f);
//        aLight.setInfluencingBounds (influencingBound);
//        aLight.setCapability(Light.ALLOW_STATE_WRITE);
//        aLight.setEnable (true);
//        lightsNode.addChild(aLight);

        // Se crea la segunda luz (luz direccional)
        aLight = new DirectionalLight (new Color3f (0.7f, 0.7f, 0.7f), 
                        new Vector3f (1.0f, -1.0f, -4.0f));
        aLight.setInfluencingBounds (influencingBound);
        aLight.setCapability(Light.ALLOW_STATE_WRITE);
        aLight.setEnable (false);
        lightsNode.addChild(aLight);

        this.addChild(lightsNode);
    }  
    
    // Encender o apagar las luces
    public void setLightOnOff (int lightIndex, boolean onOff) {
        if (lightIndex == SPOTLIGHT || lightIndex == DIRECTIONALLIGHT) {
            ((Light) lightsNode.getChild (lightIndex)).setEnable(onOff);
        }   
    } 
    
}
