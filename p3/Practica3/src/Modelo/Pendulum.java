package Modelo;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.RotationPathInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

class Pendulum extends BranchGroup {
    
  private final float length = 20.0f;
  private final long time;
  private Alpha timer;
  private BranchGroup connection;
  
  public Pendulum (float aTime) {
    // Se recibe el tiempo en segundos y se almacena en milisegundos
    time = (long) (aTime*1200);
    
    // Se crea el aspecto
    Appearance gold = new Appearance();
    gold.setMaterial(new Material (
        new Color3f (0.20f, 0.20f, 0.20f),   // Color ambiental
        new Color3f (0.00f, 0.00f, 0.00f),   // Color emisivo
        new Color3f (0.49f, 0.34f, 0.00f),   // Color difuso
        new Color3f (0.89f, 0.79f, 0.00f),   // Color especular
        17.0f ));                            // Brillo
    
    // Se crea el cilindro y se coloca
    Cylinder cylinder = new Cylinder (length/20, length*2, Primitive.GENERATE_NORMALS, 6, 1, gold);
    Transform3D transCylinder = new Transform3D();
    transCylinder.set (new Vector3f (0.0f, -length/2, 0.0f));
    TransformGroup tgCylinder = new TransformGroup (transCylinder);
    tgCylinder.addChild(cylinder);
    
    // Se crea la esfera y se coloca
    Sphere sphere = new Sphere (length/5, Primitive.GENERATE_NORMALS, 15, gold);
    Transform3D transSphere = new Transform3D ();
    transSphere.set (new Vector3f (0.0f, -length*1.5f, 0.0f));
    TransformGroup tgSphere = new TransformGroup (transSphere);
    tgSphere.addChild(sphere);
    
    
    // Se crea la oscilación
    Transform3D transRotator = new Transform3D();
    TransformGroup rotator = new TransformGroup (transRotator);
    rotator.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    rotator.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);  
    timer = new Alpha (-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 
        0, 0, time, time/2, 0, time, time/2, 0);
    float[] alphas = {0.0f, 1.0f};
    Quat4f[] rotations = {new Quat4f(), new Quat4f()};
    rotations[0].set (new AxisAngle4f (0.0f, 0.0f, 1.0f, -1.0f));
    rotations[1].set (new AxisAngle4f (0.0f, 0.0f, 1.0f,  1.0f));
    RotationPathInterpolator interpolator = new RotationPathInterpolator (
        timer, rotator, transRotator, alphas, rotations);
    interpolator.setSchedulingBounds(new BoundingSphere(new Point3d (0.0f, 0.0f, 0.0f), 100.0f));
    rotator.addChild(interpolator);
    
    // Se termina de conectar el grafo
    rotator.addChild(tgCylinder);
    rotator.addChild(tgSphere);
    this.addChild(rotator);
  }
  
}