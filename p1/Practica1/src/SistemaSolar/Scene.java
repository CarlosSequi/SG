package SistemaSolar;

import javax.media.j3d.BranchGroup;

public class Scene extends BranchGroup{
    
    private Estrella sol;
    private Planeta mercurio, venus, tierra, marte, jupiter;
    private Satelite luna, fobos, deimos, io, europa, calisto;
    
    // Constructor de toda la escena
    public Scene() {
        
        // Como raíz se usa un BranchGroup
        BranchGroup scene = new BranchGroup();
        
        // Creación de la estrella
        sol = new Estrella(4.0f, "sol");
        
        // Creación de los planetas
        mercurio = new Planeta(0.39f, "mercurio", 5.85f, 0.0f, 0.0f, 37600, 11740);        
        venus    = new Planeta(0.95f, "venus"   , 10.8f, 0.0f, 0.0f, 44949, 28600);        
        tierra   = new Planeta(1.00f, "tierra"  , 15.0f, 0.0f, 0.0f, 20000, 43050);       
        marte    = new Planeta(0.53f, "marte"   , 22.8f, 0.0f, 0.0f, 25396, 59240);       
        jupiter  = new Planeta(1.50f, "jupiter" , 35.0f, 0.0f, 0.0f, 15780, 82000);
        
        // Creación de los satélites
        //EXAMEN: añado el último parámetro a los satélites para indicar cual es la luna
        luna    = new Satelite(0.27f, "luna"   , 2.0f, 0.0f, 0.0f, 10000, 10000,true);
        fobos   = new Satelite(0.22f, "fobos"  , 2.0f, 0.0f, 0.0f, 2500, 2500,false);
        deimos  = new Satelite(0.12f, "deimos" , 3.0f, 0.0f, 0.0f, 3400, 3400,false);
        io      = new Satelite(0.30f, "io"     , 2.0f, 0.0f, 0.0f, 3500, 3500,false);
        europa  = new Satelite(0.25f, "europa" , 4.0f, 0.0f, 0.0f, 5100, 5100,false);
        calisto = new Satelite(0.38f, "calisto", 6.0f, 0.0f, 0.0f, 7338, 7338,false);
        
        
        // Grafo de escena de los cuerpos celestes
        sol.addPlaneta(mercurio);
        sol.addPlaneta(venus);
        sol.addPlaneta(tierra);
        sol.addPlaneta(marte);
        sol.addPlaneta(jupiter);
        tierra.addSatelite(luna);
        marte.addSatelite(fobos);
        marte.addSatelite(deimos);
        jupiter.addSatelite(io);
        jupiter.addSatelite(europa);
        jupiter.addSatelite(calisto);
        
        // Se añade la esfera a la rama (BG)
        scene.addChild(sol);    
        this.addChild(scene);
    }

}
