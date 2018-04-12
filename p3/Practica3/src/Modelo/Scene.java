package Modelo;

import java.util.ArrayList;
import javax.media.j3d.BranchGroup;
import java.util.Collections;
import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

public class Scene extends BranchGroup{
    
    private ArrayList<Nivel> niveles = new ArrayList();
    private ArrayList<Tapete> tapetes = new ArrayList();
    private static int nivel_actual = 0;
    private int num_cartas;
    private BranchGroup nivel;
    private Tapete tapete;
    private Alpha alfa = new Alpha(1,3000);
    private ArrayList<Carta> cartas;
        
    // Constructor de toda la escena
    public Scene() {
        
        this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        this.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        
        this.añadirNiveles();
        this.añadirTapetes();
        
        // Como raíz se usa un BranchGroup
        //BranchGroup scene = new BranchGroup();
        
        tapete = tapetes.get(nivel_actual);
        nivel = this.generarNivel();
        nivel.setCapability(BranchGroup.ALLOW_DETACH);

        Reloj reloj = new Reloj();
        
        this.addChild(reloj);
        this.addChild(nivel);
        this.addChild(tapete);
                
    }
    
    private void añadirNiveles(){
        
        Nivel nuevo_nivel = new Nivel(1,2,2,2);
        niveles.add(nuevo_nivel);
        
        nuevo_nivel = new Nivel(2,4,4,2);
        niveles.add(nuevo_nivel);
        
        nuevo_nivel = new Nivel(3,6,4,3);
        niveles.add(nuevo_nivel);
        
        nuevo_nivel = new Nivel(4,8,4,4);
        niveles.add(nuevo_nivel);
        
        nuevo_nivel = new Nivel(5,10,5,4);
        niveles.add(nuevo_nivel);
    }
    
    private void añadirTapetes(){
        
        Tapete tapete1 = new Tapete(5,7);
        Tapete tapete2 = new Tapete(14,8);
        Tapete tapete3 = new Tapete(14,14);
        Tapete tapete4 = new Tapete(14,18);
        
        tapete1.setCapability(BranchGroup.ALLOW_DETACH);
        tapete2.setCapability(BranchGroup.ALLOW_DETACH);
        tapete3.setCapability(BranchGroup.ALLOW_DETACH);
        tapete4.setCapability(BranchGroup.ALLOW_DETACH);
        
        tapetes.add(tapete1);
        tapetes.add(tapete2);
        tapetes.add(tapete3);
        tapetes.add(tapete4);
        
    }
    
    private BranchGroup generarNivel(){
        
        BranchGroup rama_nivel = new BranchGroup();
        cartas = new ArrayList();
        
        Nivel nivel = niveles.get(nivel_actual);
        num_cartas = nivel.getNumParesCartas();
        
        for (int i = 0; i < 2*nivel.getNumParesCartas(); i++){
            
            Carta carta1 = new Carta(0+(i%nivel.getColumnas())*5f, 0.1f,
                                     0+((int) (i/nivel.getColumnas()))*7f);
            rama_nivel.addChild(carta1);
            i= i+1;
            Carta carta2 = new Carta(0+(i%nivel.getColumnas())*5f, 0.1f,
                                     0+((int) (i/nivel.getColumnas()))*7f);
            rama_nivel.addChild(carta2);
            
            cartas.add(carta1);
            cartas.add(carta2);
        }
        
        Collections.shuffle(cartas);
        
        int indice_textura = 0;
        for(int i=0; i<cartas.size(); i=i+2){
            cartas.get(i).añadirParteAbajo(Integer.toString(indice_textura));
            cartas.get(i).setID(indice_textura);
            cartas.get(i).setUserData(cartas.get(i));
            cartas.get(i+1).añadirParteAbajo(Integer.toString(indice_textura));
            cartas.get(i+1).setID(indice_textura);
            cartas.get(i+1).setUserData(cartas.get(i+1));
            indice_textura++;
        }
        
        cartas.get(1).setScene(this);
                        
        return rama_nivel;
    }
    
    public void parejaEncontrada(){
        this.num_cartas--;
        System.out.println(num_cartas);
    }
    
    public void comprobarFin(){
        if (num_cartas == 0){
            
            this.lanzarFuegos();
            
            nivel_actual++;
            this.nivel.detach();
            this.tapete.detach();
            this.tapete = tapetes.get(nivel_actual);
            this.nivel = this.generarNivel();
            this.nivel.setCapability(BranchGroup.ALLOW_DETACH);
            this.addChild(tapete);
            this.addChild(nivel);
            
        }
    }
    
    private void lanzarFuegos(){
        
        FuegosArtificiales cohete1 = new FuegosArtificiales("cohete1.jpg", 0,0);
        FuegosArtificiales cohete2 = new FuegosArtificiales("cohete2.jpg", 2,4);
        FuegosArtificiales cohete3 = new FuegosArtificiales("cohete3.jpg", 4,1);
        
        TransformGroup tgaux = new TransformGroup();
        tgaux.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        tgaux.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tgaux.addChild(cohete3);
        tgaux.addChild(cohete2);
        tgaux.addChild(cohete1);
        
        BranchGroup bgaux = new BranchGroup();
        bgaux.addChild(tgaux);
        
        this.addChild(bgaux);
     
        Transform3D transf = new Transform3D();
        //alfa = new Alpha(1,2000);
        alfa.setStartTime(System.currentTimeMillis());
        //alfa.pause();
        float [] alfas = {0.0f, 0.25f, 0.50f, 0.75f, 1.0f};
        
        Point3f [] posiciones = {
            new Point3f(0.0f, 0.0f, 0.0f), new Point3f(0.0f, 20.0f, 0.0f),
            new Point3f(0.0f, 40.0f, 0.0f), new Point3f(0.0f, 70.0f, 0.0f),
            new Point3f(0.0f, 150.0f, 0.0f)
        };
        
        Quat4f [] orientaciones = new Quat4f[5];
        for (int i = 0; i < 5; i++){
            orientaciones[i] = new Quat4f();
        }
        orientaciones[0].set(new AxisAngle4f(0f,0f,1f, 0.0f));
        orientaciones[1].set(new AxisAngle4f(0f,0f,1f, 0.0f));
        orientaciones[2].set(new AxisAngle4f(0f,0f,1f, 0.0f));
        orientaciones[3].set(new AxisAngle4f(0f,0f,1f, 0.0f));
        orientaciones[4].set(new AxisAngle4f(0f,0f,1f, 0.0f));
        
        RotPosPathInterpolator interpolador = new RotPosPathInterpolator(
                alfa, tgaux, transf, alfas, orientaciones, posiciones);
        
        interpolador.setSchedulingBounds(new BoundingSphere (new Point3d(), 2.0f));
        
        BranchGroup bgaux2 = new BranchGroup();
        bgaux2.addChild(interpolador);
        
        tgaux.addChild(bgaux2);
        
    }

}
