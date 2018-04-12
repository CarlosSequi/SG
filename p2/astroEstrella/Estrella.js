 
/* ******* ******* ******* ******* ******* ******* ******* 
   
   Con las clases  Astro  y  Estrella  (que hereda de Astro) se explica como se implementan
   las clases en  Javascript  mediante prototipos.
   
   ******* ******* ******* ******* ******* ******* ******* */

// Estrella  hereda de   Astro

Estrella = function (radio, tiempoGiro, textura) {
  // La propia función que define la clase es su constructor
  // Lo primero que se hace es llamar al constructor de su superclase, en este caso   Astro
  // Como parámetros se pasa a sí misma,   this,   más los otros parámetros que hicieran falta pasarle a la superclase.
  
  Astro.call (this, radio, tiempoGiro, textura);
  
  // Se le añade la componente emisiva al Astro: intensidad y textura
  this.elAstro.material.emissive.set(0xffffff);
  this.elAstro.material.emissiveMap = this.texturaCargada;
  
  // Le ponemos una luz puntual
  this.elAstro.add (new THREE.PointLight(0xffffff,50,0,1));
}

// La clase  Estrella  hereda los métodos de su superclase, en este caso la clase  Astro
Estrella.prototype = Object.create (Astro.prototype);

// Indicamos cuál es su constructor
Estrella.prototype.constructor = Estrella;

// Si queremos sobreescribir un método se define
Estrella.prototype.metodo = function () {
  // Si quisiéramos llamar al mismo método de su superclase hacemos uso de los prototipos de la superclase
  Astro.prototype.metodo();
  console.log ('Soy una Estrella');
}
