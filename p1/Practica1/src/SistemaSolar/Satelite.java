package SistemaSolar;

public class Satelite extends CuerpoCeleste{
    
    public Satelite(float size, String name, float x, float y, float z, 
                    long vr, long vt,boolean esLuna) {
        
            super(size, name, false, x, y, z, vr, vt,esLuna,true);
      
    } 
}
