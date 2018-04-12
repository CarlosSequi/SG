
/* ******* ******* ******* ******* ******* ******* ******* 

Javascript implementa el paradigma de la orientación a objetos de una manera diferente a otros
lenguajes como Java o C++

Se podría decir que una función es una clase.

Dentro de esa función las variables y funciones que se declaren con   var   serían 
variables de instancia y métodos privados.

Mientras que lo que se declare con   this.   serían 
variables de instancia y métodos públicos.

Ver como ejemplo esta clase   TheScene.

El problema está, que en cada objeto que se cree a partir de una clase definida de esta manera, se repite todo el código de todos los métodos, con lo que las necesidades de memoria aumentan considerablemente.

Lo deseable sería que el código de los métodos existiera solamente una vez, común para todos los objetos que se creen de dicha clase.

Para ello, debemos recurrir al modo de definición de métodos de clases mediante prototipos.

Para la clase   TheScene,   dado que es una clase fachada, de la que solo va a existir un objeto, no importa definirla de la manera que se muestra en este archivo. Pero para clases de las que se van a instanciar muchos objetos, lo recomendable es hacerlo mediante prototipos.

En las clases   Astro   y   Estrella   que se incluyen en este proyecto, se tiene un ejemplo de cómo se definen clases mediante prototipos.

   ******* ******* ******* ******* ******* ******* ******* */


/// Clase fachada, la escena
TheScene = function (renderer) {
  THREE.Scene.call (this);
  
  var camera = null;
  // El objeto que permite interactuar con la cámara
  var trackballControls = null;
  var axis = null;
  var model = null;
  
  /// Se crea la cámara, es necesario el renderer para interactuar con ella
  /**
   * @param renderer - El renderer que muestra la imagen y al mismo tiempo captura la interacción del usuario
   */
  var createCamera = function (self, renderer) {
    // Se define una cámara en perspectiva, con un ángulo de visión de 45 grados,
    // Un ratio de aspecto según las dimensiones de la ventana
    // Y unos planos de recorte cercano y lejano
    camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 1000);
    
    // Dónde se sitúa y hacia donde mira
    camera.position.set (30, 30, 30);
    var look = new THREE.Vector3 (0,0,0);
    camera.lookAt(look);
    
    // El objeto que permite orbitar la cámara, reencuadrarla y hacer zoom
    trackballControls = new THREE.TrackballControls (camera, renderer);
    trackballControls.rotateSpeed = 5;
    trackballControls.zoomSpeed = -2;
    trackballControls.panSpeed = 0.5;
    trackballControls.target = look;
  }
  
  /// Se crean las luces y se añaden a la escena
  var createLights = function (self) {
    // Una ambiental
    ambientLight = new THREE.AmbientLight(0xffffff, 5);
    self.add (ambientLight);
  }
  
  /// Se crea el modelo
  /**
   * @return La raiz de la rama del modelo
   */
  var createModel = function () {
    var sol = new Estrella (10, 4000, 'sun.jpg');
    // Ejemplo de llamada a un método que está sobreescrito y en su sobreescritura llama al mismo método de la superclase. Verlo en las clases Astro y Estrella
    sol.metodo();
    return sol;
  }
  
  /// Inicializador
  /**
   * @param renderer - El renderer donde se visualizará la escena
   */
  var init = function (self, renderer) {
    createLights (self);
    createCamera (self, renderer);
    axis = new THREE.AxisHelper (25);
    self.add (axis);
    model = createModel ();
    self.add (model);
  }
  
  // public

  /// Teniendo en cuenta los controles de la GUI se modifica en la escena todo lo necesario. Se realliza mediante mensajes a los objetos que correspondan. Los mensajes al modelo se realizan a través de su fachada.
  this.animate = function (controls) {
    // Se muestran o no los ejes
    axis.visible = controls.axis;
    model.startStop(controls.startStop);
  }
  
  /// Getter de la cámara
  this.getCamera = function () {
    return camera;
  }
  
  /// Getter del controlador de la cámara
  this.getCameraControls = function () {
    return trackballControls;
  }
  
  /// Modifica el ratio de aspecto de la cámara
  /**
   * @param anAspectRatio - El nuevo ratio de aspecto de la cámara
   */
  this.setCameraAspect = function (anAspectRatio) {
    camera.aspect = anAspectRatio;
    camera.updateProjectionMatrix();
  }
  
  // constructor
  
  init (this, renderer);
}

TheScene.prototype = Object.create (THREE.Scene.prototype);
TheScene.prototype.constructor = TheScene;

