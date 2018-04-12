package SistemaSolar;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

public class Axes extends BranchGroup {
    
    private Switch axesBranch;
    
    // Constructor de los ejes.
    
    public Axes(float length) {
        
        // Se crea el nodo del que cuelgan los tres ejes.
        //      Se le permite mostrar sus hijos o no, modificable en tiempo de 
        //      ejecución.
        //      De inicio, NO se muestran todos los hijos -> NONE.
        //      Para mostrarlos poner Switch.CHILD_ALL.
        axesBranch = new Switch();
        axesBranch.setCapability(Switch.ALLOW_SWITCH_WRITE);
        axesBranch.setWhichChild(Switch.CHILD_NONE);
        
        // Los cilindros se crean con su centroide en el origen en vertical.
        // Traslación para situar el origen en la base del cilindro.
        Transform3D translation = new Transform3D();
        translation.setTranslation(new Vector3d(0.0f, length/2, 0.0f));
        
        // Eje X en rojo
        // La transformación para orientarlo
        TransformGroup axisTransformGroup = new TransformGroup();
        Transform3D transform = new Transform3D();
        transform.rotZ(-Math.PI/2);
        transform.mul(translation);
        axisTransformGroup.setTransform(transform);
        
        // El aspecto en color rojo
        Appearance material = new Appearance();
        material.setColoringAttributes(new ColoringAttributes(1.0f, 0.0f, 0.0f, ColoringAttributes.SHADE_FLAT));
        
        // Cilindro en rojo con la orientación correcta
        Primitive axisGeometry = new Cylinder(0.1f, length, material);
        axisTransformGroup.addChild(axisGeometry);
        
        // Se añade este eje a la rama de los ejes
        axesBranch.addChild(axisTransformGroup);
        
        // Eje Y en verde
        // La transformación para orientarlo
        axisTransformGroup = new TransformGroup();
        axisTransformGroup.setTransform(translation);
        
        // El aspecto en color verde
        material = new Appearance();
        material.setColoringAttributes(new ColoringAttributes(0.0f, 1.0f, 0.0f, ColoringAttributes.SHADE_FLAT));
        
        // Cilindro en verde con la orientación correcta
        axisGeometry = new Cylinder(0.1f, length, material);
        axisTransformGroup.addChild(axisGeometry);
        
        // Se añade este eje a la rama de los ejes
        axesBranch.addChild(axisTransformGroup);
        
        // Eje Z en azul
        // La transformación para orientarlo
        axisTransformGroup = new TransformGroup();
        transform = new Transform3D();
        transform.rotX(Math.PI/2);
        transform.mul(translation);
        axisTransformGroup.setTransform(transform);
        
        // El aspecto en color azul
        material = new Appearance();
        material.setColoringAttributes(new ColoringAttributes(0.0f, 0.0f, 1.0f, ColoringAttributes.SHADE_FLAT));
        
        // Cilindro en azyl con la orientación correcta
        axisGeometry = new Cylinder(0.1f, length, material);
        axisTransformGroup.addChild(axisGeometry);
        
        // Se añade este eje a la rama de los ejes
        axesBranch.addChild(axisTransformGroup);
        
        // La rama de los ejes se cuelga de su padre
        this.addChild(axesBranch);
    }
    
    // Función que permite mostrar u ocultar los ejes.
    
    public void showAxes(boolean onOff) {
        if (onOff)
            axesBranch.setWhichChild(Switch.CHILD_ALL);
        else
            axesBranch.setWhichChild(Switch.CHILD_NONE);
    }
    
}
