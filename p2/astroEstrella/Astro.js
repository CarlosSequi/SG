
/* ******* ******* ******* ******* ******* ******* ******* 
   
   Con las clases  Astro  y  Estrella  (que hereda de Astro) se explica como se implementan
   las clases en  Javascript  mediante prototipos.
   
   ******* ******* ******* ******* ******* ******* ******* */


// Astro   va a heredar de la clase   Object3D   de la biblioteca THREE

Astro = function (radio, tiempoGiro, textura) {
  // La propia función que define la clase es su constructor
  // Lo primero que se hace es llamar al constructor de su superclase, en este caso   Object3D
  // Como parámetros se pasa a sí misma,   this,   más otros parámetros que hicieran falta pasarle a la superclase.
  THREE.Object3D.call (this);
  
  // Las variables   var   son variables  locales  al constructor. No son accesibles desde los métodos prototipo.
  
  var cargadorTextura = new THREE.TextureLoader();
  
  // Las variables   this.   son variables de instancia públicas.
  // Siempre hay que nombrarlas con la sintaxis   this.variable   No poner   this.   daría error ya que no sería la misma variable.
  
  this.texturaCargada = cargadorTextura.load (textura);
    
  this.elAstro = new THREE.Mesh (
        new THREE.SphereGeometry (radio),
        new THREE.MeshLambertMaterial ({
          color: 0x0f0f0f, 
          map: this.texturaCargada
        })
      );
    
  var rotacionInicial = { angulo : 0 };
  var rotacionFinal = { angulo : 2 * Math.PI };
  // Alamacenamos en la variable local   astro   una referencia  al atributo this.elAstro
  var astro = this.elAstro;
  this.interpolador = new TWEEN.Tween (rotacionInicial).to(rotacionFinal, tiempoGiro)
    .onUpdate (function(){
      // Dentro de esta función podemos acceder a  this.elAstro  gracias a la referencia que hemos almacenado previamente en   astro
      astro.rotation.y = rotacionInicial.angulo;
    })
    .repeat (Infinity)
    .start();
  
  this.add (this.elAstro);
  
}

// La clase  Astro  hereda los métodos de su superclase, en este caso la clase  Object3D  de la biblioteca  THREE
Astro.prototype = Object.create (THREE.Object3D.prototype);

// Indicamos cuál es su constructor
Astro.prototype.constructor = Astro;

  // Ahora se añaden los nuevos métodos públicos de la clase. Se añaden como prototipos.

Astro.prototype.startStop = function (onOff) {
  if (onOff) {
    // Recordar. Para acceder a los atributos de la clase hay que usar obligatoriamente la notación this.variable
    this.interpolador.resume();
  } else {
    this.interpolador.pause();
  }
}

// Este método se incluye para explicar como se redefinen métodos y como se haría una llamada al método equivalente de la superclase.
// Se explicará en la clase  Estrella  que hereda de  Astro.
Astro.prototype.metodo = function () {
  console.log ('Soy un Astro');
}
