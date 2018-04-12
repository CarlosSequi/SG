package SistemaSolar;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

public class Orbita extends BranchGroup {
    
    private float radioInterno;
    private float radioExterno;
    private Material material;
    private Appearance ap;
    
    public Orbita(float radioInterno, float radioExterno) {
        
        this.radioInterno = radioInterno;
        this.radioExterno = radioExterno;
        Material materialAnillos = new Material (
            new Color3f (0.8f, 0.8f, 0.8f), // componente ambiental
            new Color3f (0.7f, 0.7f, 0.7f), // componente difusa
            new Color3f (0.0f, 0.0f, 0.0f), // componente emisiva 
            new Color3f (0.2f, 0.2f, 0.2f), // componente especualar
            10f                             // brillo
        );
        this.material = materialAnillos;
        
        // creación de la apariencia a partir del material
        ap = new Appearance();
        ap.setMaterial(this.material);
        
        // Se cuelgan del grafo las transformaciones y la figura
        this.addChild(new Torus(radioInterno,radioExterno,64,2,ap));
        
    }
    
}
