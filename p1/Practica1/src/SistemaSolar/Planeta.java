package SistemaSolar;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.vecmath.Color3f;

public class Planeta extends CuerpoCeleste{
    
    private Orbita orbita;
    
    public Planeta(float size, String name, float x, float y, float z, 
                    long vr, long vt) {
        
        super(size, name, false, x, y, z, vr, vt, false,false);
        
        // Creación órbita
        orbita = new Orbita(x, 0.01f);     
        this.addChild(orbita);
        
    }
    
    public void addSatelite(Satelite unSatelite) {
        posicion.addChild(unSatelite);
    }
    
}
